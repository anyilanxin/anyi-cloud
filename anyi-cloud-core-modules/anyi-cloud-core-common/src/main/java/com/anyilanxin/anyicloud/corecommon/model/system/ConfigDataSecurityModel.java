

package com.anyilanxin.anyicloud.corecommon.model.system;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.utils.encryption.RSAUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据加解密配置
 *
 * @author zxh
 * @date 2021-07-13 09:30
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConfigDataSecurityModel extends RSAUtils.RsaKey {

    private static final long serialVersionUID = 7403318026445432214L;

    @Schema(name = "validityInSeconds", title = "有效时间(秒)")
    private long validityInSeconds = 60 * 60 * 12 * 300;

    @Schema(name = "expiresAt", title = "有效时间止", type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expiresAt;

    @Schema(name = "currentRefreshTime", title = "最近一次刷新时间", type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime currentRefreshTime;

    @Schema(name = "detectInSeconds", title = "触发续期检测最小时间(单位:秒)")
    private long detectInSeconds = 1800L;

    @Schema(name = "renewInSeconds", title = "续期时间(单位:秒)")
    private long renewInSeconds = 3600L;

    @Schema(name = "serialNumberKey", title = "请求序列号放入请求头或者query时的key")
    private String serialNumberKey = "serial";

    @Schema(name = "refreshHeaderKey", title = "请求头刷新密钥信息标识(需要url解码)")
    private String refreshHeaderKey = "refreshKey";

    @Schema(name = "ciphertextKey", title = "密文放入body或query时的key")
    private String ciphertextKey = "textOne";

    @Schema(name = "secretKey", title = "密钥放入body或者query时的key")
    private String secretKey = "textTwo";

    @Schema(name = "queryOtherKey", title = "get参数额外key")
    private String queryOtherKey = "_t";
}
