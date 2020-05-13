package com.imooc.demo.common;


import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

/**
 * ClassName: Const
 * Description: TODO常量
 * Author: Leo
 * Date: 2020/3/9-22:41
 * email 1437665365@qq.com
 */
@Data
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final  String USERNAME = "username";

    public static final  String EMAIL = "email";

    //登录时间有效期
    public interface RedisCacheExtime{
        //24小时
        int REDIS_SESSION_EXTIME = 60 * 60 * 24;
    }

    public interface Role{
        int ROLE_CUSTOMER = 1 ;//普通用户
        int ROLE_ADMIN = 0 ;//管理员
    }

    //排序
    public interface ProductListOrderBy{
        //价格排序
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
        //修改时间排序
        Set<String> UPDATETIME_ASC_DESC = Sets.newHashSet("update_time_desc","update_time_asc");
    }

    public interface Cart{
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";//库存不够
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";//库存充足
    }


    @Getter
    @AllArgsConstructor
    public enum ProductStatusEnum{
        ON_SALE(1,"在售"),
        ON_PULL(2,"下架"),
        ;

        private Integer code;

        private String message;
    }

    //订单状态枚举
    @AllArgsConstructor
    @Getter
    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPING(40,"已发货"),
        ORDER_SUCCESS(50,"订单已完成"),
        ORDER_CLOSE(60,"订单关闭"),
        ;
        private Integer code;

        private String message;
        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    @AllArgsConstructor
    @Getter
    //支付状态枚举
    public enum PayPlatforEnum{
        ALIPAY(1,"支付宝"),
        ;
        private Integer code;

        private String message;

    }

    //支付方式
    @AllArgsConstructor
    @Getter
    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付"),
        ;
        private Integer code;

        private String message;

        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }

    }



    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAIL = "failed";
    }


}
