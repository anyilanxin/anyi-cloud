// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户任务审批人员信息
 *
 * @author zxiaozhou
 * @date 2021-08-01 18:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class UserTaskNodeApprovePersonDto implements Serializable {
    private static final long serialVersionUID = -224129324744993013L;

    @Schema(name = "name", title = "节点名称")
    private String name;

    @Schema(name = "assignee", title = "审批人")
    private String assignee;

    @Schema(name = "candidateGroups", title = "候选组")
    private String candidateGroups;

    @Schema(name = "candidateUsers", title = "候选人")
    private String candidateUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTaskNodeApprovePersonDto)) {
            return false;
        }
        UserTaskNodeApprovePersonDto personDto = (UserTaskNodeApprovePersonDto) o;

        return Objects.equals(personDto.assignee, getAssignee()) &&
                Objects.equals(personDto.candidateGroups, getCandidateGroups()) &&
                Objects.equals(personDto.candidateUsers, getCandidateUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssignee(), getCandidateGroups(), getCandidateUsers());
    }
}
