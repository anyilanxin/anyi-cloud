package com.anyilanxin.skillfull.process.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.manage.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.manage.service.IDefinitionManageService;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.DeploymentDetailDto;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.ProcessInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 流程定义管理
 *
 * @author zxiaozhou
 * @date 2020-10-14 20:45
 * @since JDK11
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
