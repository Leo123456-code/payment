package com.imooc.demo.dao;

import com.imooc.demo.form.ShippingAddForm;
import com.imooc.demo.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShippingAddForm record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
    //删除收货地址
    int deleteByShippingIdUserId(@Param("userId") Integer userId,@Param("shippingId") Integer shippingId);
    //修改收货地址
    int updateByShippingIdUserId(Shipping shipping);
    //查询收货地址
    Shipping selectByShippingIdUserId(@Param("userId")Integer userId,@Param("shippingId")Integer shippingId);
    //查询当前用户所有收货地址详情list
    List<Shipping> selectByUserId(@Param("userId") Integer userId);
}