package com.imooc.demo.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.imooc.demo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ClassName: JsonUtil
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/7-13:36
 * email 1437665365@qq.com
 */
@Slf4j
public class JsonUtil {

    private  static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);
        //忽略空Bean转Json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下样式 "yyyy-MM-dd HH:mm:ss"
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //反序列化
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,false);
    }

    public static <T> String Obj2String(T obj){
        if(obj == null){
            return null;
        }

        try {
            return obj instanceof String ? (String)obj: objectMapper.writeValueAsString(obj);

        }catch (Exception e){
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> String Obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }

        try {
            return obj instanceof String ? (String)obj: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

        }catch (Exception e){
            log.warn("Parse object to String error",e);
            return null;
        }
    }


    public static <T>  T string2Obj(String str,Class<T> clazz)  {
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T) str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    //测试
    public static void main(String[] args) {
        User u1 = new User();
        User u2 = new User();
        u1.setId(1);
        u2.setId(2);
        u1.setEmail("1437665365@qq.com");
        u1.setEmail("xu18257624255@163.com");
        String userJson = JsonUtil.Obj2String(u1);
        String userJsonPretty = JsonUtil.Obj2StringPretty(u1);
        log.info("userJson={}",userJson);
        log.info("userJsonPretty={}",userJsonPretty);

        User user = JsonUtil.string2Obj(userJsonPretty, User.class);
        log.info("user={}",user);
        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String userListStr = JsonUtil.Obj2StringPretty(userList);
        log.info("userListStr={}",userListStr);

        List<User> userListObject = JsonUtil.string2Obj(userListStr,List.class,User.class);
        log.info("userListObject={}",userListObject);
        List<User> userListObject2 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {
        });
        log.info("userListObject2={}",userListObject2);
    }

}
