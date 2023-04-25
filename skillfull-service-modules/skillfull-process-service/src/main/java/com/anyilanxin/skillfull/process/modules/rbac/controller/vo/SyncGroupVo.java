package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 用户组信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder

@NoArgsConstructor
@Schema
public class SyncGroupVo implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;

    @Schema(name = "groupId", title = "用户组id", required = true)
    @NotBlank(message = "用户组id不能为空")
    private String groupId;

    @Schema(name = "name", title = "用户组名称", required = true)
    @NotBlank(message = "用户组名称不能为空")
    private String name;

    @Schema(name = "code", title = "用户组编码", required = true)
    @NotBlank(message = "用户组编码不能为空")
    private String code;

    public Group getCamundaGroup() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupId);
        groupEntity.setName(name);
        groupEntity.setType(code);
        return groupEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SyncGroupVo)) {
            return false;
        }
        SyncGroupVo that = (SyncGroupVo) o;
        return Objects.equals(getGroupId(), that.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId());
    }
}
