package com.imooc.demo.dao;

import com.imooc.demo.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);


    List<Category> selectALL();
    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);



    int updateByPrimaryKey(Category record);
    //添加分类管理
    int insert(Category record);
    //修改分类管理名字
    int updateByPrimaryKeySelective(Category record);
    //根据categoryId获得子节点的category信息
    List<Category> selectCategoryChildrenByParentId(Integer parentId);

}