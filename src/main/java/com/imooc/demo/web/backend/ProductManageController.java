package com.imooc.demo.web.backend;

import com.google.common.collect.Maps;
import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IFileService;
import com.imooc.demo.service.IProductService;
import com.imooc.demo.service.IUserService;
import com.imooc.demo.util.CookieUtil;
import com.imooc.demo.util.JsonUtil;
import com.imooc.demo.util.PropertiesUtil;
import com.imooc.demo.util.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * ClassName: ProductManageController
 * Description: //TODO商品控制层
 * Author: Leo
 * Date: 2020/3/11-7:05
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFileService fileService;

    //新增或修改商品
    @RequestMapping("save.do")
    public ServerResponse<Product> productSave(HttpServletRequest request ,Product product){
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充增加商品的业务逻辑
            return productService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //修改商品的销售状态
    @RequestMapping("set_sale_staus.do")
    public ServerResponse setSaleStatus(HttpServletRequest request,Integer productId,Integer status){
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //修改商品的销售状态
            return productService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //获取商品详情
    @RequestMapping("get_detail.do")
    public ServerResponse getDetail(HttpServletRequest request,Integer productId){
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //获取商品详情
            return productService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //分页
    @RequestMapping("get_List.do")
    public ServerResponse getListPageHelper(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1")  int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //查询所有商品并进行分页
            return productService.getProductListPageHelper(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }
    //根据姓名或productId进行搜素,分页
    @RequestMapping("search_product_page.do")
    public ServerResponse searchProductVOListPage(HttpServletRequest request,Integer productId,String productName,@RequestParam(value = "pageNum",defaultValue = "1")  int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //根据姓名或productId进行搜素
            return productService.searshProductListVoPage(productName,productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }
    //根据姓名或productId进行搜素,不分页
    @RequestMapping("search_product.do")
    public ServerResponse searchProductVOListPage(HttpServletRequest request,String productName,Integer productId){

//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //根据姓名或productId进行搜素
            return productService.searshProductListVo(productName,productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }
    //文件上传 upload
    @RequestMapping("upload.do")
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false)MultipartFile file, HttpServletRequest request){

        //权限判断
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //upload 文件夹名称
            String path = "d:/upload";
            String targetFileName = fileService.upload(file, path);

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+ targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);

            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //富文本上传   只针对simditor插件
    @RequestMapping("richtext_img_upload.do")
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();

        //权限判断
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求,我们使用的是 simditor所以按照simditor的要求返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if(userService.checkAdminRole(user).isSuccess()){
            //upload 文件夹名称
            String path =  "d:/upload";;
            String targetFileName = fileService.upload(file, path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            //response.addHeader 这是和前端的约定
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }
}
