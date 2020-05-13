package com.imooc.demo.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Cart {
    //主键
    private Integer id;
    //用户id
    private Integer userId;
    // '商品id',
    private Integer productId;
    // 数量',
    private Integer quantity;
    //'是否选择,1=已勾选,0=未勾选',
    private Integer checked;
    //'创建时间',
    private Date createTime;
    // 更新时间',
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}