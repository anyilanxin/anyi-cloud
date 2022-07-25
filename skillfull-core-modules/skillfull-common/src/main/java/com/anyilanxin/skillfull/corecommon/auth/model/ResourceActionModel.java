// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author zxiaozhou
 * @date 2022-03-02 02:07
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ResourceActionModel implements Serializable {

    private static final long serialVersionUID = 954160189156133923L;

    @Schema(name = "requestPrefix", title = "资源请求前缀，即server.servlet.context-path值或spring.webflux.base-path值，前缀mvc,后缀webflux")
    private String requestPrefix;

    @Schema(name = "resourceCode", title = "资源编码,即后端服务名")
    private String resourceCode;

    @Schema(name = "requestMethod", title = "请求类型，多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "apiUri", title = "接口地址")
    private String apiUri;

    @Schema(name = "apiUriAll", title = "接口全地址,request_prefix+api_uri")
    private String apiUriAll;

    @Schema(name = "requireAuth", title = "是否鉴权,0-不需要,1-需要。默认1")
    private Integer requireAuth;

    @Schema(name = "authType", title = "鉴权类型:1-全局(网关与服务都开启时同时鉴权)，2-网关(紧网关鉴权)，3-服务(网关与服务都开启时紧紧服务鉴权)，默认1,具体与AuthType一致")
    private Integer authType;

    @Schema(name = "permissionExpress", title = "鉴权表达式，不需要鉴权时默认为：permitAll()")
    private String permissionExpress;
}
