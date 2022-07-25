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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacUserIdentityService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityPageDto;
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
 * 实名信息表(RbacUserIdentity)控制层
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
@Tag(name = "RbacUserIdentity", description = "实名信息表Api接口相关")
@RequestMapping(value = "/rbac-user-identity", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserIdentityController extends BaseController {
    private final IRbacUserIdentityService service;

    @Operation(summary = "实名信息表添加", tags = {"v1.0.0"}, description = "添加实名信息表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserIdentityVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "实名信息审核", tags = {"v1.0.0"}, description = "实名信息审核")
    @Parameter(in = ParameterIn.PATH, description = "实名信息id", name = "identityId", required = true)
    @PutMapping(value = "/audit/{identityId}")
    public Result<String> audit(@PathVariable(required = false) @PathNotBlankOrNull(message = "实名信息id不能为空") String identityId,
                                @RequestBody @Valid RbacUserIdentityVo vo) {
        service.audit(identityId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "实名信息表分页查询", tags = {"v1.0.0"}, description = "分页查询实名信息表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserIdentityPageDto>> selectPage(@RequestBody RbacUserIdentityPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
