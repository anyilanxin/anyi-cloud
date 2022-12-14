// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.constant;

/**
 * 全局公共常量
 *
 * @author zxiaozhou
 * @date 2020-07-23 19:57
 * @since JDK11
 */
public interface CommonGatewayConstant {

    /**
     * 负载均衡类型前缀
     */
    String LB_PREFIX = "lb:";

    /**
     * 用户信息
     */
    String GATEWAY_USER_INFO = "GATEWAY_USER_INFO";

    /**
     * 日志记录数据
     */
    String GATEWAY_LOG_INFO = "GATEWAY_LOG_INFO";

    /**
     * 加解密密钥信息
     */
    String GATEWAY_ENCRYPT_DECRYPT_INFO = "GATEWAY_ENCRYPT_DECRYPT_INFO";
}
