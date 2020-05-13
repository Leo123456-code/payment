package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;

/**
 * ClassName: IOrderService
 * Description: TODO 订单接口层
 * Author: Leo
 * Date: 2020/3/20-20:54
 * email 1437665365@qq.com
 */
public interface IOrderService {
    //创建订单
    ServerResponse createOrder(Integer userId,Integer shippingId) throws Exception;
    //取消订单
    ServerResponse<String> cancel(Integer userId,Long orderNo)throws Exception;
    //获取购物车已经选中的商品详情
    ServerResponse getOrderCartProductDetail(Integer userId);
    //订单详情
    ServerResponse getOrderDetail(Integer userId,Long orderNo);
    //根据UserId查询所有订单,分页显示
    ServerResponse getOrderList(Integer userId,int pageNum,int pageSize);
    //后台
    //backend
    //后台分页查询订单列表
    ServerResponse manageList(int pageNum,int pageSize);
    //详情
    ServerResponse manageDetail(Long orderNo);
    //后台搜索,精确匹配
    ServerResponse manageSearch(Long orderNo,int pageNum,int pageSize);
    //发货
    ServerResponse<String> manageSendGoods(Long orderNo);
}
