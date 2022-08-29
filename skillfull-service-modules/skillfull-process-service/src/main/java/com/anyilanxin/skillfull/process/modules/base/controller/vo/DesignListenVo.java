// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程监听添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:17:59
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DesignListenVo implements Serializable {
    private static final long serialVersionUID = -98880760854177415L;

    @Schema(name = "listenCode", title = "监听编码", required = true)
    @NotBlankOrNull(message = "监听编码不能为空")
    private String listenCode;

    @Schema(name = "listenType", title = "监听类型", required = true)
    @NotBlankOrNull(message = "监听类型不能为空")
    private Integer listenType;

    @Schema(name = "listenName", title = "监听名称", required = true)
    @NotBlankOrNull(message = "监听名称不能为空")
    private String listenName;

    @Schema(name = "classPath", title = "类路径", required = true)
    @NotBlankOrNull(message = "类路径不能为空")
    private String classPath;

    @Schema(name = "eventType", title = "事件属性", required = true)
    @NotBlankOrNull(message = "事件属性不能为空")
    private String eventType;

    @Schema(name = "listenState", title = "监听状态:0-禁用,1-启用。默认0", required = true)
    @NotBlankOrNull(message = "监听状态:0-禁用,1-启用。默认0不能为空")
    private Integer listenState;

    @Schema(name = "listenValueType", title = "值类型:0-Java类,1-EL表达式,2-Spring表达式", required = true)
    @NotBlankOrNull(message = "值类型:0-Java类,1-EL表达式,2-Spring表达式不能为空")
    private Integer listenValueType;

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

    @Schema(name = "createTenantId", title = "创建租户id", required = true)
    @NotBlankOrNull(message = "创建租户id不能为空")
    private String createTenantId;

    @Schema(name = "createUserName", title = "创建用户姓名", required = true)
    @NotBlankOrNull(message = "创建用户姓名不能为空")
    private String createUserName;

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
