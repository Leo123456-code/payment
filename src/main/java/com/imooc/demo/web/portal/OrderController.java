package com.imooc.demo.web.portal;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: OrderController
 * Description: TODO 订单控制层
 * Author: Leo
 * Date: 2020/3/20-20:50
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    //根据发货地址创建订单
    @RequestMapping("create.do")
    public ServerResponse create(HttpSession session,Integer shippingId) throws Exception {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return orderService.createOrder(user.getId(),shippingId);
    }

    //购物车已经选中的商品详情
    @RequestMapping("get_order_cart_product_detail.do")
    public ServerResponse getOrderCartProductDetail(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return orderService.getOrderCartProductDetail(user.getId());
    }

    //取消订单
    @RequestMapping("cancle.do")
    public ServerResponse cancle(HttpSession session,Long orderNo) throws Exception {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return orderService.cancel(user.getId(),orderNo);
    }

    //订单详情
    @RequestMapping("detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return orderService.getOrderDetail(user.getId(),orderNo);
    }

    //
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return orderService.getOrderList(user.getId(),pageNum,pageSize);
    }
}
