

package com.anyilanxin.anyicloud.process.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.rbac.service.IUserService;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.UserDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUser", description = "用户相关")
@RequestMapping(value = "/rbac-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {
    private final IUserService service;

    @Operation(summary = "添加或更新用户", tags = {"v1.0.0"}, description = "添加或更新用户")
    @PostMapping(value = "/insert-or-update")
    public Result<String> saveOrUpdate(@RequestBody @Valid UserVo vo) {
        service.saveOrUpdate(vo);
        return ok("用户操作成功");
    }


    @Operation(summary = "删除或添加组关联关系", tags = {"v1.0.0"}, description = "删除或添加组关联关系")
    @PostMapping(value = "/delete-or-group")
    public Result<String> deleteOrAddGroup(@RequestBody @Valid UserGroupVo vo) {
        service.deleteOrAddGroup(vo);
        return ok("关联关系操作成功");
    }


    @Operation(summary = "删除或添加租户关联关系", tags = {"v1.0.0"}, description = "删除或添加租户关联关系")
    @PostMapping(value = "/delete-or-tenant")
    public Result<String> deleteOrAddTenant(@RequestBody @Valid UserTenantVo vo) {
        service.deleteOrAddTenant(vo);
        return ok("关联关系操作成功");
    }


    @Operation(summary = "删除用户", tags = {"v1.0.0"}, description = "删除用户")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteUser(userId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "全量同步用户信息", tags = {"v1.0.0"}, description = "全量同步用户信息")
    @PostMapping(value = "/all")
    public Result<String> syncUser(@RequestBody @Valid Set<SyncUserVo> voSet) {
        service.syncUser(voSet);
        return ok("用户信息操作成功");
    }


    @Operation(summary = "查询用户详情", tags = {"v1.0.0"}, description = "查询用户详情")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/select/one/{userId}")
    public Result<UserDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.getUser(userId));
    }


    @Operation(summary = "查询用户列表", tags = {"v1.0.0"}, description = "查询用户列表")
    @PostMapping(value = "/select/list")
    public Result<List<UserDto>> getList(@RequestBody UserQueryVo vo) {
        return ok(service.getUserList(vo));
    }


    @Operation(summary = "分页查询用户", tags = {"v1.0.0"}, description = "分页查询用户")
    @PostMapping(value = "/select/page")
    public Result<PageDto<UserDto>> selectPage(@RequestBody UserQueryPageVoCamunda vo) {
        return ok(service.getUserPage(vo));
    }
}
