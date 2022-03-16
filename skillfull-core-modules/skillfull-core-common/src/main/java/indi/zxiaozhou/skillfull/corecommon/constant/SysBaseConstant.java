// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.constant;

import indi.zxiaozhou.skillfull.corecommon.constant.impl.SysBaseType;

/**
 * 系统基础配置常量
 *
 * @author zxiaozhou
 * @date 2020-10-20 17:19
 * @since JDK11
 */
public interface SysBaseConstant {
    /**
     * 用户登录在线信息:前缀+userId+':'+loginCode(如果单设备登录,loginCode为用户id),返回:LoginOnlineInfoModel
     */
    String LOGIN_ONLINE_PREFIX = "LOGIN_ONLINE_PREFIX:";

    /**
     * 用户登录链接socket信息:前缀+userId+':'+loginCode(如果单设备登录,loginCode为用户id),返回:LoginSocketInfoModel
     */
    String LOGIN_SOCKET_PREFIX = "LOGIN_SOCKET_PREFIX:";

    /**
     * 用户登录信息:前缀+userId+':'+loginCode(如果单设备登录,loginCode为用户id),返回:LoginUserInfoModel
     */
    String LOGIN_USER_PREFIX = "LOGIN_USER_PREFIX:";

    /**
     * 用户登录token加解密信息:前缀+loginCode(如果单设备登录,loginCode为用户id),返回:LoginTokenSecurityModel
     */
    String LOGIN_TOKEN_SECURITY_PREFIX = "LOGIN_TOKEN_SECURITY_PREFIX:";


    /**
     * token Claim sub值key
     */
    String CLAIM_SUB_KEY = "sub";


    /**
     * 超级管理员角色
     */
    String SUPER_ROLE = SysBaseType.SUPER_ROLE.getType();


    /**
     * 系统基础配置缓存key
     */
    String SYSTEM_BASE = "SYSTEM_BASE";

    /**
     * 应用扫描基础路径
     */
    String BOOT_BASE_SCAN_PACKAGE = "indi.zxiaozhou.skillfull";

    /**
     * mapper扫描路径
     */
    String BOOT_MAPPER_BASE_SCAN_PACKAGE = "indi.zxiaozhou.skillfull.*.modules.*.mapper";

    /**
     * feign默认请求头策略
     */
    String FEIGN_DEFAULT = "default";

    /**
     * 上下文用户信息
     */
    String CONTENT_USER_INFO = "USER_INFO";

    /**
     * 上下文登录信息
     */
    String CONTENT_LOGIN_INFO = "LOGIN_INFO";

    /**
     * 子服务请求头token key
     */
    String SUB_HEADER_TOKEN_KEY = "skill-full-token";


    /**
     * 子服务query token key
     */
    String SUB_QUERY_TOKEN_KEY = "skill-full-token";

    /**
     * 消息处理策略前缀
     */
    String SOCKET_MSG_TYPE_PREFIX = "SocketMsgType_";

    /**
     * 路由元数据--按钮权限信息key
     */
    String ROUTE_META_ACTION_KEY = "AUTHORIZE_ACTION_LIST";
}
