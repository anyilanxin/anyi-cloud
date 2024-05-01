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

package com.anyilanxin.cloud.process.modules.manage.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.DeleteProcessInstanceVo;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.MigrationVo;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.ProcessInstanceBatchVo;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.ProcessInstanceHistoryPageQuery;
import com.anyilanxin.cloud.process.modules.manage.service.IInstanceManageService;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceDetailDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceHistoryPageDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceStaticDto;
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
@Tag(name = "ProcessMangeInstance", description = "流程实例管理(管理端)")
@RequestMapping(value = "/manage-instance", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstanceManageController extends AnYiBaseController {
    private final IInstanceManageService service;

    @Operation(summary = "流程实例挂起", tags = {"v1.0.0"}, description = "流程实例挂起")
    @PostMapping(value = "/suspend/process-instance")
    public AnYiResult<String> suspendProcessInstance(@RequestBody @Valid ProcessInstanceBatchVo vo) {
        service.suspendProcessInstance(vo);
        return ok("实例挂起成功");
    }


    @Operation(summary = "流程实例激活", tags = {"v1.0.0"}, description = "流程实例激活")
    @PostMapping(value = "/activate/process-instance")
    public AnYiResult<String> activateProcessInstance(@RequestBody @Valid ProcessInstanceBatchVo vo) {
        service.activateProcessInstance(vo);
        return ok("实例激活成功");
    }


    @Operation(summary = "获取流程实例详情", tags = {"v1.0.0"}, description = "获取流程实例详情")
    @GetMapping(value = "/select/detail/{processInstanceId}")
    @Parameter(in = ParameterIn.PATH, description = "流程实例id", name = "processInstanceId", required = true)
    public AnYiResult<InstanceDetailDto> getInstanceDetail(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程实例id不能为空") String processInstanceId) {
        return ok(service.getInstanceDetail(processInstanceId));
    }


    @Operation(summary = "流程实例删除", tags = {"v1.0.0"}, description = "流程实例删除")
    @PostMapping(value = "/delete/process-instance")
    public AnYiResult<String> deleteProcessInstance(@RequestBody @Valid DeleteProcessInstanceVo vo) {
        service.deleteProcessInstance(vo);
        return ok("删除流程实例成功");
    }


    @Operation(summary = "分页查询流程实例", tags = {"v1.0.0"}, description = "分页查询流程实例")
    @PostMapping(value = "/select/process-instance/page")
    public AnYiResult<AnYiPageResult<InstanceHistoryPageDto>> selectHistoryProcessInstance(@RequestBody @Valid ProcessInstanceHistoryPageQuery vo) {
        return ok(service.selectHistoryProcessInstance(vo));
    }


    @Operation(summary = "流程实例迁移", tags = {"v1.0.0"}, description = "流程实例迁移")
    @PostMapping(value = "/instance/migration")
    public AnYiResult<String> instanceMigration(@RequestBody @Valid MigrationVo vo) {
        String batchJobDefinitionId = service.instanceMigration(vo);
        return ok(vo.isAsync() ? ("流程异步迁移成功,批处理作业id为:" + batchJobDefinitionId) : "流程同步迁移成功");
    }


    @Operation(summary = "流程实例统计", tags = {"v1.0.0"}, description = "流程实例统计")
    @GetMapping(value = "/instance/static")
    public AnYiResult<InstanceStaticDto> instanceStatic() {
        return ok(service.instanceStatic());
    }
}
