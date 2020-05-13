package com.imooc.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PayInfo {

    private Integer id;
    //'用户id',
    private Integer userId;
    //'订单号',
    private Long orderNo;
    //支付金额
    private BigDecimal payAmount;
    //'支付平台:1-支付宝,2-微信',
    private Integer payPlatform;
    //'支付宝支付流水号',
    private String platformNumber;
    //支付宝支付状态',
    private String platformStatus;
    //创建时间',
    private Date createTime;
    //更新时间'
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

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus == null ? null : platformStatus.trim();
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

    public PayInfo() {
    }

    public PayInfo(Long orderNo, BigDecimal payAmount, Integer payPlatform, String platformStatus) {
        this.orderNo = orderNo;
        this.payAmount = payAmount;
        this.payPlatform = payPlatform;
        this.platformStatus = platformStatus;
    }
}