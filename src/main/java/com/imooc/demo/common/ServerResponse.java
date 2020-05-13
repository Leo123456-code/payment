package com.imooc.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;

/**
 * ClassName: ServerResponse
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/9-22:14
 * email 1437665365@qq.com
 */
@Data
//保证序列化json的时候,如果是null的对象,key也会消失
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = -6947728447369537248L;

    private Integer status;
    
    private String msg;
    
    private T data;

    public ServerResponse(Integer status) {
        this.status = status;
    }

    public ServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }



    /**
     * @Description //成功返回的状态
       @Author Leo
      *@Date 22:25 2020/3/9
     * @Param []
     * @return boolean
     */
    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){

        return this.status == ResponseCode.SUCCESS.getCode();
    }
    //成功
    public static<T> ServerResponse<T> createBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }

    public static<T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static<T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
    }


    public static<T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }



    //失败
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorCodeMessage){
        return new ServerResponse<>(errorCode,errorCodeMessage);
    }

    public static<T> ServerResponse createByErrorCodeMessageOf(Integer code,String msg) {
        return new ServerResponse(ResponseCode.ERROR.getCode(),msg);
    }

    public static<T> ServerResponse createByError(ResponseCode error, String msg) {
        return new ServerResponse(ResponseCode.ERROR.getCode(),msg);
    }

}
