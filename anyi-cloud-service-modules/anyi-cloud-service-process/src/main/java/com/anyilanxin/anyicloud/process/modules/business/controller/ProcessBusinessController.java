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

package com.anyilanxin.anyicloud.process.modules.business.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.coreprocess.model.AnYiBpmnProcessInstanceModel;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.CancelProcessVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.ProcessQueryPageVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.ProcessUrgentVo;
import com.anyilanxin.anyicloud.process.modules.business.service.IProcessBusinessService;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.InstanceApplyInfoDto;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.InstanceUserTaskDto;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.ProcessPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessTaskInfoDto;
import com.anyilanxin.anyicloud.processadapter.model.FormReSubmitProcessModel;
import com.anyilanxin.anyicloud.processadapter.model.FormSubmitProcessModel;
import com.anyilanxin.anyicloud.processadapter.model.MsgSubmitProcessModel;
import com.anyilanxin.anyicloud.processadapter.model.ProcessCallbackResultModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 流程实例管理
 *
 * @author zxh
 * @date 2020-10-14 20:45
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ProcessBusinessInstance", description = "流程(业务端)")
@RequestMapping(value = "/business-process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessBusinessController extends AnYiBaseController {
    private final IProcessBusinessService service;

    @Operation(summary = "发起流程", description = "发起流程", tags = {"v1.0.0"})
    @PostMapping(value = "/process/submit")
    public AnYiResult<ProcessCallbackResultModel> submitProcess(@RequestBody @Valid FormSubmitProcessModel vo) {
        return ok(service.submitProcess(vo), "提交流程申请成功");
    }


    @Operation(summary = "发起流程(基于消息事件)", tags = {"v1.0.0"}, description = "发起流程(基于消息事件)")
    @PostMapping(value = "/process/msg-submit")
    public AnYiResult<ProcessCallbackResultModel> submitProcess(@RequestBody @Valid MsgSubmitProcessModel model) {
        return ok(service.submitProcess(model), "提交流程申请成功");
    }


    @Operation(summary = "重新提交申请", description = "重新提交申请", tags = {"v1.0.0"})
    @PostMapping(value = "/process/resubmit")
    public AnYiResult<ProcessCallbackResultModel> reSubmit(@RequestBody @Valid FormReSubmitProcessModel vo) {
        return ok(service.reSubmit(vo), "重新提交成功");
    }


    @Operation(summary = "催办", tags = {"v1.0.0"}, description = "催办")
    @PostMapping(value = "/process/urgent")
    public AnYiResult<String> urgentTask(@RequestBody @Valid ProcessUrgentVo vo) {
        service.urgentTask(vo);
        return ok("催办成功");
    }


    @Operation(summary = "流程作废", tags = {"v1.0.0"}, description = "流程作废")
    @PostMapping(value = "/process/cancel")
    public AnYiResult<String> cancelProcess(@RequestBody @Valid CancelProcessVo vo) {
        service.cancelProcess(vo);
        return ok("流程取消成功");
    }


    @Operation(summary = "删除历史流程", tags = {"v1.0.0"}, description = "删除历史流程")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @DeleteMapping(value = "/delete/history-process-one/{processInstanceId}")
    public AnYiResult<String> deleteHistoryProcess(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        service.deleteHistoryProcess(processInstanceId);
        return ok("删除历史流程成功");
    }


    @Operation(summary = "批量删除历史流程", tags = {"v1.0.0"}, description = "批量删除历史流程")
    @PostMapping(value = "/delete/history-process-batch")
    public AnYiResult<String> deleteHistoryProcessBatch(Set<String> processInstanceIds) {
        service.deleteHistoryProcessBatch(processInstanceIds);
        return ok("批量删除历史流程成功");
    }


    @Operation(summary = "分页查询我的申请", tags = {"v1.0.0"}, description = "分页查询我的申请")
    @PostMapping(value = "/select/process/page")
    public AnYiResult<AnYiPageResult<ProcessPageDto>> selectProcessPage(@RequestBody ProcessQueryPageVo vo) {
        return ok(service.selectProcessPage(vo));
    }


    @Operation(summary = "获取申请基本信息(包含申请表单)", tags = {"v1.0.0"}, description = "获取申请基本信息(包含申请表单)")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @GetMapping(value = "/select/instance/apply-info/{processInstanceId}")
    public AnYiResult<InstanceApplyInfoDto> selectInstanceApplyInfo(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.selectInstanceApplyInfo(processInstanceId));
    }


    @Operation(summary = "获取用户任务(审批意见)", tags = {"v1.0.0"}, description = "获取用户任务(审批意见)")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @GetMapping(value = "/select/instance/user-task/{processInstanceId}")
    public AnYiResult<List<InstanceUserTaskDto>> selectInstanceOptions(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.selectUserTaskInstance(processInstanceId, true));
    }


    @Operation(summary = "获取流程实例信息(用于流程图渲染)", tags = {"v1.0.0"}, description = "获取所有流程实例信息(用于流程图渲染)")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    @GetMapping(value = "/select/instance/act-infos/{processInstanceId}")
    public AnYiResult<AnYiBpmnProcessInstanceModel> queryBpmnProcessInstance(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.queryBpmnProcessInstance(processInstanceId));
    }


    @Operation(summary = "通过流程定义key或者流程定义id获取用户任务信息", tags = {"v1.0.0"}, description = "通过流程定义key或者流程定义id获取用户任务信息")
    @GetMapping(value = "/select/process-list/user-task/by/{processKeyOrId}")
    @Parameter(in = ParameterIn.PATH, description = "流动定义key或流程定义id", name = "processKeyOrId", required = true)
    public AnYiResult<List<ProcessTaskInfoDto>> selectUserTaskByProcessKeyOrId(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程定义key或者id不能为空") String processKeyOrId) {
        return ok(service.selectUserTaskByProcessKeyOrId(processKeyOrId));
    }
}
