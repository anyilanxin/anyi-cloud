

package com.anyilanxin.anyicloud.process.modules.auxiliary.entity;

import com.anyilanxin.anyicloudee.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程连线(SequenceFlow)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-04-26 15:31:28
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("act_custom_sequence_flow")
public class SequenceFlowEntity extends BaseEntity {
    private static final long serialVersionUID = 118704983824127700L;

    @TableId
    private String sequenceFlowId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 目标活动 id
     */
    private String targetBpmnActivityId;

    /**
     * 目标活动实例 id
     */
    private String targetActivityInstanceId;

    /**
     * 元素bpmn id
     */
    private String elementBpmnId;

    /**
     * 备注
     */
    private String remark;
}
