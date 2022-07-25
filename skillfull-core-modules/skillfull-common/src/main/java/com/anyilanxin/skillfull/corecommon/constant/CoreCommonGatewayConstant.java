// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.constant;

/**
 * 公共网关常量
 *
 * @author zxiaozhou
 * @date 2022-05-03 01:16
 * @since JDK1.8
 */
public interface CoreCommonGatewayConstant {
    /**
     * 网关鉴权过滤器
     */
    String AUTHORIZE_FILTER = "Authorize";

    /**
     * 类型
     */
    String PARAM_TYPE_KEY = "type";

    /**
     * 特殊url
     */
    String PARAM_SPECIAL_URL_KEY = "specialUrl";

    /**
     * 资源指令
     */
    String ATTRIBUTES_KEY = "attributes";

    /**
     * 是否启用
     */
    String PARAM_ENABLED_KEY = "enabled";
}
