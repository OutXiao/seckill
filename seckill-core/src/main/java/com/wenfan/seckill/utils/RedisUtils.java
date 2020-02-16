package com.wenfan.seckill.utils;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/3/8 16:25
 */
public interface RedisUtils {

    /**
     * set存数据
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * get获取数据
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置有效天数
     *
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 移除数据
     *
     * @param key
     * @return
     */
    boolean remove(String key);

}
