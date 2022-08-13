package com.anyilanxin.skillfull.corecommon.model.system;

import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.utils.encryption.RSAUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 用户数据加解密信息
 *
 * @author zxiaozhou
 * @date 2021-07-13 09:30
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDataSecurityModel extends RSAUtils.RsaKey {

    private static final long serialVersionUID = 307522993673479645L;
    @Schema(name = "validityInSeconds", title = "有效时间(秒)")
    private long validityInSeconds;

    @Schema(name = "expiresAt", title = "有效时间止", type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expiresAt;

    @Schema(name = "currentRefreshTime", title = "最近一次刷新时间", type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime currentRefreshTime;

    @Schema(name = "detectInSeconds", title = "触发续期检测最小时间(单位:秒)")
    private long detectInSeconds;

    @Schema(name = "renewInSeconds", title = "续期时间(单位:秒)")
    private long renewInSeconds;

    @Schema(name = "serialNumber", title = "请求序列号")
    private String serialNumber;

    @Schema(name = "serialNumberKey", title = "请求序列号放入请求头或者query时的key")
    private String serialNumberKey;

    @Schema(name = "refreshHeaderKey", title = "请求头刷新密钥信息标识(需要url解码)")
    private String refreshHeaderKey;

    @Schema(name = "ciphertextKey", title = "密文放入body或query时的key")
    private String ciphertextKey;

    @Schema(name = "secretKey", title = "密钥放入body或者query时的key")
    private String secretKey;

    @Schema(name = "secret", title = "密钥")
    private String secret;

    @Schema(name = "queryOtherKey", title = "get参数额外key")
    private String queryOtherKey;
}
