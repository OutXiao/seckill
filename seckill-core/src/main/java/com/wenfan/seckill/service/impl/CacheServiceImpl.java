package com.wenfan.seckill.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wenfan.seckill.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenfan on 2020/2/3 16:08
 */
@Service
public class CacheServiceImpl implements CacheService {


    private Cache<String,Object> commomCache = null;

    @PostConstruct
    public void init(){
        commomCache = CacheBuilder.newBuilder()
                // 设置缓存容器的初始容量为10
                .initialCapacity(10)
                // 设置缓存容器中可以最大存储key为100个，超过100和会按照LRU进行移除
                .maximumSize(100)
                // 设置写缓存后多少秒过期
                .expireAfterWrite(60,TimeUnit.SECONDS).build();
    }


    @Override
    public void setCommonCache(String key, Object value) {
        commomCache.put(key,value);
    }

    @Override
    public Object getObjectFromCache(String key) {
        return commomCache.getIfPresent(key);
    }
}
