package com.anyilanxin.skillfull.auth.modules.login.controller;

import com.anyilanxin.skillfull.auth.modules.login.service.IAuthCodeService;
import com.anyilanxin.skillfull.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.skillfull.corecommon.annotation.AutoLog;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

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
@Tag(name = "Code", description = "验证码相关")
@RequestMapping(value = "/code", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeController extends BaseController {
    private final IAuthCodeService service;

    @Operation(summary = "获取图片验证码", tags = {"v1.0.0"}, description = "获取图片验证码")
    @GetMapping(value = "/picture")
    @PreAuthorize("permitAll()")
    @AutoLog(note = "获取图片验证码", type = AutoLog.QUERY)
    public Result<ValidateDto> getPictureCode(HttpServletRequest request) {
        return ok(service.getPictureCode(request));
    }


    @Operation(summary = "获取登录手机验证码", tags = {"v1.0.0"}, description = "获取登录手机验证码(会验证手机是否存在)")
    @GetMapping(value = "/sms/{phone}")
    @PreAuthorize("permitAll()")
    @Parameter(in = ParameterIn.PATH, description = "电话号码", name = "phone", required = true)
    @AutoLog(note = "获取登录手机验证码(会验证手机是否存在)", type = AutoLog.QUERY)
    public Result<String> getPhoneSmsCode(@PathVariable @NotBlank(message = "手机号码不能为空") String phone, HttpServletRequest request) {
        service.getPhoneSmsCode(phone, request);
        return ok("发送短信验证码成功");
    }
}
