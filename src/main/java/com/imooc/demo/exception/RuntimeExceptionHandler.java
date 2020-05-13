package com.imooc.demo.exception;

import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: RuntimeExceptionHandler
 * Description: TODO 异常处理类
 * Author: Leo
 * Date: 2020/3/21-20:06
 * email 1437665365@qq.com
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ServerResponse<Object> handle(RuntimeException e) {

        return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT,
                e.getMessage());
    }

}
