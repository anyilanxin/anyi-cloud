// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacClientDetailsService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权客户端信息(RbacClientDetails)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacClientDetails", description = "授权客户端相关")
@RequestMapping(value = "/rbac-client", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacClientDetailsController extends BaseController {
    private final IRbacClientDetailsService service;

    @Operation(summary = "授权客户端信息添加", tags = {"v1.0.0"}, description = "添加授权客户端信息")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacClientDetailsVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过客户端信息id修改", tags = {"v1.0.0"}, description = "修改授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @PutMapping(value = "/update/{clientDetailId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId,
                                 @RequestBody @Valid RbacClientDetailsVo vo) {
        service.updateById(clientDetailId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "更新或添加权限", tags = {"v1.0.0"}, description = "更新或添加权限")
    @Parameter(in = ParameterIn.PATH, description = "客户端id", name = "clientDetailId", required = true)
    @PutMapping(value = "/update-auth/{clientDetailId}")
    public Result<String> updateAuth(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端id不能为空") String clientDetailId,
                                     @RequestBody @Valid RbacClientAuthVo vo) {
        service.updateAuth(clientDetailId, vo);
        return ok("设置权限成功");
    }


    @Operation(summary = "授权客户端信息逻辑删除", tags = {"v1.0.0"}, description = "删除授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @DeleteMapping(value = "/delete-one/{clientDetailId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId) {
        service.deleteById(clientDetailId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过客户端id修改状态", tags = {"v1.0.0"}, description = "通过客户端id修改状态")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "客户端信息id", name = "clientDetailId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用,1-启用，2-锁定", name = "type", required = true)
    })
    @GetMapping(value = "/update/client/state")
    public Result<String> updateState(@RequestParam(required = false) @NotBlank(message = "客户端信息id不能为空") String clientDetailId,
                                      @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateState(clientDetailId, type);
        return ok("修改状态成功");
    }


    @Operation(summary = "授权客户端信息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除授权客户端信息")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除客户端信息id不能为空") List<String> clientDetailIds) {
        service.deleteBatch(clientDetailIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过客户端信息id查询详情", tags = {"v1.0.0"}, description = "查询授权客户端信息详情")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @GetMapping(value = "/select/one/{clientDetailId}")
    public Result<RbacClientDetailsDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId) {
        return ok(service.getById(clientDetailId));
    }


    @Operation(summary = "授权客户端信息分页查询", tags = {"v1.0.0"}, description = "分页查询授权客户端信息")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacClientDetailsPageDto>> selectPage(@RequestBody RbacClientDetailsPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
