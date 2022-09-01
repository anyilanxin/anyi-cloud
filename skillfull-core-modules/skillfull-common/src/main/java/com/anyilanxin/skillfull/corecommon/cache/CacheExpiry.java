// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.cache;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Delayed
 *
 * @author zxiaozhou
 * @date 2019-06-16 16:19
 * @since JDK1.8
 */
class CacheExpiry implements Delayed {
    private final long expiry;
    private final String key;


    CacheExpiry(String key, Date date) {
        this.key = key;
        this.expiry = date.getTime();
    }


    CacheExpiry(String key, int expiry) {
        this.key = key;
        this.expiry = System.currentTimeMillis() + expiry * 1000;
    }


    @Override
    public int compareTo(Delayed delayed) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
    }


    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.expiry - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }


    public String getKey() {
        return this.key;
    }
}
