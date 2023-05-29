

package com.anyilanxin.anyicloud.coremvc.config;

import com.anyilanxin.anyicloud.coremvc.config.properties.CoreWebMvcProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 以jar包启动时创建外置配置文件
 *
 * @author zxh
 * @date 2019-04-16 10:38
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyStartCoreMvcConfig implements ApplicationRunner {
    private final CoreWebMvcProperty property;

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
