package com.jamie.framework.redis;

import java.util.List;

/**
 * @author lizheng
 * @date: 21:12 2020/02/13
 * @Description: RedisService
 */
public interface RedisService {
    /**
     * 设置字符串 k v
     * @param key
     * @param value
     */
    void setStr(String key, String value);

    /**
     * 设置字符串 k v,并设置过期时间
     * @param key
     * @param value
     * @param second
     */
    void setStr(String key, String value, long second);

    /**
     * 设置字符串 k  Object v
     * @param key
     * @param value
     */
    void setObj(String key, String value);

    /**
     * 设置字符串 k  Object v，并设置过期时间
     * @param key
     * @param value
     * @param second
     */
    void setObj(String key, String value, long second);

    /**
     * 设置k v 数据
     * @param key
     * @param value
     */
    void set(Object key, Object value);

    /**
     * 设置k v 数据，并设置过期时间
     * @param key
     * @param value
     * @param second
     */
    void set(Object key, Object value, long second);

    /**
     * 获取字符串类型的值
     * @param key
     * @return
     */
    String getStr(String key);

    /**
     * 获取objec类型的值
     * @param key
     * @return
     */
    Object getObj(String key);

    /**
     * 获取值
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * key是否存在
     * @param key
     * @return
     */
    boolean hasKey(Object key);

    /**
     * 根据key获取string类型的list
     * @param key
     * @return
     */
    List<String> getList(String key);

    /**
     * 根据key获取Object类型的list
     * @param key
     * @return
     */
    List<Object> getList(Object key);

    /**
     * 从左边依次向给定list中插入String元素
     * @param key
     * @return
     */
    void listLPush(String key, String ...value );

    /**
     * 从右边依次向给定list中插入String元素
     * @param key
     * @return
     */
    void listRPush(String key, String ...value );

    /**
     * 从左边依次向给定list中插入Object元素
     * @param key
     * @return
     */
    void listLPush(Object key, Object ...value );

    /**
     * 从右边依次向给定list中插入Object元素
     * @param key
     * @return
     */
    void listRPush(Object key, Object ...value );
}
