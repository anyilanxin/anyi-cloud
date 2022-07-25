// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.utils;

import com.anyilanxin.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * core mvc公共工具类
 *
 * @author zxiaozhou
 * @date 2021-08-19 16:34
 * @since JDK1.8
 */
@Component
@RequiredArgsConstructor
public class CoreMvcCommonUtils {
    private static CoreMvcCommonUtils utils;
    private final StringRedisTemplate stringRedisTemplate;
    private final CoreWebMvcProperty property;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 创建redis 服务级锁
     *
     * @param key     ${@link String}
     * @param timeout ${@link Long} 过期时间(单位:s),默认10s,只能大于0，当小于等于0时为10s
     * @author zxiaozhou
     * @date 2021-08-19 18:06
     */
    public static boolean createRedisServiceLock(String key, long timeout) {
        if (timeout <= 0) {
            timeout = 10L;
        }
        if (StringUtils.isNotBlank(key)) {
            String timeoutKey = utils.property.getServiceName() + "_" + key;
            Boolean ifAbsent = utils.stringRedisTemplate.opsForValue().setIfAbsent(timeoutKey, "阻拦其他客户端消费该消息");
            if (Objects.nonNull(ifAbsent) && Boolean.FALSE.equals(ifAbsent)) {
                return true;
            }
            utils.stringRedisTemplate.opsForValue().set(timeoutKey, timeoutKey + "超时key", timeout, TimeUnit.SECONDS);
            return false;
        }
        return true;
    }
}
