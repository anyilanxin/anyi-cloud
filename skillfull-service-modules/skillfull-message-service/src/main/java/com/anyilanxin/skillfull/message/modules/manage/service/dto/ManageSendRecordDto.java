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
package com.anyilanxin.skillfull.message.modules.manage.service.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 消息发送记录表查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:41
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageSendRecordDto implements Serializable {
    private static final long serialVersionUID = 998796543102578308L;

    @Schema(name = "sendRecordId", title = "发送记录id")
    private String sendRecordId;

    @Schema(name = "templateId", title = "模板id")
    private String templateId;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码")
    private String templateThirdCode;

    @Schema(name = "templateCode", title = "模板code")
    private String templateCode;

    @Schema(name = "sendBatchNo", title = "发送批次号")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "sendStatus", title = "发送状态:0-失败，1-成功，默认0")
    private String sendStatus;

    @Schema(name = "sendResults", title = "发送失败原因")
    private String sendResults;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
