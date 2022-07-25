// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.receive.controlle;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.base.model.stream.AuthLogModel;
import com.anyilanxin.skillfull.corecommon.base.model.stream.OperateLogModel;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.skillfull.logging.modules.manage.service.IOperateService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志接收处理
 *
 * @author zxiaozhou
 * @date 2022-01-27 19:45
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ReceiveLog", description = "日志接收")
@RequestMapping(value = "/receive-log", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceiveController extends BaseController {
    private final IAuthDataService authDataService;
    private final IOperateService operateService;


    @Operation(summary = "接收授权日志", tags = {"v1.0.0"}, description = "接收授权日志")
    @DeleteMapping(value = "/receive/auth-log")
    public Result<String> receiveAuthLog(@RequestBody AuthLogModel model) {
        authDataService.save(model);
        return ok("接收成功");
    }


    @Operation(summary = "批量接收授权日志", tags = {"v1.0.0"}, description = "接收授权日志")
    @Parameter(in = ParameterIn.PATH, description = "授权日志id", name = "authLogId", required = true)
    @DeleteMapping(value = "/receive/batch/auth-log")
    public Result<String> receiveBatchAuthLog(@RequestBody List<AuthLogModel> models) {
        authDataService.saveBatch(models);
        return ok("批量接收成功");
    }


    @Operation(summary = "接收操作日志", tags = {"v1.0.0"}, description = "接收操作日志")
    @DeleteMapping(value = "/receive/operate-log")
    public Result<String> receiveOperateLog(@RequestBody OperateLogModel model) {
        operateService.save(model);
        return ok("接收成功");
    }


    @Operation(summary = "批量接收操作日志", tags = {"v1.0.0"}, description = "接收操作日志")
    @DeleteMapping(value = "/receive/batch/operate-log")
    public Result<String> receiveBatchOperateLog(@RequestBody List<OperateLogModel> models) {
        operateService.saveBatch(models);
        return ok("批量接收成功");
    }

}
