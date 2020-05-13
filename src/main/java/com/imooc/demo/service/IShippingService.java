package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.form.ShippingAddForm;
import com.imooc.demo.pojo.Shipping;

/**
 * @program: miaosha
 * @ClassName: IShippingService
 * @Description: TODO 收货地址接口层
 * @Author: Leo
 * @Date: 2020/3/17-22:14
 */
public interface IShippingService {
    //添加收货地址
    ServerResponse add(Integer userId, ShippingAddForm form);
    //删除收货地址
    ServerResponse<String> del(Integer userId, Integer shippingId);
    //修改收货地址
    ServerResponse update(Integer userId, Shipping shipping);
    //查询收货地址
    ServerResponse select(Integer userId, Integer shippingId);
    //查询当前用户所有收货地址详情list
    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);

}
