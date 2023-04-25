/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.corecommon.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
