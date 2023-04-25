package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 流程监听分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:18:04
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class DesignListenPageVo extends BasePageVo {
    private static final long serialVersionUID = 827530124435912619L;

    @Schema(name = "listenId", title = "监听id")
    private String listenId;

    @Schema(name = "listenCode", title = "监听编码")
    private String listenCode;

    @Schema(name = "listenType", title = "监听类型")
    private Integer listenType;

    @Schema(name = "listenName", title = "监听名称")
    private String listenName;

    @Schema(name = "classPath", title = "类路径")
    private String classPath;

    @Schema(name = "eventType", title = "事件属性")
    private String eventType;

    @Schema(name = "listenState", title = "监听状态:0-禁用,1-启用。默认0")
    private Integer listenState;

    @Schema(name = "listenValueType", title = "值类型:0-Java类,1-EL表达式,2-Spring表达式")
    private Integer listenValueType;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

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
