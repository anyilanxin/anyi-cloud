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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息模板查询Response
 *
 * @author zxiaozhou
 * @date 2021-05-18 00:10:39
 * @since JDK1.8
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
public class ManageTemplateDto implements Serializable {
    private static final long serialVersionUID = -30646836486118438L;

    @Schema(name = "templateId", title = "模板id")
    private String templateId;

    @Schema(name = "templateName", title = "模板名称")
    private String templateName;

    @Schema(name = "templateStatus", title = "模板状态:0-禁用,1-启用")
    private Integer templateStatus;

    @Schema(name = "toPage", title = "跳转页面路径")
    private String toPage;

    @Schema(name = "templateCode", title = "模板code")
    private String templateCode;

    @Schema(name = "templateThirdCode", title = "三方系统模板编码")
    private String templateThirdCode;

    @Schema(name = "smsSignName", title = "短信验证码签名")
    private String smsSignName;

    @Schema(name = "sendMaxNum", title = "最大重试次数,默认0-不重试")
    private Integer sendMaxNum;

    @Schema(name = "templateType", title = "模板类型:1-微信模板,2-短信,3-邮件,4-系统公告")
    private Integer templateType;

    @Schema(name = "isValidation", title = "是否验证类:0-不是,1-是,默认0")
    private Integer isValidation;

    @Schema(name = "validationTime", title = "验证类缓存有效时间(单位秒)")
    private Integer validationTime;

    @Schema(name = "templateContent", title = "模板内容")
    private String templateContent;

    @Schema(name = "templateContentDescribe", title = "模板字段说明信息")
    private String templateContentDescribe;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}