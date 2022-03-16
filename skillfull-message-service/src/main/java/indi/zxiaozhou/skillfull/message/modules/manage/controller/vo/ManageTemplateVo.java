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

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 消息模板添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:52:50
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
public class ManageTemplateVo implements Serializable {
    private static final long serialVersionUID = 423498009397065440L;

    @Schema(name = "templateName", title = "模板名称", required = true)
    @NotBlankOrNull(message = "模板名称不能为空")
    private String templateName;

    @Schema(name = "templateStatus", title = "模板状态:0-禁用,1-启用", required = true)
    @NotBlankOrNull(message = "模板状态:0-禁用,1-启用不能为空")
    private Integer templateStatus;

    @Schema(name = "toPage", title = "跳转页面路径")
    private String toPage;

    @Schema(name = "templateCode", title = "模板code", required = true)
    @NotBlankOrNull(message = "模板code不能为空")
    private String templateCode;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码")
    private String templateThirdCode;

    @Schema(name = "smsSignName", title = "短信验证码签名")
    private String smsSignName;

    @Schema(name = "sendMaxNum", title = "最大重试次数,默认0-不重试")
    private Integer sendMaxNum;

    @Schema(name = "templateType", title = "模板类型:1-微信模板,2-短信,3-邮件,4-系统公告", required = true)
    @NotBlankOrNull(message = "模板类型不能为空")
    private Integer templateType;

    @Schema(name = "isValidation", title = "是否验证类:0-不是,1-是,默认0", required = true)
    @NotBlankOrNull(message = "是否验证类不能为空")
    private Integer isValidation;

    @Schema(name = "validationTime", title = "验证类缓存有效时间(单位秒)")
    private Integer validationTime;

    @Schema(name = "templateContent", title = "模板内容", required = true)
    @NotBlankOrNull(message = "模板内容不能为空")
    private String templateContent;

    @Schema(name = "templateContentDescribe", title = "模板字段说明信息")
    private String templateContentDescribe;

    @Schema(name = "remark", title = "备注")
    private String remark;

}