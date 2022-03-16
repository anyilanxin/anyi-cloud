// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import indi.zxiaozhou.skillfull.corecommon.annotation.SkillfulCloudApplication;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * start
 *
 * @author zxiaozhou
 * @date 2020-09-10 01:10
 * @since JDK11
 */
@Slf4j
@EnableAdminServer
@SkillfulCloudApplication
public class MonitorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(MonitorApplication.class, args);
        CoreCommonUtils.printSysInfo(application, true);
    }
}
