/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.storage.modules.storage.service;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.storage.modules.storage.controller.vo.StorageInfoFilePageQuery;
import com.anyilanxin.anyicloud.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.anyicloud.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.anyilanxin.anyicloud.storageadapter.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storageadapter.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storageadapter.model.StorageModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 本地文件服务(StorageInfoFile)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-05 09:57:59
 * @since 1.0.0
 */
public interface IStorageInfoFileService extends BaseService<StorageInfoFileEntity> {
    /**
     * 分页查询
     *
     * @param vo ${@link StorageInfoFilePageQuery} 本地文件服务分页查询Vo
     * @return AnYiPageResult<StorageInfoFilePageDto> ${@link AnYiPlusPageResult <  StorageInfoFilePageDto  >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    AnYiPageResult<StorageInfoFilePageDto> pageByModel(StorageInfoFilePageQuery vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param fileId ${@link String} 文件id
     * @return StorageInfoModel ${@link StorageInfoModel} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    StorageInfoModel getById(String fileId) throws RuntimeException;


    /**
     * 通过fileId删除
     *
     * @param fileId ${@link String} 文件id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    void deleteById(String fileId) throws RuntimeException;


    /**
     * 本地文件服务批量删除
     *
     * @param fileIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    void deleteBatch(List<String> fileIds) throws RuntimeException;


    /**
     * 单个存储
     *
     * @param file          ${@link MultipartFile}
     * @param fileDirPrefix ${@link String}
     * @param request       ${@link HttpServletRequest}
     * @return StorageInfoModel ${@link StorageInfoModel}
     * @author zxh
     * @date 2022-04-05 10:19
     */
    StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request);


    /**
     * 批量存储
     *
     * @param fileDirPrefix ${@link String}
     * @param request       ${@link HttpServletRequest}
     * @param files         ${@link List<MultipartFile>}
     * @return List<StorageInfoModel> ${@link List<StorageInfoModel>}
     * @author zxh
     * @date 2022-04-05 10:19
     */
    List<StorageInfoModel> storageBatch(List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request);


    /**
     * 批量url地址存储
     *
     * @param model ${@link StorageModel}
     * @return List<StorageInfoUrlModel> ${@link List< StorageInfoUrlModel >}
     * @author zxh
     * @date 2022-04-05 10:19
     */
    List<StorageInfoUrlModel> storageBatchUrl(StorageModel model);
}
