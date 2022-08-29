// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.constant;

import com.anyilanxin.skillfull.corecommon.constant.impl.SysBaseType;

/**
 * 系统基础配置常量
 *
 * @author zxiaozhou
 * @date 2020-10-20 17:19
 * @since JDK11
 */
public interface SysBaseConstant {
    /**
     * 超级管理员角色(系统最高权限)
     */
    String SUPER_ROLE = SysBaseType.SUPER_ROLE.getType();

    /**
     * 应用扫描基础路径
     */
    String BOOT_BASE_SCAN_PACKAGE = "com.anyilanxin.skillfull";

    /**
     * mapper扫描路径
     */
    String BOOT_MAPPER_BASE_SCAN_PACKAGE = "com.anyilanxin.skillfull.*.modules.*.mapper";

    /**
     * feign默认请求头策略
     */
    String FEIGN_DEFAULT = "default";

    /**
     * 上下文用户信息
     */
    String CONTENT_USER_INFO = "USER_INFO";

    /**
     * 默认资源id
     */
    String DEFAULT_RESOURCE_ID = "skillfull";
}
