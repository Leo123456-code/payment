package com.imooc.demo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: ShippingAddForm
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/18-20:17
 * email 1437665365@qq.com
 */
@Data
public class ShippingAddForm {
    private Integer id;
    //'用户id',
    private Integer userId;
    //'收货姓名',
    private String receiverName;
    //'收货固定电话',
    @NotBlank(message = "联系电话不能为空")
    private String receiverPhone;
    //'收货移动电话',
    private String receiverMobile;
    //'省份',
    private String receiverProvince;
    //'城市',
    private String receiverCity;
    //'区/县',
    private String receiverDistrict;
    //'详细地址',
    private String receiverAddress;
    //'邮编',
    private String receiverZip;
}
