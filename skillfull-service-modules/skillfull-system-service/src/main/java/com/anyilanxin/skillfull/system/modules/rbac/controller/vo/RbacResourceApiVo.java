// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Map;

/**
 * 资源api表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-03 00:29:06
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacResourceApiVo implements Serializable {
    private static final long serialVersionUID = -63465291489451659L;

    @Schema(name = "resourceId", title = "资源id", required = true)
    @NotBlankOrNull(message = "资源id不能为空")
    private String resourceId;

    @Schema(name = "apiUri", title = "接口地址", required = true)
    @NotBlankOrNull(message = "接口地址不能为空")
    private String apiUri;

    @Schema(name = "apiName", title = "接口名称")
    private String apiName;

    @Schema(name = "apiNotes", title = "接口描述")
    private String apiNotes;

    @Schema(name = "requestMethod", title = "请求类型，多个英文逗号隔开", required = true)
    @NotBlankOrNull(message = "请求类型，多个英文逗号隔开不能为空")
    private String requestMethod;

    @Schema(name = "requestParams", title = "请求参数,json object 或json array,保护参数类型")
    private Map<String, Object> requestParams;

    @Schema(name = "responseData", title = "响应参数")
    private Map<String, Object> responseData;

    @Schema(name = "apiTag", title = "api所属分组")
    private String apiTag;

    @Schema(name = "apiTagName", title = "api所属分组名称")
    private String apiTagName;

    @Schema(name = "apiVersions", title = "api版本,多个英文逗号隔开")
    private String apiVersions;

    @Schema(name = "requireAuth", title = "是否鉴权,0-不需要,1-需要。默认1", required = true)
    @NotBlankOrNull(message = "是否鉴权不能为空")
    @Min(value = 0, message = "是否需要鉴权只能为0、1")
    @Max(value = 1, message = "是否需要鉴权只能为0、1")
    private Integer requireAuth;

    @Schema(name = "authType", title = "鉴权类型:1-全局(网关与服务都开启时同时鉴权)，2-网关(紧网关鉴权)，3-服务(网关与服务都开启时紧紧服务鉴权)，默认1,具体与AuthType一致")
    private Integer authType;

    @Schema(name = "permissionExpress", title = "鉴权表达式，不需要鉴权时默认为：permitAll()")
    private String permissionExpress;

    @Schema(name = "permissionAction", title = "鉴权指令，只有表达式为非角色是使用")
    private String permissionAction;

    @Schema(name = "remark", title = "备注")
    private String remark;

}
