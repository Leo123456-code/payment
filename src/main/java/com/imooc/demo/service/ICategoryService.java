package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Category;

import java.util.List;

/**
 * ClassName: ICategoryService
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/10-21:19
 * email 1437665365@qq.com
 */
public interface ICategoryService {
    //增加分类管理
    ServerResponse addCategory(String categoryName,Integer parentId);
    //修改分类管理名字
    ServerResponse updateCategoryName(String categoryName,Integer categoryId);
    //查询子节点的category信息,并且不递归,保持平级
    ServerResponse<List<Category>> getChildrenParalleCategory(Integer categoryId);
    //递归查询子节点的categoryId信息
    ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategoryId(Integer categoryId);
    //查询当前节点的category信息和递归子节点的category信息
    ServerResponse<List<Category>> getCategoryAndDeepChildrenCategory(Integer categoryId);
    //查询当前节点的category信息和递归子节点的category信息方法2 此方法效率高
    ServerResponse<List<Category>> getCategoryChildrenCategory(Integer categoryId);

}
