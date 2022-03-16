// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.controller.vo;

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 消息发送记录表分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:26
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
@Schema
public class ManageSendRecordPageVo extends BasePageVo {
    private static final long serialVersionUID = 230654704389955136L;

    @Schema(name = "esTitle", title = "消息标题")
    private String esTitle;

    @Schema(name = "esType", title = "推送方式:1-微信模板,2-短信,3-邮件,4-系统公告")
    private Integer esType;

    @Schema(name = "esReceiver", title = "接收人")
    private String esReceiver;

    @Schema(name = "esSendStatus", title = "推送状态:0未推送 1推送成功 2推送失败")
    private String esSendStatus;

}