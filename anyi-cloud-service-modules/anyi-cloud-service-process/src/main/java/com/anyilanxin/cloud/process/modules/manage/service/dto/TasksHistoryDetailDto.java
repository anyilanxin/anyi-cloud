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

package com.anyilanxin.cloud.process.modules.manage.service.dto;

import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.cloud.systemadapter.model.SimpleUserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务详情
 *
 * @author zxh
 * @date 2020-10-19 19:40
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TasksHistoryDetailDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "activityId", title = "任务活动id")
    protected String activityId;

    @Schema(name = "activityName", title = "任务活动名称")
    protected String activityName;

    @Schema(name = "activityType", title = "任务活动类型,org.camunda.bpm.engine.ActivityTypes")
    protected String activityType;

    @Schema(name = "approvalOpinions", title = "审批意见")
    private String approvalOpinions;

    @Schema(name = "activityInstanceId", title = "任务活动实例id")
    protected String activityInstanceId;

    @Schema(name = "activityInstanceState", title = "任务活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
    protected Integer activityInstanceState;

    @Schema(name = "taskId", title = "任务id")
    protected String taskId;

    @Schema(name = "taskStartTime", title = "任务开始时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskStartTime;

    @Schema(name = "taskEndTime", title = "任务结束时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskEndTime;

    @Schema(name = "durationInMillis", title = "消耗时间(s)")
    private Long durationInMillis;

    @Schema(name = "durationStr", title = "消耗时间自带单位")
    private String durationStr;

    @Schema(name = "userInfos", title = "流程候选用户信息")
    private List<SimpleUserModel> userInfos;

    @Schema(name = "groupInfos", title = "流程候选用户组信息")
    private List<RoleInfo> groupInfos;

    @Schema(name = "taskAssignee", title = "审批人", hidden = true)
    @JsonIgnore
    protected String taskAssignee;

    @Schema(name = "taskAssigneeInfo", title = "办理人信息")
    private SimpleUserModel taskAssigneeInfo;

    @Schema(name = "sequenceCounter", title = "序列计数器")
    protected Long sequenceCounter;

    @Schema(name = "taskRemovalTime", title = "删除时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskRemovalTime;
}
