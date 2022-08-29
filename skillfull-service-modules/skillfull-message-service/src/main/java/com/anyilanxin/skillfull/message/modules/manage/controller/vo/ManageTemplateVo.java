// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.controller.vo;

import com.anyilanxin.skillfull.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotInEnum;
import com.anyilanxin.skillfull.message.core.constant.impl.MsgTemplateType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 消息模板添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
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
    @NotInEnum(message = "模板类型只能为:", enumClass = MsgTemplateType.class, autoMessage = true)
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
