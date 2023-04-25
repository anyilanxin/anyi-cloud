package com.anyilanxin.skillfull.storage.engine;

import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param file          ${@link MultipartFile}
     * @return StorageInfoModel ${@link StorageInfoModel}
     * @author zxiaozhou
     * @date 2022-04-05 10:19
     */
    StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request);


    /**
     * 批量存储
     *
     * @param fileDirPrefix ${@link String}
     * @param files         ${@link List<MultipartFile>}
     * @return List<StorageInfoModel> ${@link List<StorageInfoModel>}
     * @author zxiaozhou
     * @date 2022-04-05 10:19
     */
    List<StorageInfoModel> storageBatch(List<MultipartFile> files, String fileDirPrefix, HttpServletRequest request);


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
