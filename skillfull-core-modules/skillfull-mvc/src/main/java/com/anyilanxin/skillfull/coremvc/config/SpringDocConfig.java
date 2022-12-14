// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.config;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import com.anyilanxin.skillfull.coremvc.config.properties.SpringDocCoreMvcProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * spring doc配置
 *
 * @author zxiaozhou
 * @date 2021-12-12 14:58
 * @since JDK1.8
 */
@AutoConfiguration
@RequiredArgsConstructor
public class SpringDocConfig {
    private final SpringDocCoreMvcProperty property;
    private final CoreWebMvcProperty mvcProperty;


    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private void setRequestMappingHandlerMapping(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

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
        int total = 0;
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            if (!isEffective(handlerMethod)) {
                continue;
            }
            total++;
        }
        return new OpenAPI()
                .info(new Info().title(property.getTitle() + "(总计:" + total + ")").version(property.getVersion()))
                .addServersItem(new Server().url(mvcProperty.getContentPath()))
                .addServersItem(new Server().url(apiPrefix))
                .components(new Components().securitySchemes(securitySchemes))
                .security(security);
    }


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
                .packagesToScan(property.getPackagesToScan().split("[,，]"))
                .build();
    }

    /**
     * 其他分组
     *
     * @author zxiaozhou
     * @date 2021-12-12 16:51
     */
    @Bean
    public void registerOtherGroupBean() {
        Map<String, DocInfoModel> docInfoModelMap = new HashMap<>(64);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            if (!isEffective(handlerMethod)) {
                continue;
            }
            Operation annotation = handlerMethod.getMethod().getAnnotation(Operation.class);
            String[] tags = annotation.tags();
            for (String tag : tags) {
                String beanName = tag.replaceAll("\\.", "");
                DocInfoModel docInfoModel = docInfoModelMap.get(beanName);
                if (Objects.isNull(docInfoModel)) {
                    docInfoModel = new DocInfoModel();
                    docInfoModel.setTotal(1);
                    docInfoModel.setVersion(tag);
                } else {
                    docInfoModel.setTotal(docInfoModel.getTotal() + 1);
                }
                docInfoModelMap.put(beanName, docInfoModel);
            }
        }
        if (CollUtil.isNotEmpty(docInfoModelMap)) {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            docInfoModelMap.forEach((k, v) -> {
                GroupedOpenApi build = GroupedOpenApi.builder()
                        .group(v.getVersion())
                        .packagesToScan(property.getPackagesToScan().split("[,，]"))
                        .addOpenApiCustomiser(sv -> sv.setInfo(new Info().title(property.getTitle() + "(总计:" + v.getTotal() + ")").version(v.getVersion())))
                        .addOpenApiMethodFilter(sv -> {
                            Class<?> beanType = sv.getClass();
                            Hidden hidden = beanType.getAnnotation(Hidden.class);
                            if (Objects.nonNull(hidden)) {
                                return false;
                            }
                            Operation annotation = sv.getAnnotation(Operation.class);
                            if (Objects.isNull(annotation)) {
                                return false;
                            }
                            String[] tagInfos = annotation.tags();
                            if (Objects.isNull(tagInfos) || tagInfos.length <= 0) {
                                return false;
                            }
                            return Arrays.asList(tagInfos).contains(v.getVersion());
                        })
                        .build();
                defaultListableBeanFactory.registerSingleton(k, build);
            });
        }
    }


    /**
     * 判断接口是否有效
     */
    private boolean isEffective(HandlerMethod handlerMethod) {
        Class<?> beanType = handlerMethod.getBeanType();
        Hidden hidden = beanType.getAnnotation(Hidden.class);
        if (Objects.nonNull(hidden)) {
            return false;
        }
        Operation annotation = handlerMethod.getMethod().getAnnotation(Operation.class);
        return Objects.nonNull(annotation) && !annotation.hidden();
    }

    /**
     * doc信息
     *
     * @author zxiaozhou
     * @date 2022-04-22 00:12
     * @since JDK1.8
     */
    @Getter
    @Setter
    public static class DocInfoModel {
        /**
         * 当前版本总数
         */
        private int total;
        /**
         * 版本
         */
        private String version;
    }
}
