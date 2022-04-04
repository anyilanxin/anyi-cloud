// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 消息发送记录表分页查询Response
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:30
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ManageSendRecordPageDto implements Serializable {
    private static final long serialVersionUID = 549813035968671996L;

    @Schema(name = "esId", title = "消息id")
    private String esId;

    @Schema(name = "esTitle", title = "消息标题")
    private String esTitle;

    @Schema(name = "esType", title = "推送方式:1-微信模板,2-短信,3-邮件,4-系统公告")
    private Integer esType;

    @Schema(name = "esReceiver", title = "接收人")
    private String esReceiver;

    @Schema(name = "esParam", title = "推送所需参数Json格式")
    private String esParam;

    @Schema(name = "esContent", title = "推送内容")
    private String esContent;

    @Schema(name = "esSendTime", title = "推送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime esSendTime;

    @Schema(name = "esSendStatus", title = "推送状态:0未推送 1推送成功 2推送失败")
    private String esSendStatus;

    @Schema(name = "esSendNum", title = "发送次数 超过5次不再发送")
    private Integer esSendNum;

    @Schema(name = "esResult", title = "推送失败原因")
    private String esResult;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}