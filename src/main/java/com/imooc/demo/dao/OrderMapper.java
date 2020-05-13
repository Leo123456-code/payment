package com.imooc.demo.dao;

import com.imooc.demo.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    //根据userId和orderNo查询订单
    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId,@Param("orderNo") Long orderNo);
    //
    int selectByUserIdAndOrderNoCount(@Param("userId") Integer userId,@Param("orderNo") Long orderNo);
    //orderNo查询订单
    Order selectByOrderNo(@Param("orderNo") Long orderNo);
    //根据userId查询所有的订单
    List<Order> selectByUserId(@Param("userId") Integer userId);
    //后台查询所有
    List<Order> selectAllOrder();
}