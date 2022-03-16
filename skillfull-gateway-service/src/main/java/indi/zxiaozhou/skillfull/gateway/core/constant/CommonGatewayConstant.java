// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.core.constant;

/**
 * 全局公共常量
 *
 * @author zxiaozhou
 * @date 2020-07-23 19:57
 * @since JDK11
 */
public interface CommonGatewayConstant {
    String SYSTEM_SERVICE = "system-service";

    String CONST_PREFIX = "gateway_";
    /**
     * 负载均衡类型前缀
     */
    String LB_PREFIX = "lb:";

    /**
     * 用户信息
     */
    String GATEWAY_USER_INFO = "GATEWAY_USER_INFO";

    /**
     * 用户登录信息
     */
    String GATEWAY_USER_ONLINE = "GATEWAY_USER_ONLINE";
    /**
     * 日志记录数据
     */
    String GATEWAY_LOG_INFO = "GATEWAY_LOG_INFO";

    /**
     * 加解密密钥信息
     */
    String GATEWAY_ENCRYPT_DECRYPT_INFO = "GATEWAY_ENCRYPT_DECRYPT_INFO";


    /**
     * token统一处理标识
     */
    String GATEWAY_TOKEN_UNIFIED = "GATEWAY_TOKEN_UNIFIED";

    /**
     * 自定义过滤器最大执行顺序
     */
    int GATEWAY_CUSTOM_FILTER_HIGHEST_ORDER = 100;
}
