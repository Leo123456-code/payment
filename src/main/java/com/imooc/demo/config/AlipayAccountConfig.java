package com.imooc.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ClassName: AlipayAccountConfig
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/22-19:55
 * email 1437665365@qq.com
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayAccountConfig {

    //支付宝
    private String appId;
    private String privateKey;
    private String payPublicKey;
    private String notifyUrl;
    private String returnUrl;

}
