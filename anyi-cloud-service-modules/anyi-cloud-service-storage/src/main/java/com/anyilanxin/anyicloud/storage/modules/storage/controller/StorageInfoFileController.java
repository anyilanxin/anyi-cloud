/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.skillfull.storage.modules.storage.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageModel;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.service.IStorageInfoFileService;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件服务(StorageInfoFile)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-05 09:57:58
 * @since 1.0.0
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
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "存储文件夹", name = "fileDirPrefix"), @Parameter(in = ParameterIn.QUERY, description = "文件引擎类型：1-本地，2-ali oss,3-minio,默认1，具体与StorageType一致,具体与StorageType一致", name = "fileStorageType")})
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<StorageInfoModel> storage(@RequestParam(value = "file") MultipartFile file, @RequestParam(required = false, defaultValue = "") String fileDirPrefix, final HttpServletRequest request) {
        return ok(service.storage(file, fileDirPrefix, request));
    }


    @Operation(summary = "批量上传文件", tags = {"v1.0.0"}, description = "批量上传文件")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "存储文件夹", name = "fileDirPrefix"), @Parameter(in = ParameterIn.QUERY, description = "文件引擎类型：1-本地，2-ali oss,3-minio,默认1，具体与StorageType一致,具体与StorageType一致", name = "fileStorageType")})
    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<StorageInfoModel>> storageBatch(@RequestParam(value = "files") List<MultipartFile> files, @RequestParam(required = false, defaultValue = "") String fileDirPrefix, final HttpServletRequest request) {
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
