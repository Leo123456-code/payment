package com.imooc.demo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: UserLoginForm
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/11-20:03
 * email 1437665365@qq.com
 */
@Data
public class UserLoginForm {
    @NotBlank(message = "名字不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

}
