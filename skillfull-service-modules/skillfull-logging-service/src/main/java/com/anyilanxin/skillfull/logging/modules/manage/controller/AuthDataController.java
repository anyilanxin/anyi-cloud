// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.skillfull.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志(AuthData)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 21:53:02
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "AuthData", description = "授权日志")
@RequestMapping(value = "/auth-data", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthDataController extends BaseController {
    private final IAuthDataService service;


    @Operation(summary = "登录日志逻辑删除", tags = {"v1.0.0"}, description = "删除登录日志")
    @Parameter(in = ParameterIn.PATH, description = "授权日志id", name = "authLogId", required = true)
    @DeleteMapping(value = "/delete-one/{authLogId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "授权日志id不能为空") String authLogId) {
        service.deleteById(authLogId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "登录日志逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除登录日志")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除授权日志id不能为空") List<String> authLogIds) {
        service.deleteBatch(authLogIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过授权日志id查询详情", tags = {"v1.0.0"}, description = "查询登录日志详情")
    @Parameter(in = ParameterIn.PATH, description = "授权日志id", name = "authLogId", required = true)
    @GetMapping(value = "/select/one/{authLogId}")
    public Result<AuthDataDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "授权日志id不能为空") String authLogId) {
        return ok(service.getById(authLogId));
    }


    @Operation(summary = "登录日志分页查询", tags = {"v1.0.0"}, description = "分页查询登录日志")
    @PostMapping(value = "/select/page")
    public Result<PageDto<AuthDataPageDto>> selectPage(@RequestBody AuthDataPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
