package com.imooc.demo.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: CookieUtil
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/7-16:45
 * email 1437665365@qq.com
 */
@Slf4j
public class CookieUtil {
//    private final static String COOKIE_DOMAIN=".happymmall.com";
    private final static String COOKIE_NAME="mmall_login_token";

    //读取cookie
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                log.info("read cookieName={},cookieValue={}",cookie.getName(),cookie.getValue());
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("return cookieName={},cookieValue={}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //写入Cookie
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
//        cookie.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        cookie.setPath("/");
        //时间是秒,
        //如果这个maxage不设置的话,cookie就不会写入硬盘,而是写在内存,只对当前页面有效
        //如果是-1,代表永久
        cookie.setMaxAge(60*60*24*365);
        cookie.setHttpOnly(true);
        log.info("write cookieName={},cookieValue={}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    //删除cookie
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
//                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    //失效时间为0,代表删除此cookie
                    cookie.setMaxAge(0);
                    log.info("del cookieName={},cookieValue={}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }

    }
}
