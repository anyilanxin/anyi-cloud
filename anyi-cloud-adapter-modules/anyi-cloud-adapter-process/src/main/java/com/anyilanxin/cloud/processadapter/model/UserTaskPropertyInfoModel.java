/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.processadapter.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 用户任务扩展信息,json object
 *
 * @author zxh
 * @date 2021-11-13 20:28
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserTaskPropertyInfoModel implements Serializable {
    @Serial
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
    private boolean anyiApplicantAutoSkip;

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
    private List<String> anyiUserTaskButtons = Collections.emptyList();
}
