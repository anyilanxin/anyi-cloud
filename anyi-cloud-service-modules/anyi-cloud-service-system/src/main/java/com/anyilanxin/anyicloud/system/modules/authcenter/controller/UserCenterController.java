

package com.anyilanxin.anyicloud.system.modules.authcenter.controller;

import com.anyilanxin.anyicloud.corecommon.annotation.Anonymous;
import com.anyilanxin.anyicloud.corecommon.annotation.AutoLog;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteTreeModel;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePhoneVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.service.IUserCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:16
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "UserCenter", description = "用户中心")
@RequestMapping(value = "/rbac-user-center", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCenterController extends BaseController {
    private final IUserCenterService service;

    @Operation(summary = "获取当前用户菜单信息", tags = {"v1.0.0"}, description = "获取当前用户菜单信息")
    @GetMapping(value = "/get/router")
    @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes")
    public Result<List<UserRouteModel>> getRouterInfo(@RequestParam(required = false) String systemCodes) {
        return ok(service.getRouterInfo(systemCodes));
    }


    @Operation(summary = "获取用户路由菜单信息(树形)", tags = {"v1.0.0"}, description = "获取用户路由菜单信息(树形)")
    @GetMapping(value = "/get/router-tree")
    @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes")
    public Result<List<UserRouteTreeModel>> getRouterInfoTree(@RequestParam(required = false) String systemCodes) {
        return ok(service.getRouterInfoTree(systemCodes));
    }


    @Operation(summary = "获取用户机构列表(树形)", tags = {"v1.0.0"}, description = "获取用户机构列表(树形)")
    @GetMapping(value = "/get/user-org-info")
    @AutoLog(note = "获取用户机构列表(树形)", type = AutoLog.QUERY)
    public Result<List<UserOrgTreeInfo>> getUserOrgInfo() {
        return ok(service.getUserOrgInfo());
    }


    @Operation(summary = "修改用户资料", tags = {"v1.0.0"}, description = "修改用户资料")
    @GetMapping(value = "/update/info")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    public Result<String> updateUserInfo(@RequestBody @Valid UpdateInfoVo vo) {
        service.updateUserInfo(vo);
        return ok("修改信息用户成功");
    }


    @Operation(summary = "修改用户头像", tags = {"v1.0.0"}, description = "修改用户头像")
    @GetMapping(value = "/update/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        service.updateUserAvatar(file);
        return ok("修改头像成功");
    }


    @Operation(summary = "修改用户手机号", tags = {"v1.0.0"}, description = "修改用户手机号")
    @GetMapping(value = "/update/phone")
    @AutoLog(note = "修改用户手机号", type = AutoLog.QUERY)
    public Result<String> updateUserPhone(@RequestBody @Valid UpdatePhoneVo vo) {
        service.updateUserPhone(vo);
        return ok("修改手机号成功");
    }


    @Operation(summary = "修改密码", tags = {"v1.0.0"}, description = "修改密码")
    @GetMapping(value = "/update/password")
    @AutoLog(note = "修改密码", type = AutoLog.QUERY)
    public Result<String> updatePassword(@RequestBody @Valid UpdatePasswordVo vo) {
        service.updatePassword(vo);
        return ok("修改密码成功");
    }


    @Operation(summary = "找回密码", tags = {"v1.0.0"}, description = "找回密码")
    @GetMapping(value = "/update/find-password")
    @AutoLog(note = "找回密码", type = AutoLog.QUERY)
    @Anonymous
    public Result<String> findPassword(@RequestBody @Valid FindPasswordVo vo) {
        service.findPassword(vo);
        return ok("找回密码成功");
    }


    @Operation(summary = "修改手机号或者找回密码发送短信验证码", tags = {"v1.0.0"}, description = "修改手机号或者找回密码发送短信验证码")
    @Parameter(in = ParameterIn.PATH, description = "手机号", name = "phone", required = true)
    @GetMapping(value = "/send-sms-code/{phone}")
    @Anonymous
    @AutoLog(note = "修改手机号或者找回密码发送短信验证码", type = AutoLog.QUERY)
    public Result<String> sendSmsCode(@PathVariable(required = false) @PathNotBlankOrNull(message = "电话号码不能为空不能为空") String phone) {
        service.sendSmsCode(phone);
        return ok("发送短信验证码成功");
    }
}
