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

package com.anyilanxin.cloud.corecommon.cache;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 缓存数据
 *
 * @author zxh
 * @date 2019-06-16 14:06
 * @since 1.0.0
 */
public class CacheData<T> {
    private long expiresIn = -1L;
    private Date expiration;
    private T t;

    /**
     * 获取过期剩余时间,永不过期为-1(单位:秒)
     *
     * @author zxh
     * @date 2019-06-16 16:12
     */
    public int getExpiresIn() {
        return this.expiresIn != -1 ? Long.valueOf(expiresIn / 1000L).intValue() : -1;
    }


    /**
     * 设置过期时间,永不过期为-1(单位:秒)
     *
     * @author zxh
     * @date 2019-06-16 16:12
     */
    void setExpiresIn(int expiresIn) {
        if (expiresIn != -1) {
            this.expiresIn = expiresIn * 1000L;
            this.setExpiration(new Date(System.currentTimeMillis() + this.expiresIn));
        }
    }


    /**
     * 获取如果过期时间,如果永不过期此时返回null;
     *
     * @return Date ${@link Date}
     * @author zxh
     * @date 2019-06-16 16:11
     */
    public Date getExpiration() {
        return this.expiration;
    }


    /**
     * 设置过期时间,永不过期为null
     *
     * @author zxh
     * @date 2019-06-16 16:12
     */
    private void setExpiration(Date expiration) {
        this.expiration = expiration;
        if (expiration != null) {
            this.expiresIn = expiration.getTime();
        }
    }


    /**
     * 判断当前缓存是否过期
     *
     * @author zxh
     * @date 2019-06-16 16:13
     */
    public boolean isExpired() {
        if (this.expiresIn == -1) {
            return false;
        } else {
            return this.expiration != null && this.expiration.before(new Date());
        }
    }


    public T getT() {
        return this.t;
    }


    public void setT(T t) {
        this.t = t;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
