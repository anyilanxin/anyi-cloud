// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coreredis.constant;

/**
 * redis订阅常量
 *
 * @author zxiaozhou
 * @date 2021-05-29 17:04
 * @since JDK1.8
 */
public interface RedisSubscribeConstant {
    /**
     * 路由重新加载
     */
    String GATEWAY_ROUTER_INFO_RELOAD = "GATEWAY_ROUTER_INFO_RELOAD";

    /**
     * socket消息广播处理
     */
    String MESSAGE_SOCKET_HANDLE = "MESSAGE_SOCKET_HANDLE";
    /**
     * 更新路由
     */
    String GATEWAY_ROUTER_INFO_UPDATE = "GATEWAY_ROUTER_INFO_UPDATE";


    /**
     * 删除路由
     */
    String GATEWAY_ROUTER_INFO_DELETE = "GATEWAY_ROUTER_INFO_DELETE";


    /**
     * url动态权限重新加载
     */
    String SECURITY_AUTH_URL_RELOAD = "SECURITY_AUTH_URL_RELOAD";


    /**
     * url动态权限更新
     */
    String SECURITY_AUTH_URL_UPDATE_PREFIX = "SECURITY_AUTH_URL_UPDATE:";


    /**
     * url动态权限删除
     */
    String SECURITY_AUTH_URL_DELETE_PREFIX = "SECURITY_AUTH_URL_DELETE:";

}
