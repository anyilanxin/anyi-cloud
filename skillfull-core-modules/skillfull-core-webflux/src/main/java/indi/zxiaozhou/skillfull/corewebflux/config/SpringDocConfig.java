// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corewebflux.config;

import indi.zxiaozhou.skillfull.corewebflux.config.properfy.CoreWebFluxAppProperty;
import indi.zxiaozhou.skillfull.corewebflux.config.properfy.SpringDocCoreWebFluxProperty;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * spring doc配置
 *
 * @author zxiaozhou
 * @date 2021-12-12 14:58
 * @since JDK1.8
 */
@Configuration
@RequiredArgsConstructor
public class SpringDocConfig {
    private final SpringDocCoreWebFluxProperty property;
    private final CoreWebFluxAppProperty fluxAppProperty;
    private final ApplicationContext applicationContext;

    /**
     * 基本配置
     *
     * @param apiPrefix ${@link String}
     * @return OpenAPI ${@link OpenAPI}
     * @author zxiaozhou
     * @date 2021-12-12 16:51
     */
    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.api-prefix}") String apiPrefix) {
        Set<String> headers = property.getHeaders();
        Map<String, SecurityScheme> securitySchemes = new HashMap<>(headers.size());
        List<SecurityRequirement> security = new ArrayList<>(headers.size());
        headers.forEach(v -> {
            securitySchemes.put(v, new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name(v));
            security.add(new SecurityRequirement().addList(v));
        });
        return new OpenAPI()
                .info(new Info().title(property.getTitle()).version(property.getVersion()))
                .addServersItem(new Server().url(apiPrefix))
                .addServersItem(new Server().url(fluxAppProperty.getBasePath()))
                .components(new Components().securitySchemes(securitySchemes))
                .security(security);
    }


//    /**
//     * 其他分组
//     *
//     * @author zxiaozhou
//     * @date 2021-12-12 16:51
//     */
//    @Bean
//    public void registerAllGroupBean() {
//        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        GroupedOpenApi build = GroupedOpenApi.builder()
//                .group("v2.0.0")
//                .pathsToMatch("/**")
//                .build();
//        defaultListableBeanFactory.registerSingleton("testsfsdfsdf", build);
//        build = GroupedOpenApi.builder()
//                .group("v1.0.0")
//                .pathsToMatch("/**")
//                .build();
//        defaultListableBeanFactory.registerSingleton("testsjflksdfjlskfsfsdfsdf", build);
//    }


    /**
     * 默认分组
     *
     * @return GroupedOpenApi ${@link GroupedOpenApi}
     * @author zxiaozhou
     * @date 2021-12-12 16:51
     */
    @Bean
    public GroupedOpenApi defaultGroup() {
        return GroupedOpenApi.builder()
                .group("all")
                .packagesToScan(property.getPackagesToScan().split(","))
                .build();
    }
}
