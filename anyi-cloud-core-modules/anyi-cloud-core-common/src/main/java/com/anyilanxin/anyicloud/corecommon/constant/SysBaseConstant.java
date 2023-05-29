

package com.anyilanxin.anyicloud.corecommon.constant;

import com.anyilanxin.anyicloud.corecommon.constant.impl.SysBaseType;

/**
 * 系统基础配置常量
 *
 * @author zxh
 * @date 2020-10-20 17:19
 * @since 1.0.0
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
