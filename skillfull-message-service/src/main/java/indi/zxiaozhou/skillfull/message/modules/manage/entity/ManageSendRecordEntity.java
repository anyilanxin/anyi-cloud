// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 消息发送记录表(ManageSendRecord)Entity
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:53:24
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("msg_manage_send_record")
public class ManageSendRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 292371971745740026L;

    @TableId
    private String esId;

    @Schema(name = "esTitle", title = "消息标题")
    private String esTitle;

    @Schema(name = "templateId", title = "模板id")
    private String templateId;

    @Schema(name = "templateCode", title = "模板code")
    private String templateCode;

    @Schema(name = "templateOriginalData", title = "模板原始数据,json")
    private String templateOriginalData;

    @Schema(name = "esType", title = "推送方式:1-微信模板,2-短信,3-邮件,4-系统公告")
    private Integer esType;

    @Schema(name = "esReceiver", title = "接收人")
    private String esReceiver;

    @Schema(name = "toPage", title = "跳转页面路径")
    private String toPage;

    @Schema(name = "esParam", title = "推送所需参数")
    private String esParam;

    @Schema(name = "esContent", title = "推送内容")
    private String esContent;

    @Schema(name = "esSendTime", title = "推送时间")
    private LocalDateTime esSendTime;

    @Schema(name = "esSendStatus", title = "推送状态:0未推送 1推送成功 2推送失败")
    private String esSendStatus;

    @Schema(name = "esSendMaxNum", title = "最大发送次数，默认1")
    private Integer esSendMaxNum;

    @Schema(name = "esSendNum", title = "已经发送次数，默认1")
    private Integer esSendNum;

    @Schema(name = "esResults", title = "推送失败原因，json数组")
    private String esResults;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "remark", title = "备注")
    private String remark;
}