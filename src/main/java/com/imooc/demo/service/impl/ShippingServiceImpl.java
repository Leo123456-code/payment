package com.imooc.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.ShippingMapper;
import com.imooc.demo.form.ShippingAddForm;
import com.imooc.demo.pojo.Shipping;
import com.imooc.demo.service.IShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShippingService
 * Description: TODO 收货地址实现类
 * Author: Leo
 * Date: 2020/3/17-22:15
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId,@Valid ShippingAddForm form) {
        /**
         * @Description //TODO 添加收货地址
           @Author Leo
         * @Date 21:41 2020/3/18
         * @Param [userId, form]
         * @return com.imooc.demo.common.ServerResponse
        */
        form.setUserId(userId);
        int rowCount = shippingMapper.insert(form);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId",form.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        /**
         * @Description //TODO 删除收货地址
           @Author Leo
         * @Date 21:41 2020/3/18
         * @Param [userId, shippingId]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        //防止横向越权,把别人的地址给删除了
        int rowCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        /**
         * @Description //TODO 修改收货地址
           @Author Leo
         * @Date 21:41 2020/3/18
         * @Param [userId, shipping]
         * @return com.imooc.demo.common.ServerResponse
        */
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShippingIdUserId(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改地址成功");
        }
        return ServerResponse.createByErrorMessage("修改地址失败");
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
        /**
         * @Description //TODO 查询收货地址
           @Author Leo
         * @Date 21:44 2020/3/18
         * @Param [userId, shippingId]
         * @return com.imooc.demo.common.ServerResponse
        */
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该收货地址");
        }
        return ServerResponse.createBySuccess("查询成功",shipping);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
        /**
         * @Description //TODO 查询当前用户所有收货地址详情list
           @Author Leo
         * @Date 21:59 2020/3/18
         * @Param [userId, pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse
        */
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo<Shipping> pageInfo = new PageInfo<>(shippingList);
        pageInfo.setList(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
