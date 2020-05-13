package com.imooc.demo.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: PayPlatformEnum
 * Description: TODO 支付平台枚举
 * Author: Leo
 * Date: 2020/3/22-19:15
 * email 1437665365@qq.com
 */
@AllArgsConstructor
@Getter
public enum PayPlatformEnum {
    ALIPAY(1),
    WX(2),
    ;
    private Integer code;


    //支付方式
    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
//        if (bestPayTypeEnum.getPlatform().name().equals(PayPlatformEnum.ALIPAY.name())) {
//            return PayPlatformEnum.ALIPAY;
//        } else if (bestPayTypeEnum.getPlatform().name().equals(PayPlatformEnum.WX.name())) {
//            return PayPlatformEnum.WX;
//        }
        for (PayPlatformEnum payPlatforEnum : PayPlatformEnum.values()) {
            //如果支付方式中支付平台名字相同,则返回支付平台的,否则抛出异常
            if (bestPayTypeEnum.getPlatform().name().equals(payPlatforEnum.name())) {
                return payPlatforEnum;
            }
        }
        throw new RuntimeException("错误的支付平台:" + bestPayTypeEnum.name());
    }
}
