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

package com.anyilanxin.anyicloud.process.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.WaitTaskPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.AllTaskPageQuery;
import com.anyilanxin.anyicloud.process.modules.manage.service.ITaskManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "ProcessManageTask", description = "流程任务管理(管理端)")
@RequestMapping(value = "/manage-task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskManageController extends AnYiBaseController {
    private final ITaskManageService service;

    @Operation(summary = "添加候选人", tags = {"v1.0.0"}, description = "添加候选人")
    @GetMapping(value = "/task/add-candidate/user")
    @Parameters({@Parameter(description = "任务id", name = "taskId", required = true), @Parameter(description = "用户id", name = "userId", required = true)})
    public AnYiResult<String> addCandidateUser(@RequestParam(required = false) @NotBlank(message = "任务id不能为空") String taskId, @RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId) {
        service.addCandidateUser(taskId, userId);
        return ok("添加候选人成功");
    }


    @Operation(summary = "删除候选人", tags = {"v1.0.0"}, description = "删除候选人")
    @GetMapping(value = "/task/delete-candidate/user")
    @Parameters({@Parameter(description = "任务id", name = "taskId", required = true), @Parameter(description = "用户id", name = "userId", required = true)})
    public AnYiResult<String> deleteCandidateUser(@RequestParam(required = false) @NotBlank(message = "任务id不能为空") String taskId, @RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId) {
        service.deleteCandidateUser(taskId, userId);
        return ok("删除候选人成功");
    }


    @Operation(summary = "添加候选组", tags = {"v1.0.0"}, description = "添加候选组")
    @GetMapping(value = "/task/add-candidate/group")
    @Parameters({@Parameter(description = "任务id", name = "taskId", required = true), @Parameter(description = "用户组id", name = "groupId", required = true)})
    public AnYiResult<String> addCandidateGroup(@RequestParam(required = false) @NotBlank(message = "任务id不能为空") String taskId, @RequestParam(required = false) @NotBlank(message = "用户组id不能为空") String groupId) {
        service.addCandidateGroup(taskId, groupId);
        return ok("添加候选组成功");
    }


    @Operation(summary = "删除候选组", tags = {"v1.0.0"}, description = "删除候选组")
    @GetMapping(value = "/task/delete-candidate/group")
    @Parameters({@Parameter(description = "任务id", name = "taskId", required = true), @Parameter(description = "用户组id", name = "groupId", required = true)})
    public AnYiResult<String> deleteCandidateGroup(@RequestParam(required = false) @NotBlank(message = "任务id不能为空") String taskId, @RequestParam(required = false) @NotBlank(message = "用户组id不能为空") String groupId) {
        service.deleteCandidateGroup(taskId, groupId);
        return ok("删除候选组成功");
    }


    @Operation(summary = "分页查询所有待办", tags = {"v1.0.0"}, description = "分页查询所有待办")
    @PostMapping(value = "/select/all-tasks/page")
    public AnYiResult<AnYiPageResult<WaitTaskPageDto>> getAllTasks(@RequestBody @Valid AllTaskPageQuery vo) {
        return ok(service.getAllTasks(vo));
    }


    @Operation(summary = "分页查询所有已办", tags = {"v1.0.0"}, description = "分页查询所有已办")
    @PostMapping(value = "/select/all-task/history")
    public AnYiResult<String> getAllTaskHistory() {
        service.getAllTaskHistory();
        return ok("处理成功");
    }
}
