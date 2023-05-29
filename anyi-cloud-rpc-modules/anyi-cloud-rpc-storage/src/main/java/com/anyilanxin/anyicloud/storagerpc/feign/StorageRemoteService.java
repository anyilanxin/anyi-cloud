

package com.anyilanxin.anyicloud.storagerpc.feign;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageModel;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务feign
 *
 * @author zxh
 * @date 2020-09-12 16:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.STORAGE_SERVICE, path = ServiceConstant.STORAGE_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface StorageRemoteService {

    /**
     * 上传文件
     */
    @PostMapping(value = "/storage-file/upload")
    Result<StorageInfoModel> upload(@RequestParam(value = "file") MultipartFile file, @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /**
     * 批量上传文件
     */
    @Operation(summary = "批量上传文件", tags = {"v1.0.0"}, description = "批量上传文件")
    @PostMapping(value = "/storage-file/upload-batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<List<StorageInfoModel>> uploadBatch(@RequestParam(value = "files") List<MultipartFile> files, @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /**
     * 批量上传url文件到服务器
     */
    @PostMapping(value = "/upload/batch-url")
    List<StorageInfoUrlModel> uploadBatchUrl(@RequestBody @Valid StorageModel model);
}
