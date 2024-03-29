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
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.manage.service.IDefinitionManageService;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionInfoDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionInfoPageDto;
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
public class DefinitionManageController extends AnYiBaseController {
    private final IDefinitionManageService service;

    @Operation(summary = "分页查询流程部署信息", tags = {"v1.0.0"}, description = "分页查询流程部署信息")
    @PostMapping(value = "/select/page/definition/page")
    public AnYiResult<AnYiPageResult<ProcessDefinitionInfoPageDto>> selectPageDefinition(@RequestBody @Valid ProcessDefinitionPageVo pageVo) {
        return ok(service.selectPageDefinition(pageVo));
    }


    @Operation(summary = "创建部署", tags = {"v1.0.0"}, description = "创建部署")
    @PostMapping(value = "/create/deployment")
    public AnYiResult<String> createDeployment(@RequestBody @Valid DeploymentVo vo) {
        service.createDeployment(vo);
        return ok("部署成功");
    }


    @Operation(summary = "查询流动定义", tags = {"v1.0.0"}, description = "查询流动定义")
    @GetMapping(value = "/select/process-definition/{processDefinitionKey}")
    public AnYiResult<ProcessDefinitionInfoDto> selectProcessById(@PathVariable @PathNotBlankOrNull(message = "流程定义key不能为空") String processDefinitionKey) {
        return ok(service.selectProcessById(processDefinitionKey));
    }


    @Operation(summary = "草稿流程部署", tags = {"ProcessManageDefinition", "v1.0.0"}, description = "草稿流程部署")
    @Parameter(in = ParameterIn.PATH, description = "草稿id", name = "draftId", required = true)
    @GetMapping(value = "/create/deployment/draft/{draftId}")
    public AnYiResult<String> draftProcessDeployment(@PathVariable @PathNotBlankOrNull(message = "草稿id不能为空") String draftId) {
        service.createDeployment(DeploymentVo.builder().draftId(draftId).build());
        return ok("草稿流程部署成功");
    }


    @Operation(summary = "历史再次部署", tags = {"v1.0.0"}, description = "历史再次部署")
    @PostMapping(value = "/history/deployment")
    public AnYiResult<String> historyDeployment(@RequestBody @Valid DeploymentHistoryVo vo) {
        service.historyDeployment(vo);
        return ok("部署成功");
    }


    @Operation(summary = "流程定义状态修改", tags = {"v1.0.0"}, description = "流程定义状态修改")
    @PostMapping(value = "/update/process-definition/state")
    public AnYiResult<String> processDefinitionUpdateState(@RequestBody @Valid UpdateProcessDefinitionStateVo vo) {
        service.processDefinitionUpdateState(vo);
        return ok("流程定义" + (vo.getState() ? "激活" : "挂起") + "成功");
    }


    @Operation(summary = "删除流程定义", tags = {"v1.0.0"}, description = "删除流程定义")
    @PostMapping(value = "/delete/process-definition")
    public AnYiResult<String> deleteProcessDefinition(@RequestBody @Valid DeleteProcessDefinitionVo vo) {
        service.deleteProcessDefinition(vo);
        return ok("删除流程定义成功");
    }
}
