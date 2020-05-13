package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @program: miaosha
 * @ClassName: IPayService
 * @Description: TODO 支付接口层
 * @Author: Leo
 * @Date: 2020/3/22-21:47
 */
public interface IPayService {
    /**
     * 创建支付
     */
    PayResponse create(String orderId, BigDecimal amount,
                       BestPayTypeEnum bestPayTypeEnum);
    /**
     * 异步通知处理
     * @return
     */
    String asyncNotify(String notifyData);
    /**
     * 通过订单号查询支付记录
     */
    PayInfo queryByOrderId(String orderId);


}
