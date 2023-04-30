/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.storagerpc.feign;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
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
 * @author zxiaozhou
 * @date 2020-09-12 16:54
 * @since JDK11
 */
@FeignClient(value = ServiceConstant.STORAGE_SERVICE, path = ServiceConstant.STORAGE_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface StorageRemoteService {

    /** 上传文件 */
    @PostMapping(value = "/storage-file/upload")
    Result<StorageInfoModel> upload(@RequestParam(value = "file") MultipartFile file, @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /** 批量上传文件 */
    @Operation(summary = "批量上传文件", tags = {"v1.0.0"}, description = "批量上传文件")
    @PostMapping(value = "/storage-file/upload-batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<List<StorageInfoModel>> uploadBatch(@RequestParam(value = "files") List<MultipartFile> files, @RequestParam(required = false, defaultValue = "") String fileDirPrefix);


    /** 批量上传url文件到服务器 */
    @PostMapping(value = "/upload/batch-url")
    List<StorageInfoUrlModel> uploadBatchUrl(@RequestBody @Valid StorageModel model);
}
