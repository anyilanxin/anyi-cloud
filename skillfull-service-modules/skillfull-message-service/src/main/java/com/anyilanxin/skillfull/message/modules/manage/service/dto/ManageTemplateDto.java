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

package com.anyilanxin.skillfull.message.modules.manage.service.dto;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息模板查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageTemplateDto implements Serializable {
    private static final long serialVersionUID = 400261450235058454L;

    @Schema(name = "templateId", title = "模板id")
    private String templateId;

    @Schema(name = "templateName", title = "模板名称")
    private String templateName;

    @Schema(name = "templateStatus", title = "模板状态:0-禁用,1-启用")
    private Integer templateStatus;

    @Schema(name = "templateCode", title = "模板code")
    private String templateCode;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码")
    private String templateThirdCode;

    @Schema(name = "sendMaxNum", title = "最大重试次数,默认0-不重试")
    private Integer sendMaxNum;

    @Schema(name = "templateType", title = "模板类型:1-微信模板,2-短信,3-邮件")
    private Integer templateType;

    @Schema(name = "limitSend", title = "是否限制发送次数：0-不限制,1-限制。默认0")
    private Integer limitSend;

    @Schema(name = "maxSendNum", title = "每天允许最大发送次数,当启用限制次数时有效，默认10")
    private Integer maxSendNum;

    @Schema(name = "templateContent", title = "模板内容")
    private String templateContent;

    @Schema(name = "templateContentDescribe", title = "模板字段说明信息")
    private String templateContentDescribe;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
