/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.corecommon.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 日志信息
*
* @author zxiaozhou
* @date 2021-05-06 12:56
* @since JDK1.8
*/
@Setter
@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class GatewayLogModel implements Serializable {
    private static final long serialVersionUID = -5373929396939925329L;

    @Schema(name = "loginEndpoint(与LoginEndpointType一致)", title = "登录端点")
    private String loginEndpoint;

    @Schema(name = "loginEquipment", title = "登录设备")
    private String loginEquipment;

    @Schema(name = "loginIp", title = "登录ip")
    private String loginIp;

    @Schema(name = "loginCode", title = "登录编号")
    private String loginCode;

    @Schema(name = "targetServiceName", title = "目标服务名")
    private String targetServiceName;

    @Schema(name = "targetIp", title = "目标服务ip")
    private String targetIp;

    @Schema(name = "targetPort", title = "目标服务端口")
    private int targetPort;

    @Schema(name = "requestUrl", title = "目的地路径")
    private String targetRequestUrl;

    @Schema(name = "requestAllResult", title = "请求响应所有信息")
    private String requestAllResult;

    @Schema(name = "requestResultData", title = "请求响应数据")
    private String requestResultData;

    @Schema(name = "requestStatus", title = "请求响应状态")
    private Integer requestStatus;

    @Schema(name = "requestHttpStatus", title = "请求http状态")
    private Integer requestHttpStatus;
}
