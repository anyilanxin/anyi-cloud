

package com.anyilanxin.anyicloud.corecommon.model.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * web端安全基本信息
 *
 * @author zxh
 * @date 2021-07-13 09:30
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WebSecurityModel implements Serializable {

    @Schema(name = "validityInSeconds", title = "有效时间(秒)(请在这个时间后手动刷新或者自动刷新)")
    private long validityInSeconds;

    @Schema(name = "expiresAt", title = "有效时间止(请在这个时间后手动刷新或者自动刷新)", type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expiresAt;

    @Schema(name = "privateKey", title = "公钥")
    private String publicKey;

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
}
