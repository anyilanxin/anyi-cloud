

package com.anyilanxin.anyicloud.database.datasource.config;

import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * key配置
 *
 * @author zxh
 * @date 2020-08-28 10:23
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class KeyCoreDatabaseConfig {
    private final Snowflake snowflake;

    /**
     * 自定义mybatis plus id生成器
     *
     * @return IdentifierGenerator ${@link IdentifierGenerator}
     * @author zxh
     * @date 2020-08-29 01:34
     */
    @Bean
    @Primary
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator(snowflake);
    }

    static class CustomIdGenerator implements IdentifierGenerator {
        private final Snowflake generatorSnowflake;

        public CustomIdGenerator(Snowflake snowflake) {
            this.generatorSnowflake = snowflake;
        }


        @Override
        public Number nextId(Object entity) {
            return generatorSnowflake.nextId();
        }


        @Override
        public String nextUUID(Object entity) {
            return CoreCommonUtils.get32UUId();
        }
    }
}
