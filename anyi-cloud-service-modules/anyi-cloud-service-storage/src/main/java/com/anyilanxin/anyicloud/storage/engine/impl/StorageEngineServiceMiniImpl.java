

package com.anyilanxin.skillfull.storage.engine.impl;

import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageModel;
import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.engine.EngineCondition;
import com.anyilanxin.skillfull.storage.engine.IStorageEngineService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * minio存储
 *
 * @author zxh
 * @date 2022-03-30 19:54
 * @since 1.0.0
 */
@Component(value = StorageTypeConstant.MINIO_STORAGE)
@Conditional(EngineCondition.class)
public class StorageEngineServiceMiniImpl implements IStorageEngineService {

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
