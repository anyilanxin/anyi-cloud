// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 流程模型历史(DesignModelHistory)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:52:03
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName("act_custom_design_model_history")
public class DesignModelHistoryEntity extends BaseEntity {
    private static final long serialVersionUID = -68433941014688158L;

    @TableId
    private String historyModelId;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 流程定义key,多个逗号隔开
     */
    private String processDefinitionKeys;

    /**
     * 流程定义ids,多个逗号隔开
     */
    private String processDefinitionIds;

    /**
     * bpmn模型(转换为base64存储)
     */
    private String diagramData;

    /**
     * 部署名称
     */
    private String deploymentName;

    /**
     * 模型名称
     */
    private String diagramNames;

    /**
     * 部署id
     */
    private String deploymentId;

    /**
     * 资源名称
     */
    private String resourceNames;

    /**
     * 资源ids
     */
    private String resourceIds;

    /**
     * 是否pool模型,0-不是,1-是。默认0
     */
    private Integer havePool;

    /**
     * 部署时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime deploymentTime;

    /**
     * 模型类别
     */
    private String category;

    /**
     * 当前模型版本
     */
    private Integer version;

    /**
     * 备注
     */
    private String remark;
}
