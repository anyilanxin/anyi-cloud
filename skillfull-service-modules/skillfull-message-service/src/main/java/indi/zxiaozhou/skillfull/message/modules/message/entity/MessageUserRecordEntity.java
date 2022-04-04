// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户消息记录(MessageUserRecord)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:54:04
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
@TableName("msg_message_user_record")
public class MessageUserRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 579244319225918329L;

    @TableId
    private String msgId;

    @Schema(name = "title", title = "标题")
    private String title;

    @Schema(name = "msgAbstract", title = "摘要")
    private String msgAbstract;

    @Schema(name = "msgContent", title = "内容")
    private String msgContent;

    @Schema(name = "busiState", title = "三方业务状态")
    private Integer busiState;

    @Schema(name = "busiId", title = "三方业务id")
    private String busiId;

    @Schema(name = "iconType", title = "图标类型：0-系统自带,1-自定义")
    private Integer iconType;

    @Schema(name = "icon", title = "内容")
    private String icon;

    @Schema(name = "senderUserName", title = "发布人姓名")
    private String senderUserName;

    @Schema(name = "senderUserId", title = "发布人")
    private String senderUserId;

    @Schema(name = "otherInfo", title = "三方业务其他信息")
    private String otherInfo;

    @Schema(name = "sendTime", title = "发布时间")
    private LocalDateTime sendTime;

    @Schema(name = "readStatus", title = "阅读状态:0-未阅读,1-阅读。默认0")
    private Integer readStatus;

    @Schema(name = "userId", title = "消息用户id")
    private String userId;

    @Schema(name = "msgType", title = "消息类型:0-系统公告,1-代办事项")
    private Integer msgType;

    @Schema(name = "pageUrl", title = "页面url")
    private String pageUrl;

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