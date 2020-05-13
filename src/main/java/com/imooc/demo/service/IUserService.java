package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;

/**
 * @program: miaosha
 * @ClassName: IUserService
 * @Description: TODO用户接口
 * @Author: Leo
 * @Date: 2020/3/9-22:11
 */
public interface IUserService {
    //登录接口
    ServerResponse<User> login(String username,String password);
    //校验接口传入参数
    ServerResponse<String> checkVaild(String str,String type);
    //注册
    ServerResponse<String> reigster(User user);
    //将密码返回给前端
    ServerResponse<String> forgetGetQuestion(String username,String email);
    //找回密码
    ServerResponse<String> selectAnswer(String username,String question,String answer);
    //重置密码
    ServerResponse<String> resetAnswer(String username,String passwordNew,String forgetToken);
    //登錄狀態重置密碼
    ServerResponse<String> loginResetPassword(String passwordNew,String passwordOld,User user);
    //更新用户信息
    ServerResponse<User> updateInformation(User user);
    //获取用户登录状态信息
    ServerResponse<User> getInformation(Integer userId);
    //校验是否是管理员
    ServerResponse checkAdminRole(User user);


}
