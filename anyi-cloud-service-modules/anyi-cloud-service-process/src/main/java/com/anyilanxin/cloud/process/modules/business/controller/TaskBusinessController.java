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

package com.anyilanxin.cloud.process.modules.business.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.coreprocess.model.task.*;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskType;
import com.anyilanxin.cloud.process.modules.business.controller.vo.*;
import com.anyilanxin.cloud.process.modules.business.service.IProcessBusinessService;
import com.anyilanxin.cloud.process.modules.business.service.ITaskBusinessService;
import com.anyilanxin.cloud.process.modules.business.service.dto.EnableDismissDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.HistoryTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.WaitTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.WaitUserTaskDto;
import com.anyilanxin.cloud.processadapter.model.ProcessCallbackResultModel;
import com.anyilanxin.cloud.processadapter.model.WaitUserTaskDetailModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 流程任务管理
 *
 * @author zxh
 * @date 2020-10-14 20:45
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ProcessBusinessTask", description = "流程任务管理(业务端)")
@RequestMapping(value = "/business-task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskBusinessController extends AnYiBaseController {
    private final ITaskBusinessService service;
    private final IProcessBusinessService businessService;

    @Operation(summary = "获取历史审批用户任务", tags = {"v1.0.0"}, description = "获取历史审批用户任务")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @GetMapping(value = "/select/task/history-list/{processInstanceId}")
    public AnYiResult<Set<HistoryUserTaskDto>> getHistoryTaskList(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.getHistoryTaskList(processInstanceId));
    }


    @Operation(summary = "获取未办用户任务", tags = {"v1.0.0"}, description = "获取未办用户任务")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @PostMapping(value = "/select/task/wait-list/{processInstanceId}")
    public AnYiResult<Set<WaitUserTaskDto>> getWaitTaskList(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.getWaitTaskList(processInstanceId));
    }


    @Operation(summary = "查询待审批任务详情", tags = {"v1.0.0"}, description = "查询待审批任务详情")
    @Parameter(in = ParameterIn.PATH, description = "任务id", name = "taskId", required = true)
    @GetMapping(value = "/task/wait-detail/{taskId}")
    public AnYiResult<WaitUserTaskDetailModel> waitTaskDetail(@PathVariable(required = false) @PathNotBlankOrNull(message = "任务id不能为空") String taskId) {
        return ok(service.waitTaskDetail(taskId));
    }


    @Operation(summary = "委托", tags = {"v1.0.0"}, description = "委托")
    @PostMapping(value = "/task/delegate")
    public AnYiResult<String> delegateTask(@RequestBody @Valid TaskDelegateModel vo) {
        service.delegateTask(vo);
        return ok("委托成功");
    }


    @Operation(summary = "签收", tags = {"v1.0.0"}, description = "签收")
    @GetMapping(value = "/task/claim/{taskId}")
    @Parameter(in = ParameterIn.PATH, description = "任务id", name = "taskId", required = true)
    public AnYiResult<String> taskClaim(@PathVariable(required = false) @PathNotBlankOrNull(message = "任务id不能为空") String taskId) {
        service.taskClaim(taskId);
        return ok("签收成功");
    }


    @Operation(summary = "归还", tags = {"v1.0.0"}, description = "归还")
    @GetMapping(value = "/task/un-claim/{taskId}")
    @Parameter(in = ParameterIn.PATH, description = "任务id", name = "taskId", required = true)
    public AnYiResult<String> taskUnClaim(@PathVariable(required = false) @PathNotBlankOrNull(message = "任务id不能为空") String taskId) {
        service.taskUnClaim(taskId);
        return ok("任务归还成功");
    }


    @Operation(summary = "撤回", tags = {"v1.0.0"}, description = "撤回")
    @GetMapping(value = "/task/cancel/{taskId}")
    @Parameter(in = ParameterIn.PATH, description = "任务id", name = "taskId", required = true)
    public AnYiResult<String> cancelComplete(@PathVariable(required = false) @PathNotBlankOrNull(message = "任务id不能为空") String taskId) {
        service.cancelComplete(taskId);
        return ok("撤回成功");
    }


    @Operation(summary = "审批", tags = {"v1.0.0"}, description = "审批")
    @PostMapping(value = "/task/complete")
    public AnYiResult<String> completeTask(@RequestBody @Valid TaskAgreeModel vo) {
        service.completeTask(vo);
        return ok("审批完成");
    }


    @Operation(summary = "驳回", tags = {"v1.0.0"}, description = "驳回")
    @PostMapping(value = "/task/dismiss")
    public AnYiResult<String> dismissTask(@RequestBody @Valid TaskDismissModel vo) {
        service.dismissTask(vo);
        return ok("驳回成功");
    }


    @Operation(summary = "转办", description = "转办", tags = {"v1.0.0"})
    @PostMapping(value = "/task/turn-over")
    public AnYiResult<String> turnOverTask(@RequestBody @Valid TaskTurnOverModel vo) {
        service.turnOverTask(vo);
        return ok("转交成功");
    }


    @Operation(summary = "预提交获取下个用户任务节点信息", tags = {"v1.0.0"}, description = "预提交获取下个用户任务节点信息")
    @PostMapping(value = "/task/complete-advance/get-task-info")
    public AnYiResult<ProcessCallbackResultModel> completeAdvanceGetTaskInfo(@RequestBody @Valid CompleteAdvanceTaskModel vo) {
        return ok(service.completeAdvanceGetTaskInfo(vo));
    }


    @Operation(summary = "审批并返回审批节点信息", tags = {"v1.0.0"}, description = "审批并返回审批节点信息")
    @PostMapping(value = "/task/complete-callback")
    public AnYiResult<ProcessCallbackResultModel> completeTaskAndCallback(@RequestBody @Valid TaskAgreeModel vo) {
        return ok(service.completeTask(vo), "审批完成");
    }


    @Operation(summary = "分页查询一般待处理任务", tags = {"v1.0.0"}, description = "分页查询一般待处理任务")
    @PostMapping(value = "/select/general-task/page")
    public AnYiResult<AnYiPageResult<WaitTaskPageDto>> selectGeneralTaskPage(@RequestBody @Valid TaskWaitPageVo vo) {
        return ok(service.selectTaskPage(vo, TaskType.APPROVAL_TASK));
    }


    @Operation(summary = "分页查询审批已处理任务", tags = {"v1.0.0"}, description = "分页查询审批已处理任务")
    @PostMapping(value = "/select/history-task/page")
    public AnYiResult<AnYiPageResult<HistoryTaskPageDto>> selectHistoryTaskPage(@RequestBody @Valid TaskHistoryPageVo vo) {
        return ok(service.selectHistoryTaskPage(vo, 0));
    }


    @Operation(summary = "分页查询抄送任务", tags = {"v1.0.0"}, description = "分页查询抄送任务")
    @PostMapping(value = "/select/copy-task/page")
    public AnYiResult<AnYiPageResult<HistoryTaskPageDto>> selectCopyTaskPage(@RequestBody @Valid TaskHistoryPageVo vo) {
        return ok(service.selectHistoryTaskPage(vo, 1));
    }


    @Operation(summary = "分页查询委托任务", tags = {"v1.0.0"}, description = "分页查询委托任务")
    @PostMapping(value = "/select/delegate-task/page")
    public AnYiResult<AnYiPageResult<WaitTaskPageDto>> selectDelegateTaskPage(@RequestBody @Valid TaskWaitPageVo vo) {
        return ok(service.selectTaskPage(vo, TaskType.DELEGATE_TASK));
    }


    @Operation(summary = "分页查询催办任务", tags = {"v1.0.0"}, description = "分页查询催办任务")
    @PostMapping(value = "/select/urgent-task/page")
    public AnYiResult<AnYiPageResult<WaitTaskPageDto>> selectUrgentTaskPage(@RequestBody @Valid TaskWaitPageVo vo) {
        return ok(service.selectTaskPage(vo, TaskType.URGENT_TASK));
    }


    @Operation(summary = "查询可打回的节点或连线", tags = {"v1.0.0"}, description = "查询可打回的节点或连线")
    @PostMapping(value = "/select/list/enable-dismiss")
    public AnYiResult<List<EnableDismissDto>> selectEnableDismiss(@RequestBody @Valid EnableDismissVo vo) {
        return ok(businessService.selectEnableDismiss(vo));
    }


    @Operation(summary = "会签加签", tags = {"会签加签减签"}, description = "会签加签")
    @PostMapping(value = "/handle/multi-instance/task-add")
    public AnYiResult<String> multiInstanceAddTask(@RequestBody @Valid MultiInstanceHandleAddTaskVo vo) {
        service.multiInstanceAddTask(vo);
        return ok("会签操作成功");
    }


    @Operation(summary = "会签减签", tags = {"会签加签减签"}, description = "会签减签")
    @PostMapping(value = "/handle/multi-instance/task-subtract")
    public AnYiResult<String> multiInstanceSubtractTask(@RequestBody @Valid @Size(min = 1, message = "待减签任务不能为空") List<String> multiInstanceTaskIds) {
        service.multiInstanceSubtractTask(multiInstanceTaskIds);
        return ok("会签操作成功");
    }


    @Operation(summary = "分页查询转办任务", tags = {"v1.0.0"}, description = "分页查询转办任务")
    @PostMapping(value = "/select/turn-over-task/page")
    public AnYiResult<AnYiPageResult<WaitTaskPageDto>> selectTurnOverTaskPage(@RequestBody @Valid TaskWaitPageVo vo) {
        return ok(service.selectTaskPage(vo, TaskType.TURN_OVER_TASK));
    }
}
