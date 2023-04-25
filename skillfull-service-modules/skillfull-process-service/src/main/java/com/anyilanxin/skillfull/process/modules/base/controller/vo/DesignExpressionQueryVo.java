package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 流程表达式条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-27 00:20:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class DesignExpressionQueryVo implements Serializable {
    private static final long serialVersionUID = -77445021074393966L;

    @Schema(name = "expressionId", title = "表达式id")
    private String expressionId;

    @Schema(name = "handleType", title = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他")
    private Integer handleType;

    @Schema(name = "implementClass", title = "实现class路径")
    private String implementClass;

    @Schema(name = "example", title = "示例")
    private String example;

    @Schema(name = "remark", title = "备注")
    private String remark;

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

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

    @Schema(name = "expressionName", title = "表达式名称")
    private String expressionName;

    @Schema(name = "expressionType", title = "表达式类型:1-表达式,2-委托表达式")
    private Integer expressionType;

    @Schema(name = "elExpressionValue", title = "El编码值,‘${’开头’}‘结尾，唯一")
    private String elExpressionValue;

    @Schema(name = "expressionState", title = "表达式状态:0-禁用,1-启用")
    private Integer expressionState;

}
