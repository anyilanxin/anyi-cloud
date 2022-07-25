// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权客户端信息分页查询Response
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
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacClientDetailsPageDto implements Serializable {
    private static final long serialVersionUID = 735495266631499851L;

    @Schema(name = "clientDetailId", title = "客户端信息id")
    private String clientDetailId;

    @Schema(name = "clientId", title = "客户端id")
    private String clientId;

    @Schema(name = "clientName", title = "客户端名称")
    private String clientName;

    @Schema(name = "clientIco", title = "客户端图标")
    private String clientIco;

    @Schema(name = "signatureRequired", title = "是否验签:0-不验签，1-验签，默认1")
    private Integer signatureRequired;

    @Schema(name = "signatureKey", title = "数据签名key，当需要验签时必填")
    private String signatureKey;

    @Schema(name = "singleLogin", title = "是否单设备登录：0-不是,1-是，默认0")
    private Integer singleLogin;

    @Schema(name = "singleLoginType", title = "单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录")
    private Integer singleLoginType;

    @Schema(name = "innerSystem", title = "是否内部系统：0-不是，1-是，默认0")
    private Integer innerSystem;

    @Schema(name = "lastAuthTime", title = "上次授权时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime lastAuthTime;

    @Schema(name = "limitError", title = "限制授权错误次数:0-不限制,1-限制。默认0")
    private Integer limitError;

    @Schema(name = "maxErrorNum", title = "允许最大授权错误次数，当限制授权错误时必填")
    private Integer maxErrorNum;

    @Schema(name = "clientStatus", title = "状态：0-未启用,1-启用，2-锁定，默认0")
    private Integer clientStatus;

    @Schema(name = "accessTokenValiditySeconds", title = "访问token的有效时长(单位s)，默认1800秒")
    private Integer accessTokenValiditySeconds;

    @Schema(name = "refreshTokenValiditySeconds", title = "刷新token的有效时长(单位s)，默认604800秒,即7天")
    private Integer refreshTokenValiditySeconds;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "havaScoped", title = "是否领域，0-不是,1-是。默认0")
    private Integer havaScoped;

    @Schema(name = "webRegisteredRedirectUri", title = "授权后跳转的URI（授权码模式必填）")
    private String webRegisteredRedirectUri;

    @Schema(name = "havaAutoApprove", title = "是否自动批准：0-不自动，1-自动,默认0")
    private Integer havaAutoApprove;

    @Schema(name = "codeValiditySeconds", title = "授权码有效时常(单位s)，默认300秒")
    private Integer codeValiditySeconds;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;
}
