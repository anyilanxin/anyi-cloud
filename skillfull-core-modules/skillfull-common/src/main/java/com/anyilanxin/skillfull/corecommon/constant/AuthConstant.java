package com.anyilanxin.skillfull.corecommon.constant;

import com.anyilanxin.skillfull.corecommon.constant.impl.SysBaseType;

/**
 * 系统基础配置常量
 *
 * @author zxiaozhou
 * @date 2020-10-20 17:19
 * @since JDK11
 */
public interface AuthConstant {
    /**
     * 请求头token key键
     */
    String BEARER_TOKEN_HEADER_NAME = "SkillfullAuthorization";

    /**
     * query 参数token key键
     */
    String ACCESS_TOKEN_QUERY_NAME = "skillfull_access_token";

    /**
     * 超级管理员角色(系统最高权限)
     */
    String SUPER_ROLE = SysBaseType.SUPER_ROLE.getType();

    /**
     * 按钮权限校验角色前缀
     */
    String DEFAULT_ROLE_PREFIX = "ROLE_";


    /**
     * 白名单或者不鉴权时默认权限表达式
     */
    String PERMIT_ALL_EXPRESS = "permitAll()";

    /**
     * 黑名单或者不允许访问时默认权限表达式
     */
    String DENY_ALL_EXPRESS = "denyAll()";
}
