// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.controller;

import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFileVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.IOssFileService;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileUploadBatchDto;
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
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

import static indi.zxiaozhou.skillfull.storage.core.constant.CommonStorageConstant.OSS_FILE;

/**
 * oss文件(OssFile)控制层
 *
 * @author zxiaozhou
 * @date 2020-10-22 23:36:20
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "OssFile", description = "oss上传")
@RequestMapping(value = OSS_FILE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OssFileController extends BaseController {
    private final IOssFileService service;


    @Operation(summary = "文件上传到oss", tags = {"v1.0.0"}, description = "上传文件到oss")
    @PostMapping(value = "/upload")
    @Parameter(description = "文件存放目录", name = "fileFolder")
    public Mono<Result<OssFileDto>> upload(@RequestParam(value = "file") MultipartFile file,
                                           @RequestParam(required = false, defaultValue = "") String fileFolder,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.upload(file, fileFolder));
    }


    @Operation(summary = "url文件上传到服务器", tags = {"v1.0.0"}, description = "文件上传")
    @PostMapping(value = "/upload/url-one")
    public Mono<Result<OssFileDto>> uploadUrlOne(@RequestBody @Valid OssFileVo vo,
                                                 final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.uploadUrlOne(vo));
    }


    @Operation(summary = "url文件批量上传到服务器", tags = {"v1.0.0"}, description = "文件上传")
    @PostMapping(value = "/upload/url-batch")
    public Mono<Result<OssFileUploadBatchDto>> uploadUrlBatch(@RequestBody @NotNullSize(message = "待上传文件信息不能为空") @Valid List<OssFileVo> vos,
                                                              final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.uploadUrlBatch(vos));
    }


    @Operation(summary = "通过文件id获取oss访问地址", tags = {"v1.0.0"}, description = "通过文件id获取oss访问地址")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "ossFileId", required = true)
    @GetMapping(value = "/get/url/{ossFileId}")
    public Mono<Result<String>> getAccessUrlById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String ossFileId,
                                                 final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getAccessUrlById(ossFileId), "获取访问url成功");
    }


    @Operation(summary = "oss文件逻辑删除", tags = {"v1.0.0"}, description = "删除oss文件")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "ossFileId", required = true)
    @DeleteMapping(value = "/delete-one/{ossFileId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String ossFileId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(ossFileId);
        return ok("删除成功");
    }


    @Operation(summary = "oss文件逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除oss文件")
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除文件id不能为空") List<String> ossFileIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(ossFileIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过文件id查询详情", tags = {"v1.0.0"}, description = "查询oss文件详情")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "ossFileId", required = true)
    @GetMapping(value = "/select/one/{ossFileId}")
    public Mono<Result<OssFileDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String ossFileId,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(ossFileId, true));
    }


    @Operation(summary = "oss文件分页查询", tags = {"v1.0.0"}, description = "分页查询oss文件")
    @PostMapping(value = "/select/page")
    @AutoLog(note = "oss文件分页查询", type = AutoLog.QUERY)
    public Mono<Result<PageDto<OssFilePageDto>>> selectPage(@RequestBody OssFilePageVo vo,
                                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}