// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacUserService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateRoleDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserListDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserPageDto;
import indi.zxiaozhou.skillfull.corecommon.annotation.Anonymous;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户表(User)控制层
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:16
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "用户管理")
@RequestMapping(value = "/rbac-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserController extends BaseController {
    private final IRbacUserService service;
    private final IRbacCorrelateRoleService correlateRoleService;


    @Operation(summary = "用户表添加", tags = {"v1.0.0"}, description = "添加用户表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserVo vo) {
        String newPassword = service.save(vo);
        return ok(newPassword, "添加成功,初始密码为:" + newPassword);
    }

    @Operation(summary = "获取关联的角色信息", tags = {"v1.0.0"}, description = "获取关联的角色信息")
    @PostMapping(value = "/select/role-correlate")
    public Result<List<RbacCorrelateRoleDto>> getCorrelateRoleInfos(@RequestBody @Valid RbacCorrelateRoleQueryVo vo) {
        return ok(correlateRoleService.selectListByModel(vo));
    }


    @Operation(summary = "用户授权", tags = {"v1.0.0"}, description = "用户授权")
    @PostMapping(value = "/auth")
    public Result<String> userAuth(@RequestBody @Valid RbacCorrelateRoleVo vo) {
        correlateRoleService.updateRoleCorrelate(vo);
        return ok("授权成功");
    }


    @Operation(summary = "重置用户密码", tags = {"v1.0.0"}, description = "重置用户密码")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/reset-password/{userId}")
    public Result<String> resetPassword(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        String newPassword = service.resetPassword(userId);
        return ok(newPassword, "重置密码成功,新密码:" + newPassword);
    }


    @Operation(summary = "通过用户id修改", tags = {"v1.0.0"}, description = "修改用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @PutMapping(value = "/update/{userId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId,
                                 @RequestBody @Valid RbacUserVo vo) {
        service.updateById(userId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "通过用户id修改用户状态", tags = {"v1.0.0"}, description = "通过用户id修改用户状态")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true),
            @Parameter(description = "类型:2-冻结,1-解冻", name = "type", required = true)
    })
    @GetMapping(value = "/update/user/state")
    public Result<String> updateUserState(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId,
                                          @RequestParam(required = false) @NotNull(message = "操作类型不能为空")
                                          @Min(value = 1, message = "操作类型只能为1、2")
                                          @Max(value = 2, message = "操作类型只能为1、2") Integer type) {
        service.updateUserState(userId, type);
        return ok("用户状态修改成功");
    }


    @Operation(summary = "用户表逻辑删除", tags = {"v1.0.0"}, description = "删除用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteById(userId);
        return ok("删除成功");
    }


    @Operation(summary = "通过用户id查询详情", tags = {"v1.0.0"}, description = "查询用户表详情")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/select/one/{userId}")
    @Anonymous
    public Result<RbacUserDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.getById(userId));
    }


    @Operation(summary = "根据多个用户id查询用户信息", tags = {"v1.0.0"}, description = "根据多个用户id查询用户信息")
    @PostMapping(value = "/select/list")
    @Anonymous
    public Result<List<RbacUserListDto>> getListByIds(@RequestBody @NotNullSize(message = "用户userIds不能为空") List<String> userIds) {
        return ok(service.getListByIds(userIds));
    }


    @Operation(summary = "更具真实姓名模糊查询用户信息", tags = {"v1.0.0"}, description = "更具真实姓名模糊查询用户信息")
    @GetMapping(value = "/select/list/real-name")
    @Anonymous
    public Result<List<RbacUserListDto>> getUserByRealName(@RequestParam @NotBlank(message = "用户真实姓名不能为空") String realName) {
        return ok(service.getUserByRealName(realName));
    }


    @Operation(summary = "用户表分页查询", tags = {"v1.0.0"}, description = "分页查询用户表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserPageDto>> selectPage(@RequestBody RbacUserPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}