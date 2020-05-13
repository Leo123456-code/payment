package com.imooc.demo.web.backend;

import com.github.pagehelper.PageInfo;
import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IOrderService;
import com.imooc.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: OrderManageController
 * Description: TODO 后台商品管理
 * Author: Leo
 * Date: 2020/3/21-13:29
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

    //获取所有的商品OrderVo,分页
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务逻辑
            return orderService.manageList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    //商品详情
    @RequestMapping("datail.do")
    public ServerResponse orderDetail(HttpSession session,Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务逻辑
            return orderService.manageDetail(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    //后台搜索,精确匹配
    @RequestMapping("search.do")
    public ServerResponse orderSearch(HttpSession session,long orderNo,
                                      @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务逻辑
            return orderService.manageSearch(orderNo,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    //发货
    @RequestMapping("send_goods.do")
    public ServerResponse<String> orderSendGoods(HttpSession session,long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务逻辑
            return orderService.manageSendGoods(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
