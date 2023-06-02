/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.skillfull.storage.engine.impl;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.SLASH;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonDateUtils;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.anyicloud.storagerpc.model.StorageModel;
import com.anyilanxin.skillfull.storage.core.config.properties.LocalFileProperty;
import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import com.anyilanxin.skillfull.storage.core.constant.impl.StorageType;
import com.anyilanxin.skillfull.storage.engine.EngineCondition;
import com.anyilanxin.skillfull.storage.engine.IStorageEngineService;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.mapper.StorageInfoFileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地存储
 *
 * @author zxh
 * @date 2022-03-30 19:54
 * @since 1.0.0
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
        String fileType = FileNameUtil.extName(fileOriginalFullName);
        if (StringUtils.isBlank(fileType)) {
            fileType = "";
        } else {
            fileType = "." + fileType;
        }
        // 处理文件保存文件夹
        String fileDiskRelativePathFolder = SLASH + CoreCommonDateUtils.getNowStrDate(CoreCommonDateUtils.YYYYMMDD);
        if (StringUtils.isNotBlank(fileDirPrefix)) {
            if (!fileDirPrefix.startsWith(SLASH)) {
                fileDirPrefix = SLASH + fileDirPrefix;
            }
            if (fileDirPrefix.endsWith(SLASH)) {
                fileDirPrefix = StringUtils.removeEnd(fileDirPrefix, "/");
            }
            fileDiskRelativePathFolder = fileDiskRelativePathFolder + fileDirPrefix;
        }
        StorageInfoModel model = StorageInfoModel.builder().contentType(file.getContentType()).fileMd5(fileMd5).fileDirPrefix(fileDirPrefix).fileId(snowflake.nextIdStr()).fileSize(fileSize).fileOriginalName(fileOriginalFullName).fileType(fileType).fileStorageType(StorageType.LOCAL.getType()).fileSizeDetail(file.getSize()).fileRelativePath(fileDiskRelativePathFolder + SLASH + snowflake.nextIdStr() + fileType).build();
        // 查看文件是否已经存储(如果存在则使用已经存在的文件信息)
        LambdaQueryWrapper<StorageInfoFileEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StorageInfoFileEntity::getFileMd5, model.getFileMd5()).eq(StorageInfoFileEntity::getFileStorageType, StorageType.LOCAL.getType());
        List<StorageInfoFileEntity> storageInfoFileEntities = fileMapper.selectList(lambdaQueryWrapper);
        boolean isHave = false;
        if (CollUtil.isNotEmpty(storageInfoFileEntities)) {
            StorageInfoFileEntity storageInfoFileEntity = storageInfoFileEntities.get(0);
            model = model.toBuilder().fileRelativePath(storageInfoFileEntity.getFileRelativePath()).build();
            isHave = true;
        }
        // 存放文件
        if (!isHave) {
            // 创建文件存放文件夹
            String uploadFolder = property.getUploadFolder();
            if (uploadFolder.endsWith("/")) {
                uploadFolder = StringUtils.removeEnd(uploadFolder, "/");
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
            model.setFileRelativePath(property.getVirtualMapping() + model.getFileRelativePath() + "?original_name=" + model.getFileOriginalName());
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
