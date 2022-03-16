// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.controller;

import indi.zxiaozhou.skillfull.auth.modules.common.service.IAuthCommonService;
import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.SmsCodeVo;
import indi.zxiaozhou.skillfull.auth.security.validate.ValidateDto;
import indi.zxiaozhou.skillfull.corecommon.annotation.Anonymous;
import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 权限服务公共接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:31:33
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Common", description = "权限服务公共接口")
@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonAuthController extends BaseController {
    private final IAuthCommonService service;


    @Operation(summary = "获取图片验证码", tags = {"v1.0.0"}, description = "获取图片验证码")
    @GetMapping(value = "/get/code/picture")
    @Anonymous
    @AutoLog(note = "获取图片验证码", type = AutoLog.QUERY)
    public Result<ValidateDto> getPictureCode(HttpServletRequest request) {
        return ok(service.getPictureCode(request));
    }


    @Operation(summary = "新手机号码获取验证码", tags = {"v1.0.0"}, description = "新手机号码获取验证码(会验证手机是否重复)")
    @GetMapping(value = "/new-phone/code/sms/{phone}")
    @Parameter(in = ParameterIn.PATH, description = "电话号码", name = "phone", required = true)
    @AutoLog(note = "新手机号码获取验证码(会验证手机是否重复)", type = AutoLog.QUERY)
    public Result<String> getNewPhoneSmsCode(@PathVariable @NotBlank(message = "手机号码不能为空") String phone, HttpServletRequest request) {
        service.getNewPhoneSmsCode(phone, request);
        return ok("发送短信验证码成功");
    }


    @Operation(summary = "旧手机号码获取验证码", tags = {"v1.0.0"}, description = "旧手机号码获取验证码(会验证手机号码是否与当前账号绑定,不需要鉴权)")
    @GetMapping(value = "/old-phone/code/sms/no-auth/{phone}")
    @Parameter(in = ParameterIn.PATH, description = "电话号码", name = "phone", required = true)
    @Anonymous
    @AutoLog(note = "旧手机号码获取验证码(会验证手机号码是否与当前账号绑定,不需要鉴权)", type = AutoLog.QUERY)
    public Result<String> getOldPhoneSmsCode(@PathVariable @NotBlank(message = "手机号码不能为空") String phone, HttpServletRequest request) {
        service.getOldPhoneSmsCode(phone, request);
        return ok("发送短信验证码成功");
    }


    @Operation(summary = "旧手机号码获取验证码", tags = {"v1.0.0"}, description = "旧手机号码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致,需要鉴权)")
    @GetMapping(value = "/old-phone/code/sms/auth/{phone}")
    @Parameter(in = ParameterIn.PATH, description = "电话号码", name = "phone", required = true)
    @AutoLog(note = "旧手机号码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致,需要鉴权)", type = AutoLog.QUERY)
    public Result<String> getOldPhoneSmsCodeAndAuth(@PathVariable @NotBlank(message = "手机号码不能为空") String phone, HttpServletRequest request) {
        service.getOldPhoneSmsCodeAndAuth(phone, request);
        return ok("发送短信验证码成功");
    }


    @Operation(summary = "新手机号码通过图片验证码获取验证码", tags = {"v1.0.0"}, description = "新手机号码通过图片验证码获取验证码(会验证手机是否重复)")
    @PostMapping(value = "/new-phone/code/sms/by-picture-code")
    @AutoLog(note = "新手机号码通过图片验证码获取验证码(会验证手机是否重复)", type = AutoLog.QUERY)
    public Result<String> getNewPhoneSmsCode(@RequestBody @Valid SmsCodeVo smsCodeVo, HttpServletRequest request) {
        service.getNewPhoneSmsCode(smsCodeVo, request);
        return ok("发送短信验证码成功");
    }


    @Operation(summary = "旧手机号码通过图片验证码获取验证码", tags = {"v1.0.0"}, description = "旧手机号码通过图片验证码获取验证码(会验证手机号码是否与当前账号绑定,不需要鉴权)")
    @PostMapping(value = "/old-phone/code/sms/no-auth/by-picture-code")
    @Anonymous
    @AutoLog(note = "旧手机号码通过图片验证码获取验证码(会验证手机号码是否与当前账号绑定,不需要鉴权)", type = AutoLog.QUERY)
    public Result<String> getOldPhoneSmsCode(@RequestBody @Valid SmsCodeVo smsCodeVo, HttpServletRequest request) {
        service.getOldPhoneSmsCode(smsCodeVo, request);
        return ok("发送短信验证码成功");
    }


    @Operation(summary = "旧手机号码通过图片验证码获取验证码", tags = {"v1.0.0"}, description = "旧手机号码通过图片验证码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致,需要鉴权)")
    @PostMapping(value = "/old-phone/code/sms/auth/by-picture-code")
    @AutoLog(note = "旧手机号码通过图片验证码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致,需要鉴权)", type = AutoLog.QUERY)
    public Result<String> getOldPhoneSmsCodeAndAuth(@RequestBody @Valid SmsCodeVo smsCodeVo, HttpServletRequest request) {
        service.getOldPhoneSmsCodeAndAuth(smsCodeVo, request);
        return ok("发送短信验证码成功");
    }
}