package com.imooc.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: ResponseCode
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/9-22:17
 * email 1437665365@qq.com
 */
@AllArgsConstructor
@Getter
public enum  ResponseCode {

    SUCCESS(0,"成功"),
    ERROR(1, "失败"),
    NEED_LOGIN(10, "需要登录"),
    ILLEGAL_ARGUMENT(2, "参数错误"),
        ;
    private final Integer code;

    private final String message;



}
