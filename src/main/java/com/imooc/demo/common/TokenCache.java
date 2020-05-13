package com.imooc.demo.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: TokenCache
 * Description: TODO token缓存
 * Author: Leo
 * Date: 2020/3/9-22:46
 * email 1437665365@qq.com
 */
@Data
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //LRU算法
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).
            expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {

        //默认的数据加载实现,当调用get取值时,如果key 没有对应的值,就调用这个方法加载
        @Override
        public String load(String key) throws Exception {
            return "null";
        }
    });

    //set方法
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }
    //get方法
    public static String getKey(String key){
        String value = null;
        try {
           value = localCache.get(key);
           if("null".equals(value)){
              return null;
           }
           return value;
        } catch (ExecutionException e) {
            logger.error("localCache get error",e);
            e.printStackTrace();
        }
        return null;
    }
}
