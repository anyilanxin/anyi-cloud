/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.job.modules.jobcore.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * local cache tool
 *
 * @author xuxueli 2018-01-22 21:37:34
 */
public class LocalCacheUtil {

    private static ConcurrentMap<String, LocalCacheData> cacheRepository = new ConcurrentHashMap<String, LocalCacheData>(); // 类型建议用抽象父类，兼容性更好；

    private static class LocalCacheData {
        private String key;
        private Object val;
        private long timeoutTime;

        public LocalCacheData() {
        }


        public LocalCacheData(String key, Object val, long timeoutTime) {
            this.key = key;
            this.val = val;
            this.timeoutTime = timeoutTime;
        }


        public String getKey() {
            return key;
        }


        public void setKey(String key) {
            this.key = key;
        }


        public Object getVal() {
            return val;
        }


        public void setVal(Object val) {
            this.val = val;
        }


        public long getTimeoutTime() {
            return timeoutTime;
        }


        public void setTimeoutTime(long timeoutTime) {
            this.timeoutTime = timeoutTime;
        }
    }

    /**
     * set cache
     *
     * @param key
     * @param val
     * @param cacheTime
     * @return
     */
    public static boolean set(String key, Object val, long cacheTime) {

        // clean timeout cache, before set new cache (avoid cache too much)
        cleanTimeoutCache();

        // set new cache
        if (key == null || key.trim().length() == 0) {
            return false;
        }
        if (val == null) {
            remove(key);
        }
        if (cacheTime <= 0) {
            remove(key);
        }
        long timeoutTime = System.currentTimeMillis() + cacheTime;
        LocalCacheData localCacheData = new LocalCacheData(key, val, timeoutTime);
        cacheRepository.put(localCacheData.getKey(), localCacheData);
        return true;
    }


    /**
     * remove cache
     *
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        if (key == null || key.trim().length() == 0) {
            return false;
        }
        cacheRepository.remove(key);
        return true;
    }


    /**
     * get cache
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        if (key == null || key.trim().length() == 0) {
            return null;
        }
        LocalCacheData localCacheData = cacheRepository.get(key);
        if (localCacheData != null && System.currentTimeMillis() < localCacheData.getTimeoutTime()) {
            return localCacheData.getVal();
        } else {
            remove(key);
            return null;
        }
    }


    /**
     * clean timeout cache
     *
     * @return
     */
    public static boolean cleanTimeoutCache() {
        if (!cacheRepository.keySet().isEmpty()) {
            for (String key : cacheRepository.keySet()) {
                LocalCacheData localCacheData = cacheRepository.get(key);
                if (localCacheData != null && System.currentTimeMillis() >= localCacheData.getTimeoutTime()) {
                    cacheRepository.remove(key);
                }
            }
        }
        return true;
    }

}
