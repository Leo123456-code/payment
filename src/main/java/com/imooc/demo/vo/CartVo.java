package com.imooc.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ClassName: CartVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/15-21:49
 * email 1437665365@qq.com
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    //总价
    private BigDecimal cartTotalPrice;
    //是否全选
    private Boolean allChecked;
    //商品图片地址
    private String imageHost;
}
