/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.storage.engine;

import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

/**
* 解析api doc
*
* @author zxiaozhou
* @date 2022-03-30 19:39
* @since JDK1.8
*/
public interface IStorageEngineService {
    /**
    * 单个存储
    *
    * @param fileDirPrefix ${@link String}
    * @param file ${@link MultipartFile}
    * @return StorageInfoModel ${@link StorageInfoModel}
    * @author zxiaozhou
    * @date 2022-04-05 10:19
    */
    StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request);

    /**
    * 批量存储
    *
    * @param fileDirPrefix ${@link String}
    * @param files ${@link List<MultipartFile>}
    * @return List<StorageInfoModel> ${@link List<StorageInfoModel>}
    * @author zxiaozhou
    * @date 2022-04-05 10:19
    */
    List<StorageInfoModel> storageBatch(
            List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request);

    /**
    * 批量url地址存储
    *
    * @param model ${@link StorageModel}
    * @return List<StorageInfoUrlModel> ${@link List<StorageInfoUrlModel>}
    * @author zxiaozhou
    * @date 2022-04-05 10:19
    */
    List<StorageInfoUrlModel> storageBatchUrl(StorageModel model);
}
