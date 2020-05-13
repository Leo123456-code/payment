package com.imooc.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.*;
import com.imooc.demo.pojo.*;
import com.imooc.demo.service.IOrderService;
import com.imooc.demo.util.BigDecimalUtil;
import com.imooc.demo.util.DateTimeUtil;
import com.imooc.demo.util.PropertiesUtil;
import com.imooc.demo.vo.OrderItemVo;
import com.imooc.demo.vo.OrderProductVo;
import com.imooc.demo.vo.OrderVo;
import com.imooc.demo.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * ClassName: OrderServiceImpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/20-20:55
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse createOrder(Integer userId, Integer shippingId) throws Exception{
        /**
         * @Description //TODO 根据发货地址创建订单
           @Author Leo
         * @Date 21:03 2020/3/20
         * @Param [userId, shippingId]
         * @return com.imooc.demo.common.ServerResponse
        */
        //从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckCartByUserId(userId);
        //计算总价
        ServerResponse serverResponse =  getCartOrderItem(userId,cartList);
        //如果不是成功的,将错误返回给前端
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        //成功后会强转
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        //总价
        BigDecimal payment = getOrderTotalPrice(orderItemList);
        //生成订单
        Order order = assembleOrder(userId,shippingId,payment);
        if(order == null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if(CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //给orderItem设置订单号
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis批量插入
        orderItemMapper.bachInsert(orderItemList);
        //生成成功,减少库存
        reduceProductStock(orderItemList);
        //清空购物车
        cleanCart(cartList);
        //返回给前端数据OrderVo
        try{
            reduceProductStock(orderItemList);
            //清空购物车
            cleanCart(cartList);
        }catch (Exception e){
           log.error("清空购物车异常",e);
           throw e;
        }
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    @Override
    public ServerResponse<String> cancel(Integer userId, Long orderNo) throws Exception {
        /**
         * @Description //TODO 取消订单
           @Author Leo
         * @Date 12:16 2020/3/21
         * @Param [userId, orderNo]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("该用户订单不存在");
        }
        if(order.getStatus() > Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        //订单已取消
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCount = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(rowCount == 0){
            return ServerResponse.createByErrorMessage("取消失败");
        }
        return ServerResponse.createBySuccess("取消成功");
    }

    @Override
    public ServerResponse getOrderCartProductDetail(Integer userId) {
        /**
         * @Description //TODO 获取购物车已经选中的商品详情
           @Author Leo
         * @Date 12:35 2020/3/21
         * @Param [userId]
         * @return com.imooc.demo.common.ServerResponse
        */
        OrderProductVo orderProductVo = new OrderProductVo();
        //从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckCartByUserId(userId);
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        //如果不成功,将错误返回
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        //计算已经选中的购物车商品总价
        BigDecimal payment = new BigDecimal(0);
        if( orderItemList == null || orderItemList.size() <= 0){
            return ServerResponse.createBySuccess(orderItemList);
        }
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return ServerResponse.createBySuccess(orderProductVo);
    }

    @Override
    public ServerResponse getOrderDetail(Integer userId, Long orderNo) {
        /**
         * @Description //TODO 订单详情
           @Author Leo
         * @Date 12:54 2020/3/21
         * @Param [userId, orderNo]
         * @return com.imooc.demo.common.ServerResponse
        */
        Order order = orderMapper.selectByUserIdAndOrderNo(userId, orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNOAndUserId(userId, orderNo);
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("没有找到该订单");
    }

    @Override
    public ServerResponse getOrderList(Integer userId, int pageNum, int pageSize) {
        /**
         * @Description //TODO 根据UserId查询所有订单,分页显示
           @Author Leo
         * @Date 13:10 2020/3/21
         * @Param [userId, pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse
        */
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,userId);
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVoList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse manageList(int pageNum, int pageSize) {
        /**
         * @Description //TODO 后台分页查询订单列表
           @Author Leo
         * @Date 13:42 2020/3/21
         * @Param [pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.PayInfo>
        */
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = assembleOrderVoList(orderList, null);
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVoList);
        pageInfo.setList(orderVoList);

        return ServerResponse.createBySuccess(orderVoList);
    }

    @Override
    public ServerResponse manageDetail(Long orderNo) {
        /**
         * @Description //TODO 后台查询订单详情
           @Author Leo
         * @Date 14:02 2020/3/21
         * @Param [orderNo]
         * @return com.imooc.demo.common.ServerResponse
        */
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            //转换Vo
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    @Override
    public ServerResponse manageSearch(Long orderNo, int pageNum, int pageSize) {
        /**
         * @Description //TODO 后台搜索orderNo,精确匹配
           @Author Leo
         * @Date 14:12 2020/3/21
         * @Param [orderNo, pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse
        */
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
           List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
           OrderVo orderVo = assembleOrderVo(order,orderItemList);

            PageInfo pageInfo = new PageInfo<>(Lists.newArrayList(order));
            pageInfo.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse manageSendGoods(Long orderNo) {
        /**
         * @Description //TODO 后台发货
           @Author Leo
         * @Date 14:25 2020/3/21
         * @Param [orderNo]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("订单不存在");
        }
        log.info("order={}",gson.toJson(order));
        if(order.getStatus() == Const.OrderStatusEnum.PAID.getCode()){
            //把状态改成已发货
            order.setStatus(Const.OrderStatusEnum.SHIPPING.getCode());
            order.setSendTime(new Date());
            int rowCount = orderMapper.updateByPrimaryKeySelective(order);
            if(rowCount == 0){
                return ServerResponse.createByErrorMessage("未付款,不能发货");
            }
            return ServerResponse.createBySuccess("发货成功");
        }else {
            return ServerResponse.createByErrorCodeMessageOf(ResponseCode.ERROR.getCode(),
                    "修改发货状态失败"+",商品状态码status="+order.getStatus());
        }
    }

    //构建OrderVoList
    private List<OrderVo> assembleOrderVoList(List<Order> orderList,Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if(userId == null){
                //TODO 管理员查询的时候不需要传UserId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            }else {
                orderItemList = orderItemMapper.getByOrderNOAndUserId(userId, order.getOrderNo());
            }
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    //构建OrderVo
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        //支付类型描述
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getMessage());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getMessage());
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(assembShippingVo(shipping));
        }
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(new Date()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(new Date()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(new Date()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(new Date()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(new Date()));
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    //构建orderItemVo对象
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(new Date());
        return orderItemVo;
    }

    //构建shippingVo
    private ShippingVo assembShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    //清空购物车
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }


    //减少库存
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    //生成订单
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        //生成订单号
        long orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        //支付状态：未支付
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        int rowCount = orderMapper.insert(order);
        if(rowCount > 0){
            return order;
        }
        return null;
    }

    //生成订单
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    //构建总价
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        //初始为0
        BigDecimal payment = new BigDecimal("0");
        //遍历,并相加
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    //构建orderItemList
    private ServerResponse<List<OrderItem>> getCartOrderItem(Integer userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //校验购物车的数据,包括产品的状态和数量
        for (Cart cartItem : cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if(Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()){
                return ServerResponse.createByErrorMessage("产品不是在线售卖状态");
            }
            //校验库存
            if(cartItem.getQuantity() > product.getStock()){
                return ServerResponse.createByErrorMessage("库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            //总价=数量*单价
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItemList.add(orderItem);



        }
        return ServerResponse.createBySuccess(orderItemList);
    }


}
