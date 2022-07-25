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
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacSystemService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemPageDto;
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
 * 系统(RbacSystem)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacSystem", description = "系统Api接口相关")
@RequestMapping(value = "/rbac-system", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacSystemController extends BaseController {
    private final IRbacSystemService service;

    @Operation(summary = "系统添加", tags = {"v1.0.0"}, description = "添加系统")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacSystemVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过系统id修改", tags = {"v1.0.0"}, description = "修改系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @PutMapping(value = "/update/{systemId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId,
                                 @RequestBody @Valid RbacSystemVo vo) {
        service.updateById(systemId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统逻辑删除", tags = {"v1.0.0"}, description = "删除系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @DeleteMapping(value = "/delete-one/{systemId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        service.deleteById(systemId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过系统id查询详情", tags = {"v1.0.0"}, description = "查询系统详情")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @GetMapping(value = "/select/one/{systemId}")
    public Result<RbacSystemDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        return ok(service.getById(systemId));
    }


    @Operation(summary = "查询有效的系统信息", tags = {"v1.0.0"}, description = "查询有效的系统信息")
    @GetMapping(value = "/select/list")
    public Result<List<RbacSystemDto>> getList() {
        return ok(service.selectList());
    }


    @Operation(summary = "系统分页查询", tags = {"v1.0.0"}, description = "分页查询系统")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacSystemPageDto>> selectPage(@RequestBody RbacSystemPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
