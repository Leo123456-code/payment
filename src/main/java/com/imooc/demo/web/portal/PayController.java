package com.imooc.demo.web.portal;


import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.demo.config.WxAccountConfig;
import com.imooc.demo.pojo.PayInfo;
import com.imooc.demo.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PayController
 * Description: TODO 支付控制层
 * Author: Leo
 * Date: 2020/3/22-21:40
 * email 1437665365@qq.com
 */
@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IPayService payService;
    @Autowired
    private WxAccountConfig wx;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping("/create")
    //返回一個二維碼網頁
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum){
        //生成一个二维码编号
        PayResponse response=payService.create(orderId,amount,bestPayTypeEnum);
        Map<String,String> map = new HashMap<>();
        //支付方式不同,渲染方式不同
        if(bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC){
            map.put("body",response.getBody());
            return new ModelAndView("cgbForAliPay",map);
        }else if(bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE){
            map.put("codeUrl",response.getCodeUrl());
            map.put("orderId",orderId);
            map.put("returnUrl",wx.getReturnUrl());
            return new ModelAndView("createForWeiXinPayNavite",map);
//            return new ModelAndView("cbgForWxPay",map);
        }
        throw new RuntimeException("暂不支持的支付类型");




    }

    /**
     * 异步通知
     */
    @ResponseBody
    @PostMapping("/notify")
    public String asyncNotify(@RequestBody String notifyData){

        return payService.asyncNotify(notifyData);
    }

    /**
     * 通过订单号查询支付记录
     * @return
     */
    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(String orderId){
        log.info("通过订单号查询支付记录");

        return payService.queryByOrderId(orderId);

    }
}
