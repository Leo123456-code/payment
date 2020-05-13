package com.imooc.demo.web.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IOrderPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * ClassName: OrderController
 * Description: TODO 订单支付控制层
 * Author: Leo
 * Date: 2020/3/19-15:28
 * email 1437665365@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderPayController {
    @Autowired
    private IOrderPayService orderService;

    @RequestMapping("pay.do")
    public ServerResponse pay(HttpSession session, Long orderNo){
        /**
         * @Description //TODO 支付
           @Author Leo
         * @Date 21:25 2020/3/19
         * @Param [session, orderNo]
         * @return com.imooc.demo.common.ServerResponse
        */
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        String path = "d:/upload";
        return orderService.pay(user.getId(),orderNo,path);
    }


    @RequestMapping("alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request)  {
        /**
         * @Description //TODO 支付回调消息
           @Author Leo
         * @Date 21:27 2020/3/19
         * @Param [request]
         * @return java.lang.Object
        */
        Map<String, String> params = Maps.newHashMap();
        Map<String, String[]> requestParameters = request.getParameterMap();
        for(Iterator iterator = requestParameters.keySet().iterator();iterator.hasNext();){
           //迭代器遍历
           String name = (String) iterator.next();
           String [] values = requestParameters.get(name);
           String valueStr = "";
           for(int i = 0; i < values.length; i++){
               valueStr = (i == values.length-1) ? valueStr + values[i] : valueStr + values[i] + ",";
           }
           params.put(name,valueStr);
        }
        log.info("支付宝回调,sign={},trade_status={},参数={}",params.get("sign"),params.get("trade_status"),params.toString());
        //验证回调的正确性,是不是支付宝发的消息,避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("支付验证回调异常");
            e.printStackTrace();
        }
        //TODO 验证各种数据
        ServerResponse serverResponse = orderService.alipayCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAIL;
    }


    //查询是否支付
    @RequestMapping("query_order_pay_status.do")
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMessage());
        }
        ServerResponse serverResponse = orderService.queryOrderPayStatus(user.getId(), orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return serverResponse;
    }






}
