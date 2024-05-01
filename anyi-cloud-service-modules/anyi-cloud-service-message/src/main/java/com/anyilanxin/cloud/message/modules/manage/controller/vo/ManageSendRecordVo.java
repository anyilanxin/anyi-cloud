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

package com.anyilanxin.cloud.message.modules.manage.controller.vo;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.cloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息发送记录表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:41
 * @since 1.0.0
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class ManageSendRecordVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 442640397503934465L;

    @Schema(name = "templateId", title = "模板id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "模板id不能为空")
    private String templateId;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "三方系统模板编码不能为空")
    private String templateThirdCode;

    @Schema(name = "templateCode", title = "模板code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "模板code不能为空")
    private String templateCode;

    @Schema(name = "sendBatchNo", title = "发送批次号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "发送批次号不能为空")
    private String sendBatchNo;

    @Schema(name = "businessId", title = "业务id")
    private String businessId;

    @Schema(name = "templateOriginalData", title = "模板原始数据,json")
    private JSONObject templateOriginalData;

    @Schema(name = "sendType", title = "发送方式:1-微信模板,2-短信,3-邮件")
    private Integer sendType;

    @Schema(name = "sendNum", title = "发送次数")
    private Integer sendNum;

    @Schema(name = "sendMaxNum", title = "最大发送次数")
    private Integer sendMaxNum;

    @Schema(name = "sendReceiver", title = "接收人")
    private String sendReceiver;

    @Schema(name = "sendContent", title = "发送内容")
    private String sendContent;

    @Schema(name = "sendTime", title = "发送时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "sendStatus", title = "发送状态:0-失败，1-成功，默认0")
    private String sendStatus;

    @Schema(name = "sendResults", title = "发送失败原因")
    private String sendResults;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
