

package com.anyilanxin.anyicloud.process.modules.base.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程模型管理(DesignModel)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 11:52:03
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("act_custom_design_model")
public class DesignModelEntity extends BaseEntity {
    private static final long serialVersionUID = -12178072556417422L;

    @TableId
    private String modelId;

    /**
     * bpmn模型(转换为base64存储)
     */
    private String diagramData;

    /**
     * 流程定义key,多个逗号隔开
     */
    private String processDefinitionKeys;

    /**
     * 流程定义ids,多个逗号隔开
     */
    private String processDefinitionIds;

    /**
     * 模型名称
     */
    private String diagramNames;

    /**
     * 模型类别
     */
    private String category;

    /**
     * 模型状态:0-未部署,1-已经部署,2-新版本待部署,参考常量字段:ModelStateType
     */
    private Integer modelState;

    /**
     * 是否pool模型,0-不是,1-是。默认0
     */
    private Integer havePool;

    /**
     * 部署名称
     */
    private String deploymentName;

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
     * 部署时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime deploymentTime;

    /**
     * 当前模型版本
     */
    private Integer version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;
}
