/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.message.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotInEnum;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.MsgTemplateCommonChannelType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息模板添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:42
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class ManageTemplateVo implements Serializable {
    private static final long serialVersionUID = -37262014870972303L;

    @Schema(name = "templateName", title = "模板名称")
    private String templateName;

    @Schema(name = "templateStatus", title = "模板状态:0-禁用,1-启用", required = true)
    @NotBlankOrNull(message = "模板状态:0-禁用,1-启用不能为空")
    private Integer templateStatus;

    @Schema(name = "templateCode", title = "模板code", required = true)
    @NotBlankOrNull(message = "模板code不能为空")
    private String templateCode;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码")
    private String templateThirdCode;

    @Schema(name = "sendMaxNum", title = "最大重试次数,默认0-不重试", required = true)
    @NotBlankOrNull(message = "最大重试次数,默认0-不重试不能为空")
    private Integer sendMaxNum;

    @Schema(name = "templateType", title = "模板类型:1-微信模板,2-短信,3-邮件", required = true)
    @NotBlankOrNull(message = "模板类型:1-微信模板,2-短信,3-邮件不能为空")
    @NotInEnum(message = "模板类型只能为:", enumClass = MsgTemplateCommonChannelType.class, autoMessage = true)
    private Integer templateType;

    @Schema(name = "limitSend", title = "是否限制发送次数：0-不限制,1-限制。默认0", required = true)
    @NotBlankOrNull(message = "是否限制发送次数：0-不限制,1-限制。默认0不能为空")
    @NotInEnum(message = "是否限制发送次数只能为:", enumClass = CommonNotHaveType.class, autoMessage = true)
    private Integer limitSend;

    @Schema(name = "maxSendNum", title = "每天允许最大发送次数,当启用限制次数时有效，默认10")
    private Integer maxSendNum;

    @Schema(name = "templateContent", title = "模板内容")
    private String templateContent;

    @Schema(name = "templateContentDescribe", title = "模板字段说明信息")
    private String templateContentDescribe;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
