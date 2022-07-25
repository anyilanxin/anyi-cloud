// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.base.model.web.WebSecurityModel;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.corewebflux.base.controller.BaseController;
import com.anyilanxin.skillfull.gateway.modules.manage.service.IToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 工具类控制层
 *
 * @author zxiaozhou
 * @date 2020-09-28 10:05
 * @since JDK11
 */
@Tag(name = "Tools", description = "工具相关")
@RestController
@RequestMapping(value = "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ToolsController extends BaseController {
    private final IToolService service;

    @Operation(summary = "获取请求安全基础信息", tags = {"v1.0.0"}, description = "获取请求安全基础信息")
    @GetMapping("/select/base-security")
    Mono<Result<WebSecurityModel>> getBaseSecurity() {
        return ok(service.getBaseSecurity());
    }


    @Operation(summary = "请求安全基础信息刷新", tags = {"v1.0.0"}, description = "请求安全基础信息刷新")
    @Parameter(in = ParameterIn.PATH, description = "请求序列", name = "serialNumber", required = true)
    @GetMapping("/select/base-security/refresh/{serialNumber}")
    Mono<Result<WebSecurityModel>> getRefreshBaseSecurity(@PathVariable @PathNotBlankOrNull(message = "请求序列不能为空") String serialNumber) {
        return ok(service.getRefreshBaseSecurity(serialNumber));
    }
}
