package com.anyilanxin.skillfull.corecommon.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;


/**
 * 本地缓存方法
 *
 * @author zxiaozhou
 * @date 2019-06-16 14:06
 * @since JDK1.8
 */
public class InMemoryCache {
    private static final ConcurrentHashMap<String, CacheData> CACHE = new ConcurrentHashMap<>();
    private static final DelayQueue<CacheExpiry> QUEUE = new DelayQueue<>();


    /**
     * 刷新缓存
     *
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    private static void flush() {
        while (true) {
            CacheExpiry expiry = QUEUE.poll();
            if (expiry != null) {
                CACHE.remove(expiry.getKey());
            } else {
                break;
            }
        }
    }


    /**
     * 清空缓存
     *
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    public static void clearAll() {
        CACHE.clear();
        QUEUE.clear();
    }


    /**
     * 清空键包含某个字符串的全部缓存
     *
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    public static void clearAllLikeStr(String str) {
        for (Iterator<Map.Entry<String, CacheData>> it = CACHE.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, CacheData> item = it.next();
            String key = item.getKey();
            if (key.contains(str)) {
                it.remove();
            }
        }

    }


    /**
     * 获取缓存数据值
     *
     * @param key ${@link String}
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    public static <T> T getCache(String key) {
        flush();
        CacheData<T> cacheData = getCacheData(key);
        return cacheData != null ? cacheData.getT() : null;
    }


    /**
     * 获取缓存数据
     *
     * @param key ${@link String}
     * @return CacheData<T> ${@link CacheData<T>}
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    public static <T> CacheData<T> getCacheData(String key) {
        flush();
        CacheData<T> cacheData = CACHE.get(key);
        if (cacheData == null) {
            cacheData = new CacheData<>();
            cacheData.setT(null);
            cacheData.setExpiresIn(0);
        }
        return cacheData;
    }


    /**
     * 添加缓存
     *
     * @param key    ${@link String}
     * @param t      ${@link Object}
     * @param expiry ${@link Integer} 缓存过期时间,-1表示永不过期（单位s）
     * @author zxiaozhou
     * @date 2019-06-16 15:47
     */
    public static <T> void addCache(String key, T t, int expiry) {
        flush();
        CacheData<T> cacheData = new CacheData<>();
        cacheData.setT(t);
        cacheData.setExpiresIn(expiry);
        if (expiry != -1) {
            CacheExpiry cacheExpiry = new CacheExpiry(key, expiry);
            QUEUE.put(cacheExpiry);
        }
        CACHE.put(key, cacheData);

    }


    /**
     * 移除缓存并返回当前缓存
     *
     * @param key ${@link String}
     * @author zxiaozhou
     * @date 2019-06-16 15:48
     */
    public static <T> T removeCache(String key) {
        flush();
        CacheData<T> cacheData = CACHE.remove(key);
        if (cacheData == null) {
            cacheData = new CacheData<>();
            cacheData.setT(null);
            cacheData.setExpiresIn(0);
        }
        return cacheData.getT();
    }
}
