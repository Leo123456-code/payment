package com.imooc.demo.web.backend;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.form.UserLoginForm;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: UserManageConroller
 * Description: TODO用户后台管理
 * Author: Leo
 * Date: 2020/3/11-19:58
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/manage/user")
public class UserManageConroller {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(HttpSession session, @RequestBody UserLoginForm form){
        ServerResponse<User> response = userService.login(form.getUsername(), form.getPassword());
        if(response.isSuccess()){
            User user = response.getData();
            if (user.getRole().equals(Const.Role.ROLE_ADMIN)){
                //说明是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;

    }
}
