

package com.anyilanxin.anyicloud.corecommon.cache;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Delayed
 *
 * @author zxh
 * @date 2019-06-16 16:19
 * @since 1.0.0
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
