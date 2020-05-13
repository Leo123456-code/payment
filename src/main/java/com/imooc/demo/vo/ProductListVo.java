package com.imooc.demo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: ProductListVo
 * Description: TODO 查询所有商品VO
 * Author: Leo
 * Date: 2020/3/11-23:00
 * email 1437665365@qq.com
 */
@Data
public class ProductListVo {

    //'商品id',
    private Integer id;
    //'分类id,对应 mmall_category表的主键',
    private Integer categoryId;
    //'商品名称',
    private String name;
    //'商品副标题',
    private String subtitle;
    //'产品主图,url相对地址',
    private String mainImage;

    //'价格,单位-元保留两位小数',
    private BigDecimal price;
    //'商品状态.1-在售 2-下架 3-删除',
    private Integer status;


    //图片服务器前缀
    private String imageHost;


}
