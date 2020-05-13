package com.imooc.demo.web.portal;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.form.ShippingAddForm;
import com.imooc.demo.pojo.Shipping;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: ShippingController
 * Description: TODO 收货地址前台控制层
 * Author: Leo
 * Date: 2020/3/18-20:04
 * email 1437665365@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    //添加收货地址
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session, ShippingAddForm form){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.add(user.getId(),form);
    }

    //删除收货地址
    @RequestMapping("delete.do")
    public ServerResponse<String> delete(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.del(user.getId(),shippingId);
    }

    //修改收货地址
    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.update(user.getId(),shipping);
    }

    //查询收货地址详情
    @RequestMapping("select.do")
    public ServerResponse select(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.select(user.getId(),shippingId);
    }

    //查询当前用户所有收货地址详情list
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session, @RequestParam(value ="pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }

}
