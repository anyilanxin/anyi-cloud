// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * token信息,用户登录返回给前端
 *
 * @author zxiaozhou
 * @date 2020-09-28 23:46
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 1753274955869772123L;

    @Schema(name = "token", title = "令牌信息")
    private String token;

    @Schema(name = "validityInSeconds", title = "令牌有效时间(单位s)")
    private long validityInSeconds;

    @Schema(name = "expiresAt", title = "令牌有效时间止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime expiresAt;

    @Schema(name = "tokenHeaderKey", title = "令牌放入请求头key")
    private String tokenHeaderKey;

    @Schema(name = "tokenHeaderStartWith", title = "请求头令牌前缀")
    private String tokenHeaderStartWith;

    @Schema(name = "tokenType", title = "令牌类型")
    private String tokenType;

    @Schema(name = "tokenQueryKey", title = "令牌放入地址时key")
    private String tokenQueryKey;

    @Schema(name = "refreshTokenKey", title = "刷新令牌标识")
    private String refreshTokenKey;
}
