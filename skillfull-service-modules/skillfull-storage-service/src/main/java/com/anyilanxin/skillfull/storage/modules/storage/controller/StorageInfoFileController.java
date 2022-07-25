// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage.modules.storage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.service.IStorageInfoFileService;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.anyilanxin.skillfull.storageapi.model.StorageInfoModel;
import com.anyilanxin.skillfull.storageapi.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storageapi.model.StorageModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 本地文件服务(StorageInfoFile)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-05 09:57:58
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "StorageInfoFile", description = "本地文件服务相关")
@RequestMapping(value = "/storage-file", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageInfoFileController extends BaseController {
    private final IStorageInfoFileService service;

    @Operation(summary = "上传文件", tags = {"v1.0.0"}, description = "上传文件")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "存储文件夹", name = "fileDirPrefix"),
            @Parameter(in = ParameterIn.QUERY, description = "文件引擎类型：1-本地，2-ali oss,3-minio,默认1，具体与StorageType一致,具体与StorageType一致", name = "fileStorageType")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<StorageInfoModel> storage(@RequestParam(value = "file") MultipartFile file,
                                            @RequestParam(required = false, defaultValue = "") String fileDirPrefix,
                                            final HttpServletRequest request) {
        return ok(service.storage(file, fileDirPrefix, request));
    }


    @Operation(summary = "批量上传文件", tags = {"v1.0.0"}, description = "批量上传文件")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "存储文件夹", name = "fileDirPrefix"),
            @Parameter(in = ParameterIn.QUERY, description = "文件引擎类型：1-本地，2-ali oss,3-minio,默认1，具体与StorageType一致,具体与StorageType一致", name = "fileStorageType")
    })
    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<List<StorageInfoModel>> storageBatch(@RequestParam(value = "files") List<MultipartFile> files,
                                                       @RequestParam(required = false, defaultValue = "") String fileDirPrefix,
                                                       final HttpServletRequest request) {
        return ok(service.storageBatch(files, fileDirPrefix, request));
    }


    @Operation(summary = "批量上传url文件到服务器", tags = {"v1.0.0"}, description = "批量上传url文件到服务器")
    @PostMapping(value = "/upload/batch-url")
    public Result<List<StorageInfoUrlModel>> storageBatchUrl(@RequestBody @Valid StorageModel model) {
        return ok(service.storageBatchUrl(model));
    }


    @Operation(summary = "本地文件服务逻辑删除", tags = {"v1.0.0"}, description = "删除本地文件服务")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "fileId", required = true)
    @DeleteMapping(value = "/delete-one/{fileId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String fileId) {
        service.deleteById(fileId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "本地文件服务逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除本地文件服务")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除文件id不能为空") List<String> fileIds) {
        service.deleteBatch(fileIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过文件id查询详情", tags = {"v1.0.0"}, description = "查询本地文件服务详情")
    @Parameter(in = ParameterIn.PATH, description = "文件id", name = "fileId", required = true)
    @GetMapping(value = "/select/one/{fileId}")
    public Result<StorageInfoModel> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "文件id不能为空") String fileId) {
        return ok(service.getById(fileId));
    }


    @Operation(summary = "本地文件服务分页查询", tags = {"v1.0.0"}, description = "分页查询本地文件服务")
    @PostMapping(value = "/select/page")
    public Result<PageDto<StorageInfoFilePageDto>> selectPage(@RequestBody StorageInfoFilePageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
