// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程表单详细条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:17:22
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class DesignFormDetailQueryVo implements Serializable {
    private static final long serialVersionUID = 784146504951916440L;

    @Schema(name = "formDetailId", title = "表单详细id")
    private String formDetailId;

    @Schema(name = "fromFiledName", title = "表单字段名称")
    private String fromFiledName;

    @Schema(name = "formFiledType", title = "表单字段类型:0-字符串,1-数字,2-日期,3-时间。默认0")
    private Integer formFiledType;

    @Schema(name = "formFiledLabel", title = "表单字段描述")
    private String formFiledLabel;

    @Schema(name = "formFiledMust", title = "表单字段是否必填:0-非必填,1-必填。默认0")
    private Integer formFiledMust;

    @Schema(name = "formFiledErrorMsg", title = "表单字段必填验证错误提示信息(当表单字段必填时有效)")
    private String formFiledErrorMsg;

    @Schema(name = "formFiledSort", title = "表单字段排序,越小越靠前。默认0")
    private Integer formFiledSort;

    @Schema(name = "formFiledState", title = "表单字段状态:0-禁用,1-启用。默认0")
    private Integer formFiledState;

    @Schema(name = "remark", title = "备注")
    private String remark;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}