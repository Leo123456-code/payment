package com.imooc.demo.dao;

import com.imooc.demo.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    //根据userId 和orderNo查询
    List<OrderItem> getByOrderNOAndUserId(@Param("userId") Integer userId,@Param("orderNo") Long orderNo);
    //批量插入
    void bachInsert(@Param("orderItemList") List<OrderItem> orderItemList);
    // 根据orderNo查询
    List<OrderItem> getByOrderNo(@Param("orderNo") Long orderNo);
}