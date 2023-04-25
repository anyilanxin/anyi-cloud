package com.anyilanxin.skillfull.process.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

import java.io.Serializable;
import java.util.Objects;

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
@EqualsAndHashCode
@Schema
public class GroupDto implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "groupId", title = "用户组id")
    protected String groupId;

    @Schema(name = "name", title = "用户组名称")
    protected String name;

    @Schema(name = "code", title = "用户组编码")
    protected String code;

    public GroupDto getGroup(Group group) {
        GroupDto groupModel = null;
        if (Objects.nonNull(group)) {
            GroupEntity groupEntity = (GroupEntity) group;
            groupModel = GroupDto.builder()
                    .groupId(groupEntity.getId())
                    .name(groupEntity.getName())
                    .code(groupEntity.getType())
                    .build();
        }
        return groupModel;
    }
}
