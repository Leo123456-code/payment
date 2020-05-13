package com.imooc.demo.web.backend;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Category;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.ICategoryService;
import com.imooc.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * ClassName: CategoryManageController
 * Description: TODO后台分类管理
 * Author: Leo
 * Date: 2020/3/10-21:17
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "add_category.do",method = RequestMethod.GET)
    public ServerResponse<Category> addCategory(HttpSession session,String categoryName,
                                  @RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        /**
         * @Description //TODO 添加分类操作
           @Author Leo
         * @Date 21:46 2020/3/10
         * @Param [session, categoryName, parentId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.Category>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //增加处理分类操作的逻辑
            return categoryService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }

    }


    @RequestMapping(value = "set_category_name.do",method = RequestMethod.GET)
    public ServerResponse<Category> setCategory(HttpSession session,String categoryName, Integer categoryId){
        /**
         * @Description //TODO 更新分类管理名字
           @Author Leo
         * @Date 22:05 2020/3/10
         * @Param [session, categoryName, parentId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.Category>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //增加处理分类操作的逻辑
            return categoryService.updateCategoryName(categoryName,categoryId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }
    }


    @RequestMapping(value = "get_category.do",method = RequestMethod.GET)
    public ServerResponse<List<Category>> getChildrenParalleCategory(HttpSession session,
                                                                     @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        /**
         * @Description //TODO 获取子节点并且是平级的节点信息  如果categoryId 没传,默认采用根节点0
           @Author Leo
         * @Date 22:22 2020/3/10
         * @Param [session, categoryName, categoryId]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.Category>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParalleCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "get_deep_categoryId.do",method = RequestMethod.GET)
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategoryId(HttpSession session,
                                                                     @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        /**
         * @Description //TODO 查询当前节点的id和递归子节点的id
           @Author Leo
         * @Date 23:10 2020/3/10
         * @Param [session, categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return categoryService.getCategoryAndDeepChildrenCategoryId(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }
    }
    @RequestMapping(value = "get_deep_categoryList.do",method = RequestMethod.GET)
    public ServerResponse<List<Category>> getCategoryAndDeepChildrenCategory(HttpSession session,
                                                                            @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        /**
         * @Description //TODO 查询当前节点的category信息和递归子节点的category信息
           @Author Leo
         * @Date 23:10 2020/3/10
         * @Param [session, categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return categoryService.getCategoryAndDeepChildrenCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "get_keep_categoryList.do",method = RequestMethod.GET)
    public ServerResponse<List<Category>> getCategoryChildrenCategory(HttpSession session,
                                                                            @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        /**
         * @Description //TODO 查询当前节点的category信息和递归子节点的category信息 方法2  效率高
           @Author Leo
         * @Date 23:10 2020/3/10
         * @Param [session, categoryId]
         * @return com.imooc.demo.common.ServerResponse<java.util.List<com.imooc.demo.pojo.Category>>
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return categoryService.getCategoryChildrenCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("没有权限操作,需要管理员权限");
        }
    }


}
