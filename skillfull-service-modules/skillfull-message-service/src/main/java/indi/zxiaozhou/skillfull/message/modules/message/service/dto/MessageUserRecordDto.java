// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户消息记录查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:48:01
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MessageUserRecordDto implements Serializable {
    private static final long serialVersionUID = 264963135404655627L;

    @Schema(name = "msgId", title = "消息记录id")
    private String msgId;

    @Schema(name = "title", title = "标题")
    private String title;

    @Schema(name = "msgAbstract", title = "摘要")
    private String msgAbstract;

    @Schema(name = "otherInfo", title = "三方业务其他信息")
    private String otherInfo;

    @Schema(name = "busiState", title = "三方业务状态")
    private Integer busiState;

    @Schema(name = "busiId", title = "三方业务id")
    private String busiId;

    @Schema(name = "iconType", title = "图标类型：0-系统自带,1-自定义")
    private Integer iconType;

    @Schema(name = "icon", title = "内容")
    private String icon;

    @Schema(name = "msgContent", title = "内容")
    private String msgContent;

    @Schema(name = "senderUserName", title = "发布人姓名")
    private String senderUserName;

    @Schema(name = "senderUserId", title = "发布人")
    private String senderUserId;

    @Schema(name = "sendTime", title = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "readStatus", title = "阅读状态:0-未阅读,1-阅读。默认0")
    private Integer readStatus;

    @Schema(name = "msgType", title = "消息类型:0-系统公告,1-代办事项")
    private Integer msgType;

    @Schema(name = "pageUrl", title = "页面url")
    private String pageUrl;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}