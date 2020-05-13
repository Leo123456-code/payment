package com.imooc.demo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: CartProductVo
 * Description: TODO结合了产品和购物车的一个抽象对象
 * Author: Leo
 * Date: 2020/3/15-21:40
 * email 1437665365@qq.com
 */
@Data
public class CartProductVo {

    private Integer id;

    private Integer userId;

    private Integer productId;
    //购物车中此商品的数量
    private Integer quantity;
    //产品名称
    private String productName;
    //商品副标题
    private String productSubtitles;
    //商品主图
    private String productMainImage;
    //商品价格
    private BigDecimal productPrice;
    //商品状态
    private Integer productStatus;
    //总价
    private BigDecimal productTotalPrice;
    //库存
    private Integer productStock;
    //此商品是否勾选
    private Integer productChecked;
    //限制数量的一个返回结果
    private String limitQuantity;

}
