package com.imooc.demo.service.impl;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.CartMapper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.pojo.Cart;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.service.ICartService;
import com.imooc.demo.util.BigDecimalUtil;
import com.imooc.demo.util.PropertiesUtil;
import com.imooc.demo.vo.CartProductVo;
import com.imooc.demo.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: CartServiceImpl
 * Description: TODO购物车实现类
 * Author: Leo
 * Date: 2020/3/15-21:09
 * email 1437665365@qq.com
 */
@Slf4j
@Service
@Transactional
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId) {
        /**
         * @Description //TODO 新增购物车里商品
         @Author Leo
          * @Date 21:16 2020/3/15
         * @Param [userId, count, prodtId]
         * @return com.imooc.demo.common.ServerResponse
         */
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个产品不在这个购物车里,需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);

            log.info("userId={}", userId);
            int rowCount = cartMapper.insert(cartItem);

            log.info("cartItem={}", gson.toJson(cartItem));
        } else {
            //这个产品已经在购物车里,如果产品已存在,数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cart.setId(cart.getId());
            int rowCount = cartMapper.updateByPrimaryKeySelective(cart);
        }

        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId) {
        /**
         * @Description //TODO 更新购物车商品的数量
         @Author Leo
          * @Date 15:10 2020/3/16
         * @Param [userId, count, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
         */
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        //根据userId 和 productId查询购物车
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        //修改购物车入库
        int rowCount = cartMapper.updateByPrimaryKeySelective(cart);
        if (rowCount == 0) {
            return ServerResponse.createByErrorMessage("修改购物车失败");
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> delete(Integer userId, String productIds) {
        /**
         * @Description //TODO 删除购物车的商品
         @Author Leo
          * @Date 18:09 2020/3/16
         * @Param [userId, productIds]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
         */
        //用逗号分隔字符串变成集合的字段添加到集合中
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        int rowCount = cartMapper.deleteByUserIdProductIds(userId, productList);
        if (rowCount == 0) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId, Integer productId) {
        /**
         * @Description //TODO 查询购物车,展示购物车所有商品CartVo
         @Author Leo
          * @Date 10:41 2020/3/17
         * @Param [userId, productId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
         */

        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, Integer checked) {
        /**
         * @Description //TODO 全选或全不选
           @Author Leo
         * @Date 20:10 2020/3/17
         * @Param [userId, checked]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        int rowCount = cartMapper.checkOrUnchechedProduct(userId, checked);
        if(rowCount == 0){
            return ServerResponse.createByErrorMessage("全选或全不选操作失败");
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnselectOne(Integer userId, Integer productId, Integer checked) {
        /**
         * @Description //TODO 单独选,单独反选
           @Author Leo
         * @Date 20:52 2020/3/17
         * @Param [userId, productId, checked]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.vo.CartVo>
        */
        int rowCount = cartMapper.checkOneOrUnchechedAll(userId, productId, checked);
        if(rowCount == 0){
           return ServerResponse.createByErrorMessage("操作失败");
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<Integer> selectCartProductCount(Integer userId) {
        /**
         * @Description //TODO 计算购物车商品的数量
           @Author Leo
         * @Date 21:20 2020/3/17
         * @Param [userId]
         * @return com.imooc.demo.common.ServerResponse<java.lang.Integer>
        */
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }


    //封装Vo
    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        //总价
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(!CollectionUtils.isEmpty(cartList)){
            for (Cart cart : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setProductId(cart.getProductId());
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitles(product.getSubImages());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductPrice(product.getPrice());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cart.getQuantity()){
                        //库存充足时
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cart.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        int rowCount = cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价 单价*数量
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                            cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cart.getChecked());
                }
                if(cart.getChecked() == Const.Cart.CHECKED){
                    log.info("cartTotalPrice={}",cartTotalPrice);
                    //如果已经勾选增加到购物车总价当中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
                            cartProductVo.getProductTotalPrice().doubleValue());

                }
                //购物车CartProductVoList添加
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    //是否全选
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
//        如果有一个,表示不是全选,返回true
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
