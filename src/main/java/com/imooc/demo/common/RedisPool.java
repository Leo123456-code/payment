package com.imooc.demo.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ClassName: RedisPool
 * Description: TODO redis连接池测试
 * Author: Leo
 * Date: 2020/4/6-22:54
 * email 1437665365@qq.com
 */
public class RedisPool {
    //jedis连接池
    private static JedisPool pool;
    //最大连接数
    private static  Integer maxTotal = 20;
    //在jedis连接池中最大的idle状态(空闲的) 的jedis实例的个数
    private static  Integer maxIdle = 10;
    //在jedis连接池中最小的idle状态(空闲的) 的jedis实例的个数
    private static  Integer minIdle = 2;
    //在borrow一个jedis实例的时候,是否要进行验证操作,如果赋值true,
    // 则得到的jedis实例肯定是可以用的
    private static Boolean testOnBorrow = true;
    //在return一个jedis实例的时候,是否要进行验证操作,如果赋值true,
    // 则放回的jedis实例肯定是可以用的
    private static Boolean testOnReturn = false;
    private static String ip = "127.0.0.1";
    private static Integer port = 6379;

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        //连接耗尽时,false会抛出异常,true阻塞直到超时
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, ip, port, 1000 * 2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("xzckey3","xzcvalue3");
        String xzcvalue3 = jedis.get("xzckey3");
        System.out.println("xzcvalue3="+xzcvalue3);
        returnResource(jedis);
        //销毁所有的连接
        pool.destroy();
        System.out.println("program is end...");
    }

}
