package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacUserAgentService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentPageDto;
import io.swagger.v3.oas.annotations.Hidden;
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
import java.util.List;

/**
 * 用户-代理人表(RbacUserAgent)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "RbacUserAgent", description = "用户-代理人表Api接口相关")
@RequestMapping(value = "/rbacUserAgent", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserAgentController extends BaseController {
    private final IRbacUserAgentService service;

    @Operation(summary = "用户-代理人表添加", tags = {"v1.0.0"}, description = "添加用户-代理人表", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserAgentVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过代理id修改", tags = {"v1.0.0"}, description = "修改用户-代理人表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @PutMapping(value = "/update/{agentId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId,
                                 @RequestBody @Valid RbacUserAgentVo vo) {
        service.updateById(agentId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "用户-代理人表逻辑删除", tags = {"v1.0.0"}, description = "删除用户-代理人表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @DeleteMapping(value = "/delete-one/{agentId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId) {
        service.deleteById(agentId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "用户-代理人表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户-代理人表", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除代理id不能为空") List<String> agentIds) {
        service.deleteBatch(agentIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过代理id查询详情", tags = {"v1.0.0"}, description = "查询用户-代理人表详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @GetMapping(value = "/select/one/{agentId}")
    public Result<RbacUserAgentDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId) {
        return ok(service.getById(agentId));
    }


    @Operation(summary = "通过条件查询用户-代理人表多条数据", tags = {"v1.0.0"}, description = "通过条件查询用户-代理人表", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<RbacUserAgentDto>> selectListByModel(@RequestBody RbacUserAgentQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "用户-代理人表分页查询", tags = {"v1.0.0"}, description = "分页查询用户-代理人表", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserAgentPageDto>> selectPage(@RequestBody RbacUserAgentPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
