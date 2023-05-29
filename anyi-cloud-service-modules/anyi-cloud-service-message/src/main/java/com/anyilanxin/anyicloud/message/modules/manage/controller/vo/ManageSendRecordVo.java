

package com.anyilanxin.anyicloud.message.modules.manage.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private static final long serialVersionUID = 442640397503934465L;

    @Schema(name = "templateId", title = "模板id", required = true)
    @NotBlankOrNull(message = "模板id不能为空")
    private String templateId;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码", required = true)
    @NotBlankOrNull(message = "三方系统模板编码不能为空")
    private String templateThirdCode;

    @Schema(name = "templateCode", title = "模板code", required = true)
    @NotBlankOrNull(message = "模板code不能为空")
    private String templateCode;

    @Schema(name = "sendBatchNo", title = "发送批次号", required = true)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "sendStatus", title = "发送状态:0-失败，1-成功，默认0")
    private String sendStatus;

    @Schema(name = "sendResults", title = "发送失败原因")
    private String sendResults;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
