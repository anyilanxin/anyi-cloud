package com.anyilanxin.skillfull.corecommon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 打印启动信息
 *
 * @author zhou
 * @date 2022-07-17 23:03
 * @since JDK11
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringDocPrintApplicationRunner implements ApplicationRunner {
    private final Environment environment;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = environment.getProperty("server.port");
        boolean webflux = Boolean.TRUE.equals(environment.getProperty("springdoc.webflux", Boolean.class));
        String path;
        if (webflux) {
            path = environment.getProperty("spring.webflux.base-path", "");
        } else {
            path = environment.getProperty("server.servlet.context-path", "");
        }
        String swaggerUrl = environment.getProperty("springdoc.swagger-ui.path");
        String customUrl = environment.getProperty("springdoc.swagger-ui.custom-path");
        if (StringUtils.isNotBlank(customUrl)) {
            swaggerUrl = customUrl;
        }
        if (StringUtils.isBlank(swaggerUrl)) {
            if (webflux) {
                swaggerUrl = path + "/webjars/swagger-ui/index.html";
            } else {
                swaggerUrl = path + "/swagger-ui.html";
            }
        }
        String profilesActive = environment.getProperty("spring.profiles.active");
        String version = environment.getProperty("spring.application.version");
        String projectName = environment.getProperty("spring.application.name");
        log.info("\n-----------------------------------------------------------------------------\n"
                + "SkillFull Cloud Application（" + projectName + " v" + version + " " + (StringUtils.isNotBlank(profilesActive) ? profilesActive : "") + "）is running! Access URLs:\n"
                + "\tWebsite Preview:\thttps://skillfull.divisu.com\n"
                + "\tApi Url Prefix:\t\thttp://" + ip + ":" + port + path + "\n"
                + "\tSwagger Ui:\t\t\thttp://" + ip + ":" + port + swaggerUrl + "\n"
                + "-----------------------------------------------------------------------------");
    }
}
