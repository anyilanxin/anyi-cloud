// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 用户任务扩展信息,json object
 *
 * @author zxiaozhou
 * @date 2021-11-13 20:28
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserTaskPropertyInfoModel implements Serializable {
    private static final long serialVersionUID = -4292185987202275819L;

    /**
     * 审批用户id
     */
    private String assignee;

    /**
     * 审批用户姓名
     */
    private String skillFullAssignee;

    /**
     * 审批用户扩展信息(人员所属机构(如果类型为系统),表达式id(如果类型为系统表达式))
     */
    private String skillFullAssigneeExtendId;
    /**
     * 是否发起人节点
     */
    private boolean skillFullApplicant;

    /**
     * 是否自动跳过(发起人节点时存在)
     */
    private boolean skillfullApplicantAutoSkip;

    /**
     * 审批人类型:1-系统，2-系统表达式，3-自动以表达式
     */
    private Integer skillFullAssigneeType;

    /**
     * 候选审批角色编码
     */
    private String candidateGroups;

    /**
     * 候选审批角色名称
     */
    private String skillFullCandidateGroups;


    /**
     * 候选审批角色扩展信息(角色id(如果类型为系统),表达式id(如果类型为系统表达式))
     */
    private String skillFullCandidateGroupsExtendId;

    /**
     * 候选审批角色类型:1-系统，2-系统表达式，3-自动以表达式
     */
    private Integer skillFullCandidateGroupsType;

    /**
     * 可操作机构id(数据权限)
     */
    private String skillFullCandidateOrg;

    /**
     * 可操作机构名称(数据权限)
     */
    private String skillFullCandidateOrgName;
    /**
     * 可操作机构类型:1-系统，2-系统表达式，3-自定义表达式
     */
    private Integer skillFullCandidateOrgType;
    /**
     * 可操作区域编码(数据权限)
     */
    private String skillFullCandidateArea;
    /**
     * 可操作区域名称(数据权限)
     */
    private String skillFullCandidateAreaName;

    /**
     * 可操作区域类型:1-系统，2-系统表达式，3-自定义表达式
     */
    private Integer skillFullCandidateAreaType;

    /**
     * 可操作按钮id
     */
    private String skillFullCandidateButtonsExtendId;

    /**
     * 可操作按钮名称
     */
    private String skillFullCandidateButtonsName;

    /**
     * 候选人id
     */
    private String candidateUsers;

    /**
     * 候选审批用户名称
     */
    private String skillFullCandidateUsers;

    /**
     * 候选审批用户扩展信息(人员所属机构(如果类型为系统),表达式id(如果类型为系统表达式))
     */
    private String skillFullCandidateUsersExtendId;

    /**
     * 候选审批用户类型:1-系统，2-系统表达式，3-自动以表达式
     */
    private Integer skillFullCandidateUsersType;

    /**
     * 审批可操作按钮
     */
    @Builder.Default
    private List<String> skillfullUserTaskButtons = Collections.emptyList();
}
