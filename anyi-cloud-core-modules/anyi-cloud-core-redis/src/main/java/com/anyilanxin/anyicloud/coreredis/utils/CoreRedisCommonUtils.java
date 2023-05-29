

package com.anyilanxin.anyicloud.coreredis.utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * core mvc公共工具类
 *
 * @author zxh
 * @date 2021-08-19 16:34
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class CoreRedisCommonUtils {
    private static CoreRedisCommonUtils utils;
    private final Environment environment;
    private final StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 创建redis锁
     *
     * @param key     ${@link String}
     * @param timeout ${@link Long} 过期时间(单位:s),默认10s,只能大于0，当小于等于0时为10s
     * @author zxh
     * @date 2021-08-19 18:06
     */
    public static boolean createRedisServiceLock(String key, long timeout) {
        if (timeout <= 0) {
            timeout = 10L;
        }
        if (StringUtils.isNotBlank(key)) {
            String timeoutKey = utils.environment.getProperty("spring.application.name", "") + "_" + key;
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
