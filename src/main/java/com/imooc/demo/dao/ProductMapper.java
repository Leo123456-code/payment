package com.imooc.demo.dao;

import com.imooc.demo.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    //查询所有的商品
    List<Product> findAllProductList();
    //根据姓名或productId进行搜素
    List<Product> selcetByNameAndProductId(@Param("name") String productName,@Param("id")Integer productId);
    //根据姓名或categoryIdList进行搜素
    List<Product> selectByNameAndCategoryIds(@Param("name") String productName,@Param("categoryIdList") List<Integer> categoryIdList);
    //根据姓名或categoryIdList进行搜素 SQL进行动态排序
    List<Product> selectByNameAndCategoryIdsOrderBy(@Param("name") String productName,@Param("categoryIdList") List<Integer> categoryIdList,@Param("orderBy") String orderBy,@Param("orderType")String orderType);


}