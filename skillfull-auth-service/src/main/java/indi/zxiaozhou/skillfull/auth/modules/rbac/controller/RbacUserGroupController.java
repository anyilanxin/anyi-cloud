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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacUserGroupService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupPageDto;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
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
 * 用户组(RbacUserGroup)控制层
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:13:08
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUserGroup", description = "用户组")
@RequestMapping(value = "/rbac-user-group", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserGroupController extends BaseController {
    private final IRbacUserGroupService service;


    @Operation(summary = "用户组添加", tags = {"v1.0.0"}, description = "添加用户组", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserGroupVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过用户组id修改", tags = {"v1.0.0"}, description = "修改用户组", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户组id", name = "userGroupId", required = true)
    @PutMapping(value = "/update/{userGroupId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String userGroupId,
                                 @RequestBody @Valid RbacUserGroupVo vo) {
        service.updateById(userGroupId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "用户组逻辑删除", tags = {"v1.0.0"}, description = "删除用户组", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户组id", name = "userGroupId", required = true)
    @DeleteMapping(value = "/delete-one/{userGroupId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String userGroupId) {
        service.deleteById(userGroupId);
        return ok("删除成功");
    }


    @Operation(summary = "用户组逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户组", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除用户组id不能为空") List<String> userGroupIds) {
        service.deleteBatch(userGroupIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过用户组id查询详情", tags = {"v1.0.0"}, description = "查询用户组详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户组id", name = "userGroupId", required = true)
    @GetMapping(value = "/select/one/{userGroupId}")
    public Result<RbacUserGroupDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String userGroupId) {
        return ok(service.getById(userGroupId));
    }


    @Operation(summary = "通过条件查询用户组多条数据", tags = {"v1.0.0"}, description = "通过条件查询用户组", hidden = true)
    @PostMapping(value = "/select/list")
    public Result<List<RbacUserGroupDto>> getList(@RequestBody RbacUserGroupQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "用户组分页查询", tags = {"v1.0.0"}, description = "分页查询用户组", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserGroupPageDto>> selectPage(@RequestBody RbacUserGroupPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}