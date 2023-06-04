/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
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
