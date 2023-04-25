package com.anyilanxin.skillfull.storage.engine.impl;

import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.engine.EngineCondition;
import com.anyilanxin.skillfull.storage.engine.IStorageEngineService;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ali oss存储
 *
 * @author zxiaozhou
 * @date 2022-03-30 19:54
 * @since JDK1.8
 */
@Component(value = StorageTypeConstant.ALI_OSS_STORAGE)
@Conditional(EngineCondition.class)
public class StorageEngineServiceAliOssImpl implements IStorageEngineService {

    @Override
    public StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<StorageInfoModel> storageBatch(List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<StorageInfoUrlModel> storageBatchUrl(StorageModel model) {
        return null;
    }
}
