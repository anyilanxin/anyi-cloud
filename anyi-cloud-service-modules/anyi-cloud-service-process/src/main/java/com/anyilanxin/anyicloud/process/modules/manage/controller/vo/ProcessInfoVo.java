

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 流程图用户任务信息
 *
 * @author zxh
 * @date 2022-01-03 10:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessInfoVo implements Serializable {
    private static final long serialVersionUID = 2741579373373551412L;

    @Schema(name = "processDefinitionId", title = "流程定义id(与流程定义key必传一个,两个都传认流程定义id),即指定版本")
    private String processDefinitionId;

    @Schema(name = "processDefinitionKey", title = "流程定义key(与流程定义id必传一个,两个都传认流程定义id),运行最新版本")
    private String processDefinitionKey;
}
