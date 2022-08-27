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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonDateUtils;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.storage.core.EngineCondition;
import com.anyilanxin.skillfull.storage.core.config.properties.LocalFileProperty;
import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.core.constant.impl.StorageType;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.mapper.StorageInfoFileMapper;
import com.anyilanxin.skillfull.storage.modules.storage.service.IStorageEngineService;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.SLASH;

/**
 * 本地存储
 *
 * @author zxiaozhou
 * @date 2022-03-30 19:54
 * @since JDK1.8
 */
@Component(value = StorageTypeConstant.LOCAL_STORAGE)
@Slf4j
@Conditional(EngineCondition.class)
@RequiredArgsConstructor
public class StorageEngineServiceLocalImpl implements IStorageEngineService {
    private final LocalFileProperty property;
    private final Snowflake snowflake;
    private final StorageInfoFileMapper fileMapper;


    @Override
    public StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request) {
        // 获取文件基本信息并补全存放文件信息
        String fileOriginalFullName = file.getOriginalFilename();
        long fileSizeDetail = file.getSize();
        String fileMd5;
        try {
            fileMd5 = CoreCommonUtils.getFileMd5Hex(file.getInputStream());
        } catch (IOException e) {
            log.error("------------LocalFileServiceImpl------获取文件md5值失败------>upload:{}", e.getMessage());
            throw new ResponseException(Status.ERROR, "获取文件md5值失败:" + e.getMessage());
        }
        String fileSize = CoreCommonUtils.getFormatFileSize(fileSizeDetail);
        String fileType = StringUtils.getFilenameExtension(fileOriginalFullName);
        if (!StringUtils.hasText(fileType)) {
            fileType = "";
        } else {
            fileType = "." + fileType;
        }
        // 处理文件保存文件夹
        String fileDiskRelativePathFolder = SLASH + CoreCommonDateUtils.getNowStrDate(CoreCommonDateUtils.YYYYMMDD);
        if (StringUtils.hasText(fileDirPrefix)) {
            if (!fileDirPrefix.startsWith(SLASH)) {
                fileDirPrefix = SLASH + fileDirPrefix;
            }
            if (fileDirPrefix.endsWith(SLASH)) {
                fileDirPrefix = org.apache.commons.lang3.StringUtils.removeEnd(fileDirPrefix, "/");
            }
            fileDiskRelativePathFolder = fileDiskRelativePathFolder + fileDirPrefix;
        }
        StorageInfoModel model = StorageInfoModel.builder()
                .contentType(file.getContentType())
                .fileMd5(fileMd5)
                .fileDirPrefix(fileDirPrefix)
                .fileId(snowflake.nextIdStr())
                .fileSize(fileSize)
                .fileOriginalName(fileOriginalFullName)
                .fileType(fileType)
                .fileStorageType(StorageType.LOCAL.getType())
                .fileSizeDetail(file.getSize())
                .fileRelativePath(fileDiskRelativePathFolder + SLASH + snowflake.nextIdStr() + fileType)
                .build();
        // 查看文件是否已经存储(如果存在则使用已经存在的文件信息)
        LambdaQueryWrapper<StorageInfoFileEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StorageInfoFileEntity::getFileMd5, model.getFileMd5())
                .eq(StorageInfoFileEntity::getFileStorageType, StorageType.LOCAL.getType());
        List<StorageInfoFileEntity> storageInfoFileEntities = fileMapper.selectList(lambdaQueryWrapper);
        boolean isHave = false;
        if (CollUtil.isNotEmpty(storageInfoFileEntities)) {
            StorageInfoFileEntity storageInfoFileEntity = storageInfoFileEntities.get(0);
            model = model.toBuilder()
                    .fileRelativePath(storageInfoFileEntity.getFileRelativePath())
                    .build();
            isHave = true;
        }
        // 存放文件
        if (!isHave) {
            // 创建文件存放文件夹
            String uploadFolder = property.getUploadFolder();
            if (uploadFolder.endsWith("/")) {
                uploadFolder = org.apache.commons.lang3.StringUtils.removeEnd(uploadFolder, "/");
            }
            File localFileFolder = new File(uploadFolder + fileDiskRelativePathFolder);
            if (!localFileFolder.exists() || !localFileFolder.isDirectory()) {
                boolean mkdirs = localFileFolder.mkdirs();
                if (!mkdirs) {
                    log.error("------------LocalFileServiceImpl------------>upload:{}", "创建文件存放路径失败");
                    throw new ResponseException(Status.ERROR, "创建文件存放路径失败");
                }
            }
            // 创建存放文件
            File localFile = new File(uploadFolder + model.getFileRelativePath());
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                log.error("------------LocalFileServiceImpl------存放文件到本地失败------>upload:{}", e.getMessage());
                throw new ResponseException(Status.ERROR, "存放文件到本地失败:" + e.getMessage());
            }
            model.setFileRelativePath(property.getVirtualMapping() + model.getFileRelativePath());
        }
        return model;
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
