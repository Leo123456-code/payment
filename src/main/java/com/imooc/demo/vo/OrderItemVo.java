package com.imooc.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: OrderItemVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/20-22:23
 * email 1437665365@qq.com
 */
@Data
public class OrderItemVo {
    //订单号
    private Long orderNo;
    //'商品id',
    private Integer productId;
    //商品名称',
    private String productName;
    //'商品图片地址',
    private String productImage;
    //生成订单时的商品单价，单位是元,保留两位小数',
    private BigDecimal currentUnitPrice;
    //'商品数量',
    private Integer quantity;
    //'商品总价,单位是元,保留两位小数',
    private BigDecimal totalPrice;
    //创建时间',
    private Date createTime;


}
