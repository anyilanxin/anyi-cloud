// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import indi.zxiaozhou.skillfull.auth.core.handler.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zxiaozhou
 */
@Configuration
public class MybatisPlusConfig {


    /**
     * sql公共字段自定义注入
     *
     * @return MyMetaObjectHandler ${@link MetaObjectHandler}
     * @author zxiaozhou
     * @date 2019-04-03 18:10
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }


}
