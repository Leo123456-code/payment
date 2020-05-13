package com.imooc.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: WxAccountConfig
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/22-20:00
 * email 1437665365@qq.com
 */
@Data
@ConfigurationProperties(prefix = "wx")
@Component
public class WxAccountConfig {

    //微信
    private String appId;
    private String mchId;
    private String mchKey;
    private String notifyUrl;
    private String returnUrl;

}
