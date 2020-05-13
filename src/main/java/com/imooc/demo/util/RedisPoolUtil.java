package com.imooc.demo.util;

import com.imooc.demo.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * ClassName: RedisPoolUtil
 * Description: TODO
 * Author: Leo
 * Date: 2020/4/7-13:02
 * email 1437665365@qq.com
 */
@Slf4j
public class RedisPoolUtil {

    //exTime的单位是秒
    //设置有效期
    public static Long setExpire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        }catch (Exception e){
            log.error("expire key={},error={}",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    //exTime的单位是秒
    public static String setEx(String key,int exTime,String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        }catch (Exception e){
            log.error("setex key={},value={},error={}",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        }catch (Exception e){
            log.error("set key={},value={},error={}",key,value,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        }catch (Exception e){
            log.error("set key={},error={}",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        }catch (Exception e){
            log.error("del key={},error={}",key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //测试
    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("keyTest","value");
        String value = RedisPoolUtil.get("keyTest");
        log.info("value={}",value);
        RedisPoolUtil.setEx("keyex",60*10,"valueex");
        Long expire = RedisPoolUtil.setExpire("keyTest", 60 * 20);
        log.info("expire={}",expire);
        Long del = RedisPoolUtil.del("keyTest");

        if(del == 1){
            log.info("删除成功");
        }

    }
}
