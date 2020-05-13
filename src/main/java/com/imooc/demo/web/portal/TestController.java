package com.imooc.demo.web.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName: TestController
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/12-13:42
 * email 1437665365@qq.com
 */
@Controller
public class TestController {

    @GetMapping(value = "/success",produces =  "application/json;charset=UTF-8")
    public String home(){
        return "index";
    }
}
