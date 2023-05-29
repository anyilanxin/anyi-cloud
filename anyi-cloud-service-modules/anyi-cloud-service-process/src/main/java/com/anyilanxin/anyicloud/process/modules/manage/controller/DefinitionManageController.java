/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.process.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.manage.service.IDefinitionManageService;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.DeploymentDetailDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessInfoDto;
import com.anyilanxin.skillfull.process.modules.manage.controller.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义管理
 *
 * @author zxh
 * @date 2020-10-14 20:45
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ProcessManageDefinition", description = "流程定义部署管理(管理端)")
@RequestMapping(value = "/manage-definition", produces = MediaType.APPLICATION_JSON_VALUE)
public class DefinitionManageController extends BaseController {
    private final IDefinitionManageService service;

    @Operation(summary = "分页查询流程部署信息", tags = {"v1.0.0"}, description = "分页查询流程部署信息")
    @PostMapping(value = "/select/page/definition/page")
    public Result<PageDto<ProcessDefinitionPageDto>> selectPageDefinition(@RequestBody @Valid ProcessDefinitionPageVo pageVo) {
        return ok(service.selectPageDefinition(pageVo));
    }


    @Operation(summary = "创建部署", tags = {"v1.0.0"}, description = "创建部署")
    @PostMapping(value = "/create/deployment")
    public Result<String> createDeployment(@RequestBody @Valid DeploymentVo vo) {
        service.createDeployment(vo);
        return ok("部署成功");
    }


    @Operation(summary = "历史再次部署", tags = {"v1.0.0"}, description = "历史再次部署")
    @PostMapping(value = "/history/deployment")
    public Result<String> historyDeployment(@RequestBody @Valid DeploymentHistoryVo vo) {
        service.historyDeployment(vo);
        return ok("部署成功");
    }


    @Operation(summary = "删除部署", tags = {"v1.0.0"}, description = "删除部署")
    @Parameter(description = "部署id", name = "deploymentId", required = true)
    @DeleteMapping(value = "/delete/deployment/by-id/{deploymentId}")
    public Result<String> deleteDeploymentById(@PathVariable @PathNotBlankOrNull(message = "部署id不能为空") String deploymentId) {
        service.deleteDeployment(deploymentId);
        return ok("删除流程部署成功");
    }


    @Operation(summary = "获取流程详细信息", tags = {"v1.0.0"}, description = "获取流程详细信息")
    @PostMapping(value = "/select/process-info")
    public Result<ProcessInfoDto> getProcessInfo(@RequestBody @Valid ProcessInfoVo vo) {
        return ok(service.getProcessInfo(vo));
    }


    @Operation(summary = "查询部署资源信息", tags = {"v1.0.0"}, description = "查询部署资源信息")
    @GetMapping(value = "/select/deployment/resource/{processKeywordId}")
    @Parameter(in = ParameterIn.PATH, description = "流程定义id或流程定义key", name = "processKeywordId", required = true)
    public Result<DeploymentDetailDto> getDeploymentDetail(@PathVariable(required = false) @PathNotBlankOrNull(message = "流程定义id或流程定义key不能为空") String processKeywordId) {
        return ok(service.getDeploymentDetail(processKeywordId));
    }


    @Operation(summary = "流程定义状态修改", tags = {"v1.0.0"}, description = "流程定义状态修改")
    @PostMapping(value = "/update/process-definition/state")
    public Result<String> processDefinitionUpdateState(@RequestBody @Valid UpdateProcessDefinitionStateVo vo) {
        service.processDefinitionUpdateState(vo);
        return ok("流程定义" + (vo.getState() ? "激活" : "挂起") + "成功");
    }


    @Operation(summary = "删除流程定义", tags = {"v1.0.0"}, description = "删除流程定义")
    @PostMapping(value = "/delete/process-definition")
    public Result<String> deleteProcessDefinition(@RequestBody @Valid DeleteProcessDefinitionVo vo) {
        service.deleteProcessDefinition(vo);
        return ok("删除流程定义成功");
    }
}
