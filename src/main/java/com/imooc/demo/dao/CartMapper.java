package com.imooc.demo.dao;

import com.imooc.demo.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    //根据userId 和 productId查询购物车
    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    //根据userId 查询购物车
    List<Cart> selectCartByUserId(@Param("userId") Integer userId);
    //根据userId 和 productId查询购物车集合
    List<Cart> selectListByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    //根据userId查询有没有未勾选
    //如果有一个,表示不是全选
    int selectCartProductCheckedStatusByUserId(@Param("userId") Integer userId);
    //删除购物车商品根据userId和productIds
    int deleteByUserIdProductIds(@Param("userId")Integer userId,@Param("productIdList") List<String> productIdList);
    //全选或全不选
    int checkOrUnchechedProduct(@Param("userId") Integer userId,@Param("checked") Integer checked);
    //单独选或单独反选
    int checkOneOrUnchechedAll(@Param("userId") Integer userId,@Param("productId") Integer productId,@Param("checked") Integer checked);
    //计算购物车产品的数量
    int selectCartProductCount(Integer userId);
    //从购物车获取已经勾选的商品
    List<Cart> selectCheckCartByUserId(@Param("userId")Integer userId);

}