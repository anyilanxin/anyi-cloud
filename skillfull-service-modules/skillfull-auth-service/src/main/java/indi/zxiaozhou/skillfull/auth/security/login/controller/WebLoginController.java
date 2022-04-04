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

import indi.zxiaozhou.skillfull.auth.security.login.service.model.TokenInfo;
import indi.zxiaozhou.skillfull.auth.security.model.LoginPicture;
import indi.zxiaozhou.skillfull.auth.security.model.LoginSms;
import indi.zxiaozhou.skillfull.corecommon.annotation.Anonymous;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.WEB_PASSWORD_LOGIN;
import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.WEB_SMS_LOGIN;

/**
 * 登录相关
 *
 * @author zxiaozhou
 * @date 2020-06-30 15:29
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "WebLogin", description = "web端登录")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WebLoginController extends BaseController {

    @Operation(summary = "账号密码登录", tags = {"v1.0.0"}, description = "账号密码登录(这是一个空方法，实际登录交由security处理)")
    @PostMapping(value = WEB_PASSWORD_LOGIN)
    @Anonymous
    public Result<TokenInfo> loginPicture(@RequestBody @Valid LoginPicture vo) {
        return ok(new TokenInfo());
    }


    @Operation(summary = "短信验证码登录", tags = {"v1.0.0"}, description = "短信验证码登录(这是一个空方法，实际登录交由security处理)")
    @PostMapping(value = WEB_SMS_LOGIN)
    @Anonymous
    public Result<TokenInfo> loginSmsCode(@RequestBody @Valid LoginSms vo) {
        return ok(new TokenInfo());
    }
}
