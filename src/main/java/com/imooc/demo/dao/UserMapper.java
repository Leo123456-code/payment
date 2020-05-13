package com.imooc.demo.dao;

import com.imooc.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByOnUserName(@Param("username")String username);

    int updateByPrimaryKey(User record);
    //查询名字是否存在
    int checkUsername(@Param("username")String username);
    //查询姓名和密码是否存在
    User selectLogin(@Param("username") String username,@Param("password") String password);
    //查询邮箱是否存在
    int checkEmail(String email);
    //将注册信息存入数据库
    int insert(User record);
    //查找问题并返回
    String questionByUsernameAndEmail(@Param("username") String username,@Param("email") String email);
    //查找答案个数是否大于0
    int selectQuestionAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);
    //根据用户姓名修改密码
    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);
    //修改密码
    int updateByPrimaryKeySelective(User record);
    //根据userId和password查询是否存在一个
    int selectByUserIdAndPassword(@Param("userId") Integer userId,@Param("password") String password);
    //根据userId和email查找是否存在一个
    int selectByEmailAndUserId(@Param("email") String email,@Param("userId") Integer userId);
    //根据id获取信息
    User getFormationByUserId(@Param("userId") Integer userId);


}