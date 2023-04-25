package com.anyilanxin.skillfull.storage.modules.storage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.storage.engine.IStorageEngineService;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.mapper.StorageInfoFileMapper;
import com.anyilanxin.skillfull.storage.modules.storage.service.IStorageInfoFileService;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.anyilanxin.skillfull.storage.modules.storage.service.mapstruct.StorageInfoFileCopyMap;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 本地文件服务(StorageInfoFile)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-05 09:57:59
 * @since JDK1.8
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
    public PageDto<StorageInfoFilePageDto> pageByModel(StorageInfoFilePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public StorageInfoModel getById(String fileId) throws RuntimeException {
        StorageInfoFileEntity byId = super.getById(fileId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
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
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> fileIds) throws RuntimeException {
        List<StorageInfoFileEntity> entities = this.listByIds(fileIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getFileId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
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
     * @author zxiaozhou
     * @date 2022-04-05 11:18
     */
    void saveBatch(List<StorageInfoModel> models) {
        if (CollUtil.isNotEmpty(models)) {
            List<StorageInfoFileEntity> storageInfoFileEntities = map.dToE(models);
            boolean b = this.saveBatch(storageInfoFileEntities);
            if (!b) {
                throw new ResponseException("存储文件失败");
            }
        }
    }
}
