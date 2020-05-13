package com.imooc.demo.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: ProductDetailVo
 * Description: TODO商品详情VO
 * Author: Leo
 * Date: 2020/3/11-20:53
 * email 1437665365@qq.com
 */
@Data
public class ProductDetailVo {
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
    //'图片地址,json格式,扩展用',
    private String subImages;
    // '商品详情',
    private String detail;
    //'价格,单位-元保留两位小数',
    private BigDecimal price;
    // '库存数量',
    private Integer stock;
    //'商品状态.1-在售 2-下架 3-删除',
    private Integer status;
    //'创建时间',
    private Date createTime;
    //'更新时间',
    private Date updateTime;


    //图片服务器前缀
    private String imageHost;
    //父分类
    private Integer parentCategoryId;

}
