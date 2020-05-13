package com.imooc.demo.vo;

import com.imooc.demo.pojo.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: OrderVo
 * Description: TODO 订单Vo
 * Author: Leo
 * Date: 2020/3/20-22:17
 * email 1437665365@qq.com
 */
@Data
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;
    //运费
    private Double postage;

    private Integer status;

    private String statusDesc;

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;
    //订单明细
    private List<OrderItemVo> orderItemVoList;
    //图片地址
    private String imageHost;
    //收货id
    private Integer shippingId;
    //收货地址详情
    private ShippingVo shippingVo;

    private String receiverName;
}
