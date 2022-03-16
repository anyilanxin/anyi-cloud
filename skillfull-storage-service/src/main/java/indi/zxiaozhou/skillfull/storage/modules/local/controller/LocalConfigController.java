package indi.zxiaozhou.skillfull.storage.modules.local.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalConfigVo;
import indi.zxiaozhou.skillfull.storage.modules.local.service.ILocalConfigService;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalConfigDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * 本地文件配置(LocalConfig)控制层
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "LocalConfig", description = "本地文件配置")
@RequestMapping(value = "/local-config", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocalConfigController extends BaseController {
    private final ILocalConfigService service;


    @Operation(summary = "本地文件配置添加", tags = {"v1.0.0"}, description = "添加本地文件配置")
    @PostMapping(value = "/insert")
    public Mono<Result<String>> insert(@RequestBody @Valid LocalConfigVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过本地配置文件id修改", tags = {"v1.0.0"}, description = "修改本地文件配置")
    @Parameter(in = ParameterIn.PATH, description = "本地配置文件id", name = "localConfigId", required = true)
    @PutMapping(value = "/update/{localConfigId}")
    public Mono<Result<String>> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "本地配置文件id不能为空") String localConfigId,
                                       @RequestBody @Valid LocalConfigVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateById(localConfigId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "本地文件配置逻辑删除", tags = {"v1.0.0"}, description = "删除本地文件配置")
    @Parameter(in = ParameterIn.PATH, description = "本地配置文件id", name = "localConfigId", required = true)
    @DeleteMapping(value = "/delete-one/{localConfigId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "本地配置文件id不能为空") String localConfigId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(localConfigId);
        return ok("删除成功");
    }


    @Operation(summary = "通过本地配置文件id查询详情", tags = {"v1.0.0"}, description = "查询本地文件配置详情")
    @Parameter(in = ParameterIn.PATH, description = "本地配置文件id", name = "localConfigId", required = true)
    @GetMapping(value = "/select/one/{localConfigId}")
    public Mono<Result<LocalConfigDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "本地配置文件id不能为空") String localConfigId,
                                                final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(localConfigId));
    }


    @Operation(summary = "通过条件查询本地文件配置多条数据", tags = {"v1.0.0"}, description = "通过条件查询本地文件配置")
    @PostMapping(value = "/select/list")
    public Mono<Result<List<LocalConfigDto>>> getList(final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.selectListByModel());
    }
}
