// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程模型管理分页查询Response
 *
 * @author zxiaozhou
 * @date 2021-11-25 05:22:56
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DesignModelPageDto implements Serializable {
    private static final long serialVersionUID = -91602774673651153L;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "category", title = "类别")
    private String category;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "modelState", title = "模型状态:0-未部署,1-已经部署,2-新版本待部署,参考常量字段:ModelStateType")
    private Integer modelState;

    @Schema(name = "havePool", title = "是否pool模型,0-不是,1-是。默认0")
    private Integer havePool;

    @Schema(name = "processDefinitionKeys", title = "流程定义key,多个逗号隔开")
    private String processDefinitionKeys;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "deploymentTime", title = "部署时间", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected LocalDateTime deploymentTime;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "version", title = "当前模型版本")
    private Integer version;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;
}
