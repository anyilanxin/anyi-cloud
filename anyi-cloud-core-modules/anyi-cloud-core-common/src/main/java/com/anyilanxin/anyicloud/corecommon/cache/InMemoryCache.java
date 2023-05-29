/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * 本地缓存方法
 *
 * @author zxh
 * @date 2019-06-16 14:06
 * @since 1.0.0
 */
public class InMemoryCache {
    private static final ConcurrentHashMap<String, CacheData> CACHE = new ConcurrentHashMap<>();
    private static final DelayQueue<CacheExpiry> QUEUE = new DelayQueue<>();

    /**
     * 刷新缓存
     *
     * @author zxh
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
     * @author zxh
     * @date 2019-06-16 15:47
     */
    public static void clearAll() {
        CACHE.clear();
        QUEUE.clear();
    }


    /**
     * 清空键包含某个字符串的全部缓存
     *
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
