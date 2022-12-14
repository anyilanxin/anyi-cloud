// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.skillfull.process.modules.rbac.service.ITenantService;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.TenantDto;
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
import java.util.Set;

/**
 * 租户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacTenant", description = "租户相关")
@RequestMapping(value = "/rbac-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantController extends BaseController {
    private final ITenantService service;

    @Operation(summary = "添加或更新租户", tags = {"v1.0.0"}, description = "添加或更新租户")
    @PostMapping(value = "/insert-or-update")
    public Result<String> saveOrUpdate(@RequestBody @Valid TenantVo vo) {
        service.saveOrUpdate(vo);
        return ok("租户操作成功");
    }


    @Operation(summary = "删除租户", tags = {"v1.0.0"}, description = "删除租户")
    @Parameter(in = ParameterIn.PATH, description = "租户id", name = "tenantId", required = true)
    @DeleteMapping(value = "/delete-one/{tenantId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId) {
        service.deleteTenant(tenantId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "全量同步租户信息", tags = {"v1.0.0"}, description = "全量同步租户信息")
    @PostMapping(value = "/all")
    public Result<String> syncTenant(@RequestBody @Valid Set<TenantVo> voSet) {
        service.syncTenant(voSet);
        return ok("租户信息操作成功");
    }


    @Operation(summary = "查询租户详情", tags = {"v1.0.0"}, description = "查询租户详情")
    @Parameter(in = ParameterIn.PATH, description = "租户id", name = "tenantId", required = true)
    @GetMapping(value = "/select/one/{tenantId}")
    public Result<TenantDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId) {
        return ok(service.getTenant(tenantId));
    }


    @Operation(summary = "查询租户列表", tags = {"v1.0.0"}, description = "查询租户列表")
    @PostMapping(value = "/select/list")
    public Result<List<TenantDto>> getList(@RequestBody TenantQueryVo vo) {
        return ok(service.getTenantList(vo));
    }


    @Operation(summary = "分页查询租户", tags = {"v1.0.0"}, description = "分页查询租户")
    @PostMapping(value = "/select/page")
    public Result<PageDto<TenantDto>> selectPage(@RequestBody TenantQueryPageVoCamunda vo) {
        return ok(service.getTenantPage(vo));
    }
}
