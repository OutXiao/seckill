package com.wenfan.seckill.service;

/**
 * Created by wenfan on 2020/2/3 16:06
 */

// 本地缓存
public interface CacheService {
    void setCommonCache(String key,Object value);

    Object getObjectFromCache(String key);

}
