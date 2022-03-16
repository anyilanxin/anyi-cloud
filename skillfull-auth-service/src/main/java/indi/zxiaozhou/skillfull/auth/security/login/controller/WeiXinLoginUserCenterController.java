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

import indi.zxiaozhou.skillfull.auth.security.login.service.IWeiXinLoginUserCenterService;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "WeiXinUserCenter", description = "微信端用户中心")
@RequestMapping(value = "/web/user-center", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeiXinLoginUserCenterController extends BaseController {
    private final IWeiXinLoginUserCenterService service;


}
