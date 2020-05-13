package com.imooc.demo.service.impl;


import com.google.common.collect.Lists;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.CategoryMapper;
import com.imooc.demo.pojo.Category;
import com.imooc.demo.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * ClassName: CategoryServiceImpl
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/10-21:19
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        /**
         * @Description //TODO 增加分类管理
           @Author Leo
         * @Date 21:40 2020/3/10
         * @Param [categoryName, parentId]
         * @return com.imooc.demo.common.ServerResponse
        */
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加分类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的
        int resultCount = categoryMapper.insert(category);
        if(resultCount == 0){
           return ServerResponse.createByErrorMessage("添加分类管理失败");
        }
        return ServerResponse.createBySuccess("添加分类管理成功");
    }

    @Override
    public ServerResponse updateCategoryName(String categoryName, Integer categoryId) {
        /**
         * @Description //TODO 修改分类管理名称
           @Author Leo
         * @Date 22:08 2020/3/10
         * @Param [categoryName, parentId]
         * @return com.imooc.demo.common.ServerResponse
        */
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("修改分类管理失败");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParalleCategory(Integer categoryId) {
        /**
         * @Description //TODO  //查询子节点的category信息,并且不递归,保持平级
           @Author Leo
         * @Date 22:32 2020/3/10
         * @Param [categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategoryId(Integer categoryId) {
        /**
         * @Description //TODO 递归查询子节点的category信息
           @Author Leo
         * @Date 23:10 2020/3/10
         * @Param [categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        //初始化
        Set<Category> categorySet = new HashSet<>();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        //排序
        Collections.sort(categoryIdList,(o1, o2)->{
            return o1-o2;
        });
        return ServerResponse.createBySuccess(categoryIdList);
    }

    @Override
    public ServerResponse<List<Category>> getCategoryAndDeepChildrenCategory(Integer categoryId) {
        /**
         * @Description //TODO 查询当前节点的category信息和递归子节点的category信息
           @Author Leo
         * @Date 0:43 2020/3/11
         * @Param [categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        //方法一
        //保存所有的category信息集合
        Set<Category> categorySet = new HashSet<>();
        findChildCategory(categorySet,categoryId);

        //排序
        List<Category> list = new ArrayList<>(categorySet);
        Collections.sort(list,(o1, o2) -> {
            return o1.getId()-o2.getId();
        });

        log.info("2.categorySet={}",categorySet);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Category>> getCategoryChildrenCategory(Integer categoryId) {
        /**
         * @Description //TODO //查询当前节点的category信息和递归子节点的category信息方法2 此方法效率高
           @Author Leo
         * @Date 4:00 2020/3/11
         * @Param [categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        List<Category> categoryList = categoryMapper.selectALL();
        //按照id组建一个map查找集合
        Map<Integer,Category> idMap = new HashMap<>();
        for (Category category : categoryList) {
            idMap.put(category.getId(),category);
        }
        //按照parentId组建一个map查找集合
        HashMap<Integer, List<Category>> parentIdMap = new HashMap<>();
        for (Category category : categoryList) {
            List<Category> categories =null;
            //判断Map 中是否存在key
            if(!parentIdMap.containsKey(category.getParentId())){
                categories =new ArrayList<>();
            }else {
                categories = parentIdMap.get(category.getParentId());
            }
            categories.add(category);
            parentIdMap.put(category.getParentId(),categories);
        }
        //获得去重的结果集categorySet
        Set<Category> categorySet = new HashSet<>();
        findCategorySet(parentIdMap,idMap,categorySet,categoryId);

        //排序
        List<Category> list = new ArrayList<>(categorySet);
        Collections.sort(list,(o1, o2) -> {
            return o1.getId()-o2.getId();
        });
        log.info("categorySet={}",list);
        return ServerResponse.createBySuccess(list);
    }

    //递归算法，算出子节点
    private Set<Category> findCategorySet(HashMap<Integer, List<Category>> parentIdMap, Map<Integer, Category> idMap, Set<Category> categorySet, Integer categoryId) {
        //获取所有第一层ParentId List
        List<Category> categoryList = getCategoryListOfByParentId(parentIdMap,categoryId);
        //结束条件
        if(categoryList == null || categoryList.size() <= 0){
            return categorySet;
        }
        //开始循环遍历
        for (Category category : categoryList) {
            categorySet.add(category);
        }
        for (Category categoryItem : categoryList) {
            findCategorySet(parentIdMap,idMap,categorySet,categoryItem.getId());
        }
        //获取其它层节点
        return categorySet;
    }

    //按照parentId去parentIdMap查找CategoryList
    private List<Category> getCategoryListOfByParentId(Map<Integer, List<Category>> parentIdMap, Integer parentId) {
        return parentIdMap.get(parentId);
    }

    //递归算法，算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出条件
        //1.查询子节点的category信息,并且不递归,保持平级
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet,categoryItem.getId());
        }
        log.info("1.categorySet={}",categorySet);

        return categorySet;
    }
}
