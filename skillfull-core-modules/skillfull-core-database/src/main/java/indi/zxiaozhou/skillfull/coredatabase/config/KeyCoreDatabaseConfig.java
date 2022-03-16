// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatabase.config;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * key配置
 *
 * @author zxiaozhou
 * @date 2020-08-28 10:23
 * @since JDK11
 */
@Configuration
@RequiredArgsConstructor
public class KeyCoreDatabaseConfig {
    private final Snowflake snowflake;

    /**
     * 自定义mybatis plus id生成器
     *
     * @return IdentifierGenerator ${@link IdentifierGenerator}
     * @author zxiaozhou
     * @date 2020-08-29 01:34
     */
    @Bean
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
