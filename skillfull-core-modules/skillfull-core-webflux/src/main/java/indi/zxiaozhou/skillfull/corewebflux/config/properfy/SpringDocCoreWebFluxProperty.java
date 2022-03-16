// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corewebflux.config.properfy;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * spring doc配置
 *
 * @author zxiaozhou
 * @date 2020-09-11 03:16
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocCoreWebFluxProperty {
    /**
     * 请求头
     */
    private Set<String> headers;

    /**
     * 版本号
     */
    private String version;


    /**
     * swagger请求前缀
     */
    private String apiPrefix;

    /**
     * 联系人
     */
    private String contactUser = "zxiaozhou";

    /**
     * 联系邮箱
     */
    private String contactEmail = "";

    /**
     * 标题
     */
    private String title = "";

    /**
     * 是否启用doc
     */
    @Value("${springdoc.swagger-ui.enabled}")
    private boolean swaggerEnable;


    /**
     * 扫包路径
     */
    private String packagesToScan;


    public Set<String> getHeaders() {
        if (CollectionUtil.isEmpty(headers)) {
            headers = new HashSet<>();
        }
        headers.add(SysBaseConstant.SUB_HEADER_TOKEN_KEY);
        headers.add("Authorization");
        return headers;
    }
}
