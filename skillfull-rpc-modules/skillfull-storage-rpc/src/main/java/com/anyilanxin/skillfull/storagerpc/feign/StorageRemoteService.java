// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storagerpc.feign;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 存储服务feign
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:54
 * @since JDK11
 */
@FeignClient(value = ServiceConstant.STORAGE_SERVICE, path = ServiceConstant.STORAGE_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface StorageRemoteService {

    /**
     * 上传文件
     */
    @PostMapping(value = "/storage-file/upload")
    Result<StorageInfoModel> upload(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /**
     * 批量上传文件
     */
    @Operation(summary = "批量上传文件", tags = {"v1.0.0"}, description = "批量上传文件")
    @PostMapping(value = "/storage-file/upload-batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<List<StorageInfoModel>> uploadBatch(@RequestParam(value = "files") List<MultipartFile> files,
                                               @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /**
     * 批量上传url文件到服务器
     */
    @PostMapping(value = "/upload/batch-url")
    List<StorageInfoUrlModel> uploadBatchUrl(@RequestBody @Valid StorageModel model);

}
