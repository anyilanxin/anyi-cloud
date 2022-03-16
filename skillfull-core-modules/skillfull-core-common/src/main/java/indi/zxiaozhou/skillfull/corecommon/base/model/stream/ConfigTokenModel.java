// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * token相关配置
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:34
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ConfigTokenModel implements Serializable {
    private static final long serialVersionUID = -8035187351349997365L;

    @Schema(name = "tokenHeaderKey", title = "令牌放入请求头key")
    private String tokenHeaderKey = "Authorization";

    @Schema(name = "tokenHeaderStartWith", title = "请求头令牌前缀")
    private String tokenHeaderStartWith = "Bearer ";

    @Schema(name = "tokenQueryKey", title = "令牌放入query时的key")
    private String tokenQueryKey = "access-token";

    @Schema(name = "tokenValidityInSeconds", title = "令牌过期时间(单位:秒)")
    private long tokenValidityInSeconds = 3600L;

    @Schema(name = "tokenDetectInSeconds", title = "触发续期检测最小时间(单位:秒)")
    private long tokenDetectInSeconds = 1800L;

    @Schema(name = "tokenRenewInSeconds", title = "续期时间(单位:秒)")
    private long tokenRenewInSeconds = 3600L;

    @Schema(name = "refreshTokenKey", title = "请求头刷新令牌标识")
    private String refreshTokenKey = "refresh-token";

    @Schema(name = "loginType", title = "登录方式:0-单设备,1-多设备")
    private Integer loginType = 1;
}
