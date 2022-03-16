package indi.zxiaozhou.skillfull.corecommon.base.model.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.zxiaozhou.skillfull.corecommon.base.model.system.BaseSecurityModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户token解密信息
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:13
 * @since JDK11
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class LoginTokenSecurityModel extends BaseSecurityModel implements Serializable {
    private static final long serialVersionUID = -2147772099404900857L;

    @Schema(name = "tokenValidityInSeconds", title = "token有效时间(秒)")
    private long tokenValidityInSeconds = 60 * 60 * 3;

    @Schema(name = "expiresAt", title = "token有效时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expiresAt;

    @Schema(name = "currentRefreshTokenTime", title = "最近一次刷新token时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentRefreshTokenTime;

    @Schema(name = "tokenDetectInSeconds", title = "触发令牌续期检测最小时间(单位:秒)")
    private long tokenDetectInSeconds = 1800L;

    @Schema(name = "tokenRenewInSeconds", title = "令牌续期时间(单位:秒)")
    private long tokenRenewInSeconds = 3600L;

    @Schema(name = "token", title = "token内容")
    private String token;
}
