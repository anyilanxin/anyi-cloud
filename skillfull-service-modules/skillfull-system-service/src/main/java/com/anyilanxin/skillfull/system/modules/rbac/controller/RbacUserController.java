package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacEnalbeUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacJoinOrgVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgUserService;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacUserService;
import com.anyilanxin.skillfull.system.modules.rbac.service.ISyncProcessService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserPageDto;
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
 * 用户表(RbacUser)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUser", description = "用户相关")
@RequestMapping(value = "/rbac-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserController extends BaseController {
    private final IRbacUserService service;
    private final ISyncProcessService syncService;
    private final IRbacOrgUserService orgUserService;

    @Operation(summary = "用户表添加", tags = {"v1.0.0"}, description = "添加用户表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过用户id修改", tags = {"v1.0.0"}, description = "修改用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @PutMapping(value = "/update/{userId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId,
                                 @RequestBody @Valid RbacUserVo vo) {
        service.updateById(userId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "通过用户d修改状态", tags = {"v1.0.0"}, description = "通过用户d修改状态")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "用户id", name = "userId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "类型:1-激活,2-冻结", name = "type", required = true)
    })
    @GetMapping(value = "/update-user/state")
    public Result<String> updateState(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId,
                                      @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateState(userId, type);
        return ok(type == 1 ? "激活成功" : "冻结成功");
    }


    @Operation(summary = "重置密码", tags = {"v1.0.0"}, description = "重置密码")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/reset/password/{userId}")
    public Result<String> resetPassword(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.resetPassword(userId), "重置密码成功");
    }


    @Operation(summary = "用户表逻辑删除", tags = {"v1.0.0"}, description = "删除用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteById(userId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "加入机构", tags = {"v1.0.0"}, description = "加入机构")
    @PostMapping(value = "/join-org")
    public Result<String> joinOrg(@RequestBody @Valid RbacJoinOrgVo vo) {
        orgUserService.joinOrg(vo);
        return ok("加入机构成功");
    }


    @Operation(summary = "移除机构", tags = {"v1.0.0"}, description = "移除机构")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true),
            @Parameter(in = ParameterIn.PATH, description = "机构id", name = "orgId", required = true)
    })

    @GetMapping(value = "/remove-org")
    public Result<String> removeOrg(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId,
                                    @RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId) {
        orgUserService.removeOrg(userId, orgId);
        return ok("移除机构成功");
    }


    @Operation(summary = "用户表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除用户id不能为空") List<String> userIds) {
        service.deleteBatch(userIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过用户id查询详情", tags = {"v1.0.0"}, description = "查询用户表详情")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "用户id", name = "userId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId")
    })
    @GetMapping(value = "/select/one")
    public Result<RbacUserDto> getById(@RequestParam(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId,
                                       @RequestParam(required = false) String orgId) {
        return ok(service.getById(userId, orgId));
    }


    @Operation(summary = "用户表分页查询", tags = {"v1.0.0"}, description = "分页查询用户表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserPageDto>> selectPage(@RequestBody RbacUserPageVo vo) {
        return ok(service.pageByModel(vo));
    }

    @Operation(summary = "分页查询可关联的用户信息", tags = {"v1.0.0"}, description = "分页查询可关联的用户信息")
    @PostMapping(value = "/select/enable-user-page")
    public Result<PageDto<RbacUserPageDto>> selectEnableUserPage(@RequestBody @Valid RbacEnalbeUserPageVo vo) {
        return ok(service.selectEnableUserPage(vo));
    }


//    @Operation(summary = "全量同步流程引擎", tags = {"v1.0.0"}, description = "全量同步流程引擎")
//    @GetMapping(value = "/sync/process")
//    public Result<String> syncProcess() {
//        syncService.syncRoleAll();
//        syncService.syncUserAll();
//        return ok("同步成功");
//    }
}
