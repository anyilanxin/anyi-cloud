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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 授权客户端信息添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacClientDetailsVo implements Serializable {
    private static final long serialVersionUID = -40072152943296674L;

    @Schema(name = "clientId", title = "客户端id", required = true)
    @NotBlankOrNull(message = "客户端id不能为空")
    private String clientId;

    @Schema(name = "clientName", title = "客户端名称", required = true)
    @NotBlankOrNull(message = "客户端名称不能为空")
    private String clientName;

    @Schema(name = "clientIco", title = "客户端图标")
    private String clientIco;

    @Schema(name = "clientSecurity", title = "客户端密码(创建时必填)")
    private String clientSecurity;

    @Schema(name = "limitResource", title = "限制授权资源：0-不限制，1-限制。默认1", required = true)
    @NotBlankOrNull(message = "是否限制授权资源不能为空")
    private Integer limitResource;

    @Schema(name = "resourceIds", title = "授权资源ids,json array，json array")
    private List<String> resourceIds;

    @Schema(name = "signatureRequired", title = "是否验签:0-不验签，1-验签，默认1", required = true)
    @NotBlankOrNull(message = "是否验签不能为空")
    private Integer signatureRequired;

    @Schema(name = "signatureKey", title = "数据签名key，当需要验签时必填")
    private String signatureKey;

    @Schema(name = "authorizedGrantTypes", title = "允许授权类型，来源与授权中心常量字典AuthorizedGrantType,json array", required = true)
    @NotBlankOrNull(message = "允许授权类型不能为空")
    private List<String> authorizedGrantTypes;

    @Schema(name = "singleLogin", title = "是否单设备登录：0-不是,1-是，默认0", required = true)
    @NotBlankOrNull(message = "是否单设备登录不能为空")
    private Integer singleLogin;

    @Schema(name = "singleLoginType", title = "单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录")
    private Integer singleLoginType;

    @Schema(name = "endpoints", title = "允许登录端点,json array")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> endpoints;

    @Schema(name = "innerSystem", title = "是否内部系统：0-不是，1-是，默认0", required = true)
    @NotBlankOrNull(message = "是否内部系统不能为空")
    private Integer innerSystem;

    @Schema(name = "limitError", title = "限制授权错误次数:0-不限制,1-限制。默认0", required = true)
    @NotBlankOrNull(message = "限制授权错误次数不能为空")
    private Integer limitError;

    @Schema(name = "maxErrorNum", title = "允许最大授权错误次数，当限制授权错误时必填")
    private Integer maxErrorNum;

    @Schema(name = "clientStatus", title = "状态：0-未启用,1-启用，2-锁定，默认0", required = true)
    @NotBlankOrNull(message = "状态不能为空")
    private Integer clientStatus;

    @Schema(name = "accessTokenValiditySeconds", title = "访问token的有效时长(单位s)，默认1800秒", required = true)
    @NotBlankOrNull(message = "访问token的有效时长不能为空")
    private Integer accessTokenValiditySeconds;

    @Schema(name = "refreshTokenValiditySeconds", title = "刷新token的有效时长(单位s)，默认604800秒,即7天", required = true)
    @NotBlankOrNull(message = "刷新token的有效时长不能为空")
    private Integer refreshTokenValiditySeconds;

    @Schema(name = "additionalInformation", title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "havaScoped", title = "是否领域，0-不是,1-是。默认0", required = true)
    @NotBlankOrNull(message = "是否领域不能为空")
    private Integer havaScoped;

    @Schema(name = "scopes", title = "领域,json array", required = true)
    private List<String> scopes;

    @Schema(name = "webRegisteredRedirectUri", title = "授权后跳转的URI（授权码模式必填）", required = true)
    private String webRegisteredRedirectUri;

    @Schema(name = "havaAutoApprove", title = "是否自动批准：0-不自动，1-自动,默认0", required = true)
    @NotBlankOrNull(message = "是否自动批准不能为空")
    private Integer havaAutoApprove;

    @Schema(name = "codeValiditySeconds", title = "授权码有效时常(单位s)，默认300秒")
    private Integer codeValiditySeconds;
}
