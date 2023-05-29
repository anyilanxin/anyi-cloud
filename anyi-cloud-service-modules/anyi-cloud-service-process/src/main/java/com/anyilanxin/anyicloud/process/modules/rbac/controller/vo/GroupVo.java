

package com.anyilanxin.anyicloud.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

/**
 * 用户组信息
 *
 * @author zxh
 * @date 2021-11-05 17:49
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class GroupVo implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "groupId", title = "用户组id", required = true)
    @NotBlank(message = "用户组id不能为空")
    protected String groupId;

    @Schema(name = "name", title = "用户组名称", required = true)
    @NotBlank(message = "用户组名称不能为空")
    protected String name;

    @Schema(name = "code", title = "用户组编码", required = true)
    @NotBlank(message = "用户组编码不能为空")
    protected String code;

    public Group getCamundaGroup() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupId);
        groupEntity.setName(name);
        groupEntity.setType(code);
        return groupEntity;
    }
}
