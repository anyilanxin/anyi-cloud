// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage.modules.storage.service.impl;

import com.anyilanxin.skillfull.storage.core.EngineCondition;
import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.modules.storage.service.IStorageEngineService;
import com.anyilanxin.skillfull.storageapi.model.StorageInfoModel;
import com.anyilanxin.skillfull.storageapi.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storageapi.model.StorageModel;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
