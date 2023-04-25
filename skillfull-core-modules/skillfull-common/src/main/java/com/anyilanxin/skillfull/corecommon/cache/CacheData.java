package com.anyilanxin.skillfull.corecommon.cache;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 缓存数据
 *
 * @author zxiaozhou
 * @date 2019-06-16 14:06
 * @since JDK1.8
 */
public class CacheData<T> {
    private long expiresIn = -1L;
    private Date expiration;
    private T t;

    /**
     * 获取过期剩余时间,永不过期为-1(单位:秒)
     *
     * @author zxiaozhou
     * @date 2019-06-16 16:12
     */
    public int getExpiresIn() {
        return this.expiresIn != -1 ? Long.valueOf(expiresIn / 1000L).intValue() : -1;
    }

    /**
     * 设置过期时间,永不过期为-1(单位:秒)
     *
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2019-06-16 16:11
     */
    public Date getExpiration() {
        return this.expiration;
    }

    /**
     * 设置过期时间,永不过期为null
     *
     * @author zxiaozhou
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
     * @author zxiaozhou
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
