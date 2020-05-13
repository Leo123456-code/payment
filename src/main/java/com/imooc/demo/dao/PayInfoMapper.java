package com.imooc.demo.dao;

import com.imooc.demo.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
    //根据订单号查找
    PayInfo selectByOrderNo(@Param("id") Long orderNo);
}