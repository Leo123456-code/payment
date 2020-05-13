package com.imooc.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: OrderProductVo
 * Description: TODO 购物车已经选中的商品详情
 * Author: Leo
 * Date: 2020/3/21-12:31
 * email 1437665365@qq.com
 */
@Data
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;
}
