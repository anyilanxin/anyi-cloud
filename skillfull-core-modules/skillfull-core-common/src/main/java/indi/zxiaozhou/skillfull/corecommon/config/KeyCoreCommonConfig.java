// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class KeyCoreCommonConfig {
    /**
     * 雪花生成器
     *
     * @return Snowflake ${@link Snowflake}
     * @author zxiaozhou
     * @date 2020-06-22 14:42
     */
    @Bean
    @ConditionalOnMissingBean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake(CommonCoreConstant.WORKER_ID, CommonCoreConstant.DATACENTER_ID);
    }
}
