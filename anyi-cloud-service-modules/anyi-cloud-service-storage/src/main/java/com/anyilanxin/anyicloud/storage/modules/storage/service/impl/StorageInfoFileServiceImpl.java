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

package com.anyilanxin.anyicloud.storage.modules.storage.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.storage.engine.IStorageEngineService;
import com.anyilanxin.anyicloud.storage.modules.storage.controller.vo.StorageInfoFilePageQuery;
import com.anyilanxin.anyicloud.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.anyicloud.storage.modules.storage.mapper.StorageInfoFileMapper;
import com.anyilanxin.anyicloud.storage.modules.storage.service.IStorageInfoFileService;
import com.anyilanxin.anyicloud.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.anyilanxin.anyicloud.storage.modules.storage.service.mapstruct.StorageInfoFileCopyMap;
import com.anyilanxin.anyicloud.storageadapter.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storageadapter.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storageadapter.model.StorageModel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 本地文件服务(StorageInfoFile)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-05 09:57:59
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageInfoFileServiceImpl extends ServiceImpl<StorageInfoFileMapper, StorageInfoFileEntity> implements IStorageInfoFileService {
    private final StorageInfoFileCopyMap map;
    private final StorageInfoFileMapper mapper;
    private final IStorageEngineService storageEngine;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<StorageInfoFilePageDto> pageByModel(StorageInfoFilePageQuery vo) throws RuntimeException {
        return PageUtils.toPageData(mapper.pageByModel(PageUtils.getPage(vo), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public StorageInfoModel getById(String fileId) throws RuntimeException {
        StorageInfoFileEntity byId = super.getById(fileId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String fileId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(fileId);
        // 删除数据
        boolean b = this.removeById(fileId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> fileIds) throws RuntimeException {
        List<StorageInfoFileEntity> entities = this.listByIds(fileIds);
        if (CollUtil.isEmpty(entities)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getFileId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request) {
        StorageInfoModel storage = storageEngine.storage(file, fileDirPrefix, request);
        saveBatch(List.of(storage));
        return storage;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public List<StorageInfoModel> storageBatch(List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request) {
        List<StorageInfoModel> storageInfoModels = storageEngine.storageBatch(files, fileDirPrefix, request);
        saveBatch(storageInfoModels);
        return storageInfoModels;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public List<StorageInfoUrlModel> storageBatchUrl(StorageModel model) {
        List<StorageInfoUrlModel> storageInfoUrlModels = storageEngine.storageBatchUrl(model);
        List<StorageInfoModel> models = new ArrayList<>(storageInfoUrlModels.size());
        storageInfoUrlModels.forEach(v -> {
            if (Objects.nonNull(v.getStatus()) && v.getStatus() == 1) {
                models.add(v);
            }
        });
        saveBatch(models);
        return storageInfoUrlModels;
    }


    /**
     * 数据存入数据库
     *
     * @param models ${@link List<StorageInfoModel>}
     * @author zxh
     * @date 2022-04-05 11:18
     */
    void saveBatch(List<StorageInfoModel> models) {
        if (CollUtil.isNotEmpty(models)) {
            List<StorageInfoFileEntity> storageInfoFileEntities = map.dToE(models);
            boolean b = this.saveBatch(storageInfoFileEntities);
            if (!b) {
                throw new AnYiResponseException("存储文件失败");
            }
        }
    }
}
