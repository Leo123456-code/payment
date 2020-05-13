package com.imooc.demo.vo;

import com.imooc.demo.pojo.Shipping;
import lombok.Data;

/**
 * ClassName: ShippingVo
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/20-22:29
 * email 1437665365@qq.com
 */
@Data
public class ShippingVo {

    //'收货姓名',
    private String receiverName;
    //'收货固定电话',
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
