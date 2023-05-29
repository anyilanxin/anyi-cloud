

package com.anyilanxin.anyicloud.process.modules.auxiliary.entity;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloudee.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程定义扩展属性信息(ExtendProperty)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "act_custom_extend_property", autoResultMap = true)
public class ExtendPropertyEntity extends BaseEntity {
    private static final long serialVersionUID = 151155484181408634L;

    @TableId
    private String propertyId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * bpmn活动定义key,如果是模型级变量则为流程id
     */
    private String bpmnActivityDefinitionKey;

    /**
     * 扩展属性内容
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONObject extendPropertyInfo;

    /**
     * 扩展属性key
     */
    private String extendPropertyKey;
}
