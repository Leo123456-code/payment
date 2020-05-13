package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.vo.CartProductVo;

/**
 * ClassName: ICartAddService
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/17-11:25
 * email 1437665365@qq.com
 */
public interface ICartAddService {
    //查询购物车里单个商品并显示
    ServerResponse<CartProductVo> onlyOne(Integer userId, Integer productId);

}
