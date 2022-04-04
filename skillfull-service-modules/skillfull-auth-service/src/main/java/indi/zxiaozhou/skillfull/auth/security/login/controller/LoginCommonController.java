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

import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.OnlineUserPageVo;
import indi.zxiaozhou.skillfull.auth.security.login.service.ILoginCommonService;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.OnlineUserInfoDto;
import indi.zxiaozhou.skillfull.corecommon.annotation.Anonymous;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
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

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.LOGIN_OUT;

/**
 * 登录相关公共接口
 *
 * @author zxiaozhou
 * @date 2021-06-04 03:08
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "LoginCommon", description = "登录公共接口")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginCommonController extends BaseController {
    private final ILoginCommonService service;

    @Operation(summary = "退出登录", tags = {"v1.0.0"}, description = "退出登录(这是一个空方法，实际登录交由security处理)")
    @GetMapping(value = LOGIN_OUT)
    @Anonymous
    public Result<String> loginOut() {
        return ok("退出登录成功");
    }


    @Operation(summary = "分页查询在线用户信息", tags = {"v1.0.0"}, description = "分页查询在线用户信息")
    @PostMapping(value = "/page-online-user")
    public Result<PageDto<OnlineUserInfoDto>> selectOnlinePage(@RequestBody OnlineUserPageVo vo) {
        return ok(service.selectOnlinePage(vo));
    }


    @Operation(summary = "踢用户下线", tags = {"v1.0.0"}, description = "踢用户下线(如果需要服务器主动使用户下线,需要消息服务支持)")
    @GetMapping(value = "/kick-out/{loginUnique}")
    @Parameter(in = ParameterIn.PATH, description = "登录唯一标识", name = "loginUnique", required = true)
    public Result<String> kickOut(@PathVariable(required = false) @PathNotBlankOrNull(message = "登录唯一标识不能为空") String loginUnique) {
        service.kickOut(loginUnique);
        return ok("踢除用户下线成功");
    }
}
