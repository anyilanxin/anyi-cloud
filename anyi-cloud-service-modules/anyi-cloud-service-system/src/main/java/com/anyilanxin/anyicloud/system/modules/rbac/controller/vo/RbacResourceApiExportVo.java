

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 资源api表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-02-18 13:39:54
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacResourceApiExportVo implements Serializable {
    private static final long serialVersionUID = 622642197226604444L;

    @Schema(name = "resourceId", title = "资源id", required = true)
    @NotBlankOrNull(message = "资源id不能为空")
    private String resourceId;

    @Schema(name = "apiDocJsonUrl", title = "doc json地址")
    private String apiDocJsonUrl;

    @Schema(name = "apiDocJsonData", title = "doc json数据")
    private String apiDocJsonData;

    @Schema(name = "docType", title = "doc类型:1-springdoc,2-swagger,默认springdoc", example = "1")
    private Integer docType;

    @Schema(name = "requireAuth", title = "是否鉴权,0-不需要,1-需要。默认1")
    private Integer requireAuth;

    @Schema(name = "authType", title = "鉴权类型:1-全局(网关与服务都开启时同时鉴权)，2-网关(紧网关鉴权)，3-服务(网关与服务都开启时紧紧服务鉴权)，默认1,具体与AuthType一致")
    private Integer authType;

    @Schema(name = "permissionExpress", title = "鉴权表达式，不需要鉴权时默认为：permitAll()")
    private String permissionExpress;

    @Schema(name = "permissionAction", title = "按钮鉴权指令")
    private String permissionAction;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
