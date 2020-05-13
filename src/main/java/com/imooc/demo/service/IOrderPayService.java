package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;

import java.util.Map;

/**
 * @program: miaosha
 * @ClassName: IOrderService
 * @Description: TODO 订单支付接口层
 * @Author: Leo
 * @Date: 2020/3/19-15:37
 */
public interface IOrderPayService {
    //支付
    ServerResponse pay(Integer userId,Long orderNo,String path);
    //支付回调
    ServerResponse alipayCallback(Map<String,String> params);
    //查询是否支付
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

}
