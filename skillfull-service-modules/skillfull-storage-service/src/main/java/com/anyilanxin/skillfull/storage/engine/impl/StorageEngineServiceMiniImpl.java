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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.storage.engine.impl;

import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.engine.EngineCondition;
import com.anyilanxin.skillfull.storage.engine.IStorageEngineService;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * minio存储
 *
 * @author zxiaozhou
 * @date 2022-03-30 19:54
 * @since JDK1.8
 */
@Component(value = StorageTypeConstant.MINIO_STORAGE)
@Conditional(EngineCondition.class)
public class StorageEngineServiceMiniImpl implements IStorageEngineService {

  @Override
  public StorageInfoModel storage(
      MultipartFile file, String fileDirPrefix, HttpServletRequest request) {
    return null;
  }

  @Override
  public List<StorageInfoModel> storageBatch(
      List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request) {
    return null;
  }

  @Override
  public List<StorageInfoUrlModel> storageBatchUrl(StorageModel model) {
    return null;
  }
}
