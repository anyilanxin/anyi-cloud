// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.local.controller;

import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFileVo;
import indi.zxiaozhou.skillfull.storage.modules.local.service.ILocalFileService;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileUploadBatchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static indi.zxiaozhou.skillfull.storage.core.constant.CommonStorageConstant.LOCAL_FILE;
import static indi.zxiaozhou.skillfull.storage.core.constant.CommonStorageConstant.LOCAL_FILE_PREVIEW;

/**
 * 本地文件服务(LocalFile)控制层
 *
 * @author zxiaozhou
 * @date 2020-10-22 23:35:05
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "LocalFile", description = "本地上传")
@RequestMapping(value = LOCAL_FILE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LocalFileController extends BaseController {
    private final ILocalFileService service;


    @Operation(summary = "通过预览路径访问文件", tags = {"v1.0.0"}, description = "通过预览路径访问文件(会加上文件content-type)")
    @Parameter(description = "是否显示:true-显示,false-下载", name = "isShow", required = true)
    @GetMapping(value = LOCAL_FILE_PREVIEW + "/**")
    public Mono<Void> downloadByPreview(final ServerHttpResponse response,
                                        final ServerHttpRequest request,
                                        @RequestParam(required = false, defaultValue = "true") boolean isShow) {
        return service.downloadByPreview(request.getURI().getPath(), response, isShow);
    }


    @Operation(summary = "通过id下载文件", tags = {"v1.0.0"}, description = "通过id下载文件(不会加上文件content-type)")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, description = "文件id", name = "localFileId", required = true),
            @Parameter(description = "是否显示:true-显示,false-下载", name = "isShow", required = true)
    })
    @GetMapping(value = "/download/{localFileId}")
    public Mono<Void> downloadById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String localFileId,
                                   final ServerHttpResponse response,
                                   @RequestParam(required = false, defaultValue = "true") boolean isShow) {
        return service.downloadById(localFileId, response, isShow);
    }


    @Operation(summary = "上传文件到服务器", tags = {"v1.0.0"}, description = "文件上传")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameter(description = "文件存放目录", name = "fileFolder")
    public Mono<Result<LocalFileDto>> upload(@RequestPart("file") @NotNull(message = "请选择待上传文件") Mono<FilePart> file,
                                             @RequestParam(required = false, defaultValue = "") String fileFolder,
                                             final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return service.upload(file, fileFolder, request).flatMap(BaseController::ok);
    }


    @Operation(summary = "url文件上传到服务器", tags = {"v1.0.0"}, description = "文件上传", hidden = true)
    @PostMapping(value = "/upload/url-one")
    public Mono<Result<LocalFileDto>> uploadUrlOne(@RequestBody @Valid LocalFileVo vo, final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.uploadUrlOne(vo));
    }


    @Operation(summary = "url文件批量上传到服务器", tags = {"v1.0.0"}, description = "文件上传", hidden = true)
    @PostMapping(value = "/upload/url-batch")
    public Mono<Result<LocalFileUploadBatchDto>> uploadUrlBatch(@RequestBody @NotNullSize(message = "待上传文件信息不能为空") @Valid List<LocalFileVo> vos,
                                                                final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.uploadUrlBatch(vos));
    }


    @Operation(summary = "本地文件删除", tags = {"v1.0.0"}, description = "删除本地文件服务")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "localFile", required = true)
    @DeleteMapping(value = "/delete-one/{localFileId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String localFileId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(localFileId);
        return ok("删除成功");
    }


    @Operation(summary = "本地文件批量删除", tags = {"v1.0.0"}, description = "批量删除本地文件服务")
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除文件id不能为空") List<String> localFileIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(localFileIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过文件id查询详情", tags = {"v1.0.0"}, description = "查询本地文件服务详情")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "localFileId", required = true)
    @GetMapping(value = "/select/one/{localFileId}")
    public Mono<Result<LocalFileDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String localFileId,
                                              final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(localFileId));
    }


    @Operation(summary = "本地文件服务分页查询", tags = {"v1.0.0"}, description = "分页查询本地文件服务")
    @PostMapping(value = "/select/page")
    @AutoLog(note = "oss文件分页查询", type = AutoLog.QUERY)
    public Mono<Result<PageDto<LocalFilePageDto>>> selectPage(@RequestBody LocalFilePageVo vo,
                                                              final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}