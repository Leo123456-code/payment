package com.imooc.demo.service.impl;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.common.TokenCache;
import com.imooc.demo.dao.UserMapper;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IUserService;
import com.imooc.demo.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * ClassName: UserServiceImpl
 * Description: TODO用户实现类
 * Author: Leo
 * Date: 2020/3/9-23:06
 * email 1437665365@qq.com
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        /**
         * @Description //TODO 登录实现类
           @Author Leo
         * @Date 23:17 2020/3/9
         * @Param [username, password]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.User>
        */
        int resultCount = userMapper.checkUsername(username);

        if(resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户姓名不存在");
        }
        ////TODO 密码加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户或者密码错误");
        }
        ////TODO
        user.setPassword("");

        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<String> checkVaild(String str, String type) {
        /**
         * @Description //TODO 校验接口参数是否合法
         * //当用户不存在时返回校验成功
           @Author Leo
         * @Date 23:50 2020/3/9
         * @Param [str, type]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int emailCount = userMapper.checkEmail(str);
                if(emailCount > 0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccess("校验成功");
    }

    @Override
    public ServerResponse<String> reigster(User user) {
        /**
         * @Description //TODO 注册
           @Author Leo
         * @Date 0:07 2020/3/10
         * @Param [user]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        ServerResponse<String> response = this.checkVaild(user.getUsername(), Const.USERNAME);
        if(!response.isSuccess()){
            return response;
        }
        response = this.checkVaild(user.getEmail(),Const.EMAIL);
        if(!response.isSuccess()){
            return response;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //密码加密
       user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int selectiveCount = userMapper.insert(user);
        if (selectiveCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String username,String email) {
        ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
        if(response.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在,请注册");
        }
        String question = userMapper.questionByUsernameAndEmail(username, email);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }

        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public ServerResponse<String> selectAnswer(String username, String question, String answer) {
        /**
         * @Description //TODO 找回密码答案
           @Author Leo
         * @Date 0:56 2020/3/10
         * @Param [username, question, answer]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */

        int resultCount = userMapper.selectQuestionAnswer(username, question, answer);
        if(resultCount > 0 ){
            //说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            //设置缓存
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案是错误的");
    }

    @Override
    public ServerResponse<String> resetAnswer(String username, String passwordNew, String forgetToken) {
        /**
         * @Description //TODO 重置密码
           @Author Leo
         * @Date 2:14 2020/3/10
         * @Param [username, passwordNew, forgetToken]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,Token 需要传递");
        }
        ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
        if(response.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在,请注册");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("Token 无效或过期");
        }
        if(StringUtils.equals(token,forgetToken)){
            String md5Passord = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCont = userMapper.updatePasswordByUsername(username, md5Passord);
            if(resultCont > 0){
                return ServerResponse.createBySuccess("修改成功");
            }
        }else {
            return ServerResponse.createByErrorMessage("Token 错误");
        }
        return ServerResponse.createByErrorMessage("修改密碼失敗");
    }

    @Override
    public ServerResponse<String> loginResetPassword(String passwordNew, String passwordOld, User user) {
        /**
         * @Description //TODO 登陆状态重置密码
           @Author Leo
         * @Date 2:26 2020/3/10
         * @Param [passwordNew, passwordOld, user]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */

        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.
        // 因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.selectByUserIdAndPassword(user.getId(),
                MD5Util.MD5EncodeUtf8(passwordOld));
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess("更新成功");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        /**
         * @Description //TODO 更新用户信息
           @Author Leo
         * @Date 2:47 2020/3/10
         * @Param [user]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,
        // 并且存在的email如果相同的话,不能是我们当前的这个用户的.
        int select = userMapper.selectByEmailAndUserId(user.getEmail(), user.getId());
        if(select > 0){
            return ServerResponse.createByErrorMessage("存在相同的邮箱,请更换");
        }
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setQuestion(user.getQuestion());
        user.setAnswer(user.getAnswer());
        int selectiveCount = userMapper.updateByPrimaryKeySelective(user);
        if(selectiveCount == 0 ){
            return ServerResponse.createByErrorMessage("更新信息失败");
        }
        return ServerResponse.createBySuccess("更新信息成功",user);
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        /**
         * @Description //TODO 获取用户登录状态信息
           @Author Leo
         * @Date 3:26 2020/3/10
         * @Param [userId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.User>
        */
        User user = userMapper.getFormationByUserId(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        /**
         * @Description //TODO 校验是否为管理员
           @Author Leo
         * @Date 3:38 2020/3/10
         * @Param [user]
         * @return com.imooc.demo.common.ServerResponse
        */
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
           return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

}
