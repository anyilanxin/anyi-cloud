/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.corecommon.model.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * web端安全基本信息
 *
 * @author zxiaozhou
 * @date 2021-07-13 09:30
 * @since JDK1.8
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
