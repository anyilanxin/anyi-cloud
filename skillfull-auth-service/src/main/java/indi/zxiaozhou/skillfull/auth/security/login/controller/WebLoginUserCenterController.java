// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.controller;

import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.*;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWebLoginUserCenterService;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.UserInfoDto;
import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteTreeModel;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import indi.zxiaozhou.skillfull.coremvc.utils.ContextHolderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2020-09-29 17:29
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "WebUserCenter", description = "web端用户中心")
@RequestMapping(value = "/web/user-center", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebLoginUserCenterController extends BaseController {
    private final IWebLoginUserCenterService service;


    @Operation(summary = "获取用户信息", tags = {"v1.0.0"}, description = "获取用户信息")
    @GetMapping(value = "/user-info")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    public Result<UserInfoDto> getUserInfo() {
        return ok(service.getUserInfo());
    }


    @Operation(summary = "获取用户路由菜单信息(树形)", tags = {"v1.0.0"}, description = "获取用户路由菜单信息(树形)")
    @GetMapping(value = "/menu-info/tree")
    @Parameters({
            @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes"),
            @Parameter(description = "机构id,不传查询所有机构的", name = "orgId")
    })
    public Result<List<LoginRouteTreeModel>> getRouterTreeInfo(@RequestParam(required = false) String systemCodes,
                                                               @RequestParam(required = false) String orgId) {
        return ok(service.getRouterTreeInfo(ContextHolderUtils.getUserId(), systemCodes, orgId));
    }


    @Operation(summary = "获取用户路由菜单信息", tags = {"v1.0.0"}, description = "获取用户路由菜单信息")
    @GetMapping(value = "/menu-info")
    @Parameters({
            @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes"),
            @Parameter(description = "机构id,不传查询所有机构的", name = "orgId")
    })
    public Result<List<LoginRouteModel>> getRouterInfo(@RequestParam(required = false) String systemCodes,
                                                       @RequestParam(required = false) String orgId) {
        return ok(service.getRouterInfo(ContextHolderUtils.getUserId(), systemCodes, orgId));
    }


    @Operation(summary = "修改密码", tags = {"v1.0.0"}, description = "修改密码")
    @PostMapping(value = "/change/password")
    @AutoLog(note = "修改密码", type = AutoLog.EDIT)
    public Result<String> changePassword(@RequestBody @Valid ChangePasswordVo vo) {
        service.changePassword(vo);
        return ok("修改密码成功,请刷新页面重新登录");
    }


    @Operation(summary = "修改用户信息", tags = {"v1.0.0"}, description = "修改用户信息")
    @PostMapping(value = "/change/user-info")
    @AutoLog(note = "修改用户信息", type = AutoLog.EDIT)
    public Result<String> changeUserInfo(@RequestBody @Valid ChangeUserInfoVo vo) {
        service.changeUserInfo(vo);
        return ok("修改用户信息成功");
    }


    @Operation(summary = "找回密码", tags = {"v1.0.0"}, description = "找回密码")
    @PostMapping(value = "/find/password")
    @AutoLog(note = "找回密码", type = AutoLog.EDIT)
    public Result<String> findPassword(@RequestBody @Valid FindPasswordVo vo) {
        service.findPassword(vo);
        return ok("找回密码成功,请返回登录");
    }


    @Operation(summary = "修改手机号前验证旧手机验证码", tags = {"v1.0.0"}, description = "修改手机号前验证旧手机验证码")
    @PostMapping(value = "/check/old-phone-sms")
    @AutoLog(note = "修改手机号前验证旧手机验证码")
    public Result<String> checkOldUserPhoneSms(@RequestBody @Valid CheckOldUserPhoneVo vo) {
        service.checkOldUserPhoneSms(vo);
        return ok("验证码正确");
    }


    @Operation(summary = "修改手机号", tags = {"v1.0.0"}, description = "修改手机号")
    @PostMapping(value = "/change/user-phone")
    @AutoLog(note = "修改手机号", type = AutoLog.EDIT)
    public Result<String> changeUserPhone(@RequestBody @Valid ChangeUserPhoneVo vo) {
        service.changeUserPhone(vo);
        return ok("修改手机号码成功");
    }


    @Operation(summary = "绑定手机号", tags = {"v1.0.0"}, description = "绑定手机号")
    @PostMapping(value = "/bind/user-phone")
    @AutoLog(note = "绑定手机号", type = AutoLog.EDIT)
    public Result<String> bindUserPhone(@RequestBody @Valid BindUserPhoneVo vo) {
        service.bindUserPhone(vo);
        return ok("绑定手机号码成功");
    }
}
