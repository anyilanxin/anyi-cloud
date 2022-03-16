// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户-代理人表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:12:37
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
public class CommonUserAgentVo implements Serializable {
    private static final long serialVersionUID = -84692156627807717L;

    @Schema(name = "userId", title = "用户名id", required = true)
    @NotBlankOrNull(message = "用户名id不能为空")
    private String userId;

    @Schema(name = "agentUserId", title = "代理人用户id", required = true)
    @NotBlankOrNull(message = "代理人用户id不能为空")
    private String agentUserId;

    @Schema(name = "agentStartTime", title = "代理开始时间", required = true)
    @NotBlankOrNull(message = "代理开始时间不能为空")
    private LocalDateTime agentStartTime;

    @Schema(name = "agentEndTime", title = "代理结束时间", required = true)
    @NotBlankOrNull(message = "代理结束时间不能为空")
    private LocalDateTime agentEndTime;

    @Schema(name = "isLimit", title = "是否限制时间:0-不限制,1-限制，默认0", required = true)
    @NotBlankOrNull(message = "是否限制时间:0-不限制,1-限制，默认0不能为空")
    private Integer isLimit;

    @Schema(name = "agentStatus", title = "状态：0-无效，1-有效,默认0", required = true)
    @NotBlankOrNull(message = "状态：0-无效，1-有效,默认0不能为空")
    private Integer agentStatus;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键", required = true)
    @NotBlankOrNull(message = "唯一索引帮助字段,默认1，如果删除该值为主键不能为空")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注", required = true)
    @NotBlankOrNull(message = "备注不能为空")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码", required = true)
    @NotBlankOrNull(message = "创建区域编码不能为空")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码", required = true)
    @NotBlankOrNull(message = "创建职位编码不能为空")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码", required = true)
    @NotBlankOrNull(message = "创建机构系统编码不能为空")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码", required = true)
    @NotBlankOrNull(message = "创建系统编码不能为空")
    private String createSystemCode;

    @Schema(name = "createUserId", title = "创建用户id", required = true)
    @NotBlankOrNull(message = "创建用户id不能为空")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名", required = true)
    @NotBlankOrNull(message = "创建用户姓名不能为空")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id", required = true)
    @NotBlankOrNull(message = "创建租户id不能为空")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间", required = true)
    @NotBlankOrNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id", required = true)
    @NotBlankOrNull(message = "更新用户id不能为空")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名", required = true)
    @NotBlankOrNull(message = "更新用户姓名不能为空")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", required = true)
    @NotBlankOrNull(message = "更新时间不能为空")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0", required = true)
    @NotBlankOrNull(message = "删除状态:0-正常,1-已删除,默认0不能为空")
    private Integer delFlag;

}