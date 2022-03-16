// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户在线信息
 *
 * @author zxiaozhou
 * @date 2021-05-29 23:44
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginOnlineInfoModel implements Serializable {
    private static final long serialVersionUID = -3529509184883905229L;

    @Schema(name = "loginIp", title = "登录ip")
    private String loginIp;

    @Schema(name = "loginCode", title = "登录编号")
    private String loginCode;

    @Schema(name = "tenantId", title = "当前租户id")
    private String tenantId;

    @Schema(name = "loginEndpoint(与LoginEndpointType一致)", title = "登录端点")
    private String loginEndpoint;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "loginEquipment", title = "登录设备")
    private String loginEquipment;

    @Schema(name = "loginTime", title = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime loginTime;

    @Schema(name = "currentRefreshTokenTime", title = "最近一次刷新token时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentRefreshTokenTime;

    @Schema(name = "expiresAt", title = "token有效时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime expiresAt;

    @Schema(name = "loginType", title = "登录类型:0-单设备登录,1-多设备登录")
    private Integer loginType;

    @Schema(name = "credentials", title = "security credentials")
    private Object credentials;

    @Schema(name = "details", title = "auth details")
    private Object details;


}
