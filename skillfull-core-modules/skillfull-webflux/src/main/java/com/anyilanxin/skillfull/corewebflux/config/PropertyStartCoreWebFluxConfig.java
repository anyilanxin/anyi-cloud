// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corewebflux.config;


import com.anyilanxin.skillfull.corewebflux.config.properfy.CoreWebFluxAppProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 以jar包启动时创建外置配置文件
 *
 * @author zxiaozhou
 * @date 2019-04-16 10:38
 * @since JDK11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyStartCoreWebFluxConfig implements ApplicationRunner {
    private final CoreWebFluxAppProperty property;


    @Override
    public void run(ApplicationArguments args) {
        // 处理生成外置配置文件
        if (property.isCreateOutConf()) {
            // 检测外置配置文件路径
            File file = new File("./config");
            if (!file.exists() || !file.isDirectory()) {
                boolean result = file.mkdirs();
                if (result) {
                    log.debug("------------StartConfig------------>createOutConfig:{}", "创建外置配置文件路径成功");
                } else {
                    throw new RuntimeException("创建外置配置文件路径失败，请检查环境");
                }
            }
            // 移动当前配置文件到外置配置文件中
            String application = "application-" + property.getActive() + ".yml";
            file = new File("./config/" + application);
            if (!file.exists()) {
                try (OutputStream outputStream = new FileOutputStream(file); InputStream inputStream = property.getResource().getInputStream()) {
                    IOUtils.copy(inputStream, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
