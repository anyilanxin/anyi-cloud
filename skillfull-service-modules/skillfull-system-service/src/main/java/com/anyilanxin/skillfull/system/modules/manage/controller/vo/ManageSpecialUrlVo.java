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


package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由特殊地址添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 09:34:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class ManageSpecialUrlVo implements Serializable {
    private static final long serialVersionUID = -43635735021401802L;

    @Schema(name = "urlName", title = "接口名称", required = true)
    @NotBlankOrNull(message = "接口名称不能为空")
    private String urlName;

    @Schema(name = "urlDescribe", title = "接口描述")
    private String urlDescribe;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)", required = true)
    @NotBlankOrNull(message = "特殊url类型不能为空")
    private Integer specialUrlType;

    @Schema(name = "urlType", title = "地址类型:0-系统,1-自定义,默认0")
    private Integer urlType;

    @Schema(name = "specialStatus", title = "特殊地址状态:0-禁用,1-启用，默认0", required = true)
    @NotBlankOrNull(message = "特殊地址状态不能为空")
    private Integer specialStatus;

    @Schema(name = "limitMethod", title = "限制请求方法：0-不限制,1-限制", required = true)
    @NotBlankOrNull(message = "限制请求方法不能为空")
    private Integer limitMethod;

    @Schema(name = "requestMethod", title = "允许的请求方法,多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "url", title = "地址,服务地址或http地址", required = true)
    @NotBlankOrNull(message = "地址,服务地址或http地址不能为空")
    private String url;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
