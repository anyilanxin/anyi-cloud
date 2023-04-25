package com.anyilanxin.skillfull.corewebflux.utils;

import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * core mvc字符串处理工具类
 *
 * @author zxiaozhou
 * @date 2019-04-01 16:54
 * @since JDK11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CoreWebFluxStringUtils {
    private static CoreWebFluxStringUtils util;
    private final Snowflake snowflake;


    @PostConstruct
    private void init() {
        util = this;
    }


    /**
     * 获取32位uuid(使用ThreadLocalRandom提高性能)
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2019-04-01 17:02
     */
    public static String get32UUId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(CommonCoreConstant.DASH, CommonCoreConstant.EMPTY);
    }


    /**
     * 获取有序唯一id
     *
     * @return String ${@link String} id
     * @author zxiaozhou
     * @date 2020-08-28 16:23
     */
    public static String getSnowflakeId() {
        return util.snowflake.nextIdStr();
    }

}
