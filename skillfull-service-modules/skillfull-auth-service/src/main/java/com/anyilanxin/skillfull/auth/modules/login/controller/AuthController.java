// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.controller;

import com.anyilanxin.skillfull.auth.modules.login.service.IAuthService;
import com.anyilanxin.skillfull.corecommon.annotation.AutoLog;
import com.anyilanxin.skillfull.corecommon.auth.model.UserInfo;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权相关
 *
 * @author zxiaozhou
 * @date 2022-02-19 09:25
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "权限相关")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController extends BaseController {
    private final IAuthService service;

    @Operation(summary = "取消授权", tags = {"v1.0.0"}, description = "取消授权")
    @GetMapping(value = "/oauth/logout")
    @PreAuthorize("permitAll()")
    public Result<String> logOut() {
        service.logOut();
        return ok("取消授权成功");
    }


    @Operation(summary = "获取当前用户信息", tags = {"v1.0.0"}, description = "获取当前用户信息")
    @GetMapping(value = "/oauth/user-info")
    @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    public Result<UserInfo> getUserInfo(@RequestParam(required = false) String orgId) {
        return ok(service.getUserInfo(orgId));
    }


//    @Operation(summary = "授权异常处理", tags = {"v1.0.0"}, description = "授权异常处理")
//    @GetMapping(value = "/auth/error")
//    public Result<String> error(Map<String, String> params) {
//        log.info("------------AuthController------------>error:\n{}", params);
//        return ok("取消授权成功");
//    }
}
