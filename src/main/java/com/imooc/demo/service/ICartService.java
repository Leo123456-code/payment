package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.vo.CartProductVo;
import com.imooc.demo.vo.CartVo;

/**
 * @program: miaosha
 * @ClassName: ICartService
 * @Description: TODO 购物车接口
 * @Author: Leo
 * @Date: 2020/3/15-21:08
 */
public interface ICartService {
    //TODO新增购物车
    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);
    //更新购物车
    ServerResponse<CartVo> update(Integer userId,Integer count, Integer productId);
    //删除购物车(可删除多个)
    ServerResponse<CartVo> delete(Integer userId,String productIds);
    //查询购物车
    ServerResponse<CartVo> list(Integer userId,Integer productId);
    //全选或全不选
    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, Integer checked);
    //单独选或单独反选
    ServerResponse<CartVo> selectOrUnselectOne(Integer userId,Integer productId,Integer checked);
    //查询当前用户的购物车里面的产品数量
    ServerResponse<Integer> selectCartProductCount(Integer userId);


}
