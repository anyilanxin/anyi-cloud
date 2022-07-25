package com.anyilanxin.skillfull.storage.core.config;

import com.anyilanxin.skillfull.storage.core.config.properties.LocalFileProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件本地映射路径配置
 *
 * @author zxiaozhou
 * @date 2020-10-23 13:15
 * @since JDK11
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class UploadLocalFileConfig implements WebMvcConfigurer {
    private final LocalFileProperty property;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(property.getVirtualMapping() + "/**")
                .addResourceLocations("file:" + property.getUploadFolder() + "/");
    }

}
