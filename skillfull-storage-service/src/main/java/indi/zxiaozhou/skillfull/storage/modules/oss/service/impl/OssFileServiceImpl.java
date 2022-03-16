// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.storage.core.config.properties.OssProperty;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFileVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.entity.OssFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.oss.mapper.OssFileMapper;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.IOssFileService;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileUploadBatchDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.mapstruct.OssFileDtoMap;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.mapstruct.OssFileUploadSuccessMap;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.mapstruct.OssFileVoUploadFailMap;
import indi.zxiaozhou.skillfull.storage.utils.AliOssFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.POINT;
import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.SLASH;

/**
 * oss文件(OssFile)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-10-23 12:13:31
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class OssFileServiceImpl extends ServiceImpl<OssFileMapper, OssFileEntity> implements IOssFileService {
    private final OssFileDtoMap dtoMap;
    private final OssFileUploadSuccessMap uploadSuccessMap;
    private final OssFileMapper mapper;
    private final OssProperty ossProperty;
    private final Snowflake snowflake;
    //    private final DataSourceTransactionManager dataSourceTransactionManager;
//    private final TransactionDefinition transactionDefinition;
    private final OssFileVoUploadFailMap uploadFailMap;


    @Override
    public OssFileDto upload(MultipartFile file, String fileFolder) throws RuntimeException {
        // 获取文件基本信息并补全存放文件信息
        long fileSizeDetail = file.getSize();
        String fileMd5;
        try {
            fileMd5 = CoreCommonUtils.getFileMd5Hex(file.getInputStream());
        } catch (IOException e) {
            log.error("------------OssFileServiceImpl------获取文件md5值失败------>upload:{}", e.getMessage());
            throw new ResponseException(Status.ERROR, "获取文件md5值失败:" + e.getMessage());
        }
        String fileOriginalName;
        String fileSize = CoreCommonUtils.getFormatFileSize(fileSizeDetail);
        String fileType;
        String fileOriginalFullName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileOriginalFullName)) {
            fileOriginalName = fileOriginalFullName.substring(0, fileOriginalFullName.lastIndexOf("."));
            fileType = fileOriginalFullName.substring(fileOriginalFullName.lastIndexOf("."));
        } else {
            throw new ResponseException(Status.ERROR, "未获取到正确的文件信息");
        }
        String fileFullName = snowflake.nextIdStr() + fileType;
        String ossKey = fileFullName;
        // 处理文件保存文件夹
        String fileDirPrefix = "";
        if (StringUtils.isNotBlank(fileFolder)) {
            if (fileFolder.startsWith(SLASH)) {
                fileFolder = fileFolder.replaceFirst(SLASH, "");
            }
            if (fileFolder.endsWith(SLASH)) {
                fileFolder = fileFolder.substring(0, fileFolder.length() - 1);
            }
            fileDirPrefix = fileFolder;
            ossKey = fileFolder + SLASH + ossKey;
        }
        OSSClient ossClient = AliOssFileUtils.createOssClient();
        // 文件上传到oss
        String eTag;
        try {
            ObjectMetadata meta = new ObjectMetadata();
            String filename = URLDecoder.decode(fileOriginalFullName, String.valueOf(StandardCharsets.UTF_8));
            meta.setContentDisposition("attachment; filename=" + filename);
            PutObjectResult putObjectResult = ossClient.putObject(ossProperty.getBucket(), ossKey, file.getInputStream(), meta);
            eTag = putObjectResult.getETag();
        } catch (IOException e) {
            throw new ResponseException(Status.API_ERROR, "上传文件到oss失败:" + e.getMessage());
        } finally {
            ossClient.shutdown();
        }

        // 构建入库文件信息并存入数据库
        OssFileEntity entity = new OssFileEntity();
        entity.setFileOriginalName(fileOriginalName);
        entity.setFileOriginalFullName(fileOriginalFullName);
        entity.setFileSize(fileSize);
        entity.setFileType(fileType);
        entity.setFileMd5(fileMd5);
        entity.setOssTag(eTag);
        entity.setFileSizeDetail(fileSizeDetail);
        entity.setFileFullName(fileFullName);
        entity.setEndpoint(ossProperty.getEndpoint());
        entity.setBucket(ossProperty.getBucket());
        entity.setFileDirPrefix(fileDirPrefix);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
        return dtoMap.bToA(entity);
    }


    @Override
    public OssFileDto uploadUrlOne(OssFileVo vo) throws RuntimeException {
        // 创建链接并判断文件是否能下载
        HttpURLConnection con;
        try {
            URL url = new URL(vo.getUrl());
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(60 * 1000);
            // 创建浏览器模拟信息,避免三方网站拒绝处理导致403
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116");
            int responseCode = con.getResponseCode();
            if (responseCode != HttpStatus.OK.value()) {
                log.error("------------OssFileServiceImpl------------>uploadUrlOne:{}", con.getResponseMessage());
                throw new ResponseException(Status.ERROR, "下载文件失败(" + vo.getUrl() + "):" + con.getResponseMessage());
            }
        } catch (IOException e) {
            log.error("------------OssFileServiceImpl------下载文件失败------>uploadUrlOne:{}", "(" + vo.getUrl() + ")" + e.getMessage());
            throw new ResponseException(Status.ERROR, "下载文件失败(" + vo.getUrl() + "):" + e.getMessage());
        }

        // 获取文件信息(首先从传入参数中获取文件名,不存在则从地址中解析，再从请求头解析)
        String fileOriginalName = "";
        String fileType = "";
        if (StringUtils.isNotBlank(vo.getFileOriginalFullName())) {
            fileOriginalName = vo.getFileOriginalFullName();
            if (fileOriginalName.contains(POINT)) {
                fileType = fileOriginalName.split("\\.")[1];
            }
        } else if (vo.getUrl().contains(POINT)) {
            String[] split = vo.getUrl().split(SLASH);
            fileOriginalName = split[split.length - 1];
            fileType = POINT + fileOriginalName.split("\\.")[1];
        } else {
            String headerField = con.getHeaderField("Content-disposition");
            if (StringUtils.isNotBlank(headerField)) {
                fileOriginalName = headerField.replace("attachment;fileOriginalName=", "");
                try {
                    fileOriginalName = URLDecoder.decode(fileOriginalName, String.valueOf(StandardCharsets.UTF_8));
                    if (fileOriginalName.contains(POINT)) {
                        fileType = POINT + fileOriginalName.split("\\.")[1];
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    log.error("------------OssFileServiceImpl------请求头Content-disposition文件信息url解码失败------>uploadUrlOne:{}", e.getMessage());
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "请求头Content-disposition文件信息url解码失败:" + e.getMessage());
                }
            }
        }

        // 处理文件存放文件夹以及存放文件
        String fileFolder = StringUtils.isBlank(vo.getFileFolder()) ? "" : vo.getFileFolder();
        String fileFullName = snowflake.nextIdStr() + fileType;
        String ossKey = fileFullName;
        if (StringUtils.isNotBlank(fileFolder)) {
            if (fileFolder.endsWith(SLASH)) {
                fileFolder = fileFolder.substring(0, fileFolder.length() - 1);
            }
            if (fileFolder.startsWith(SLASH)) {
                fileFolder = fileFolder.replaceFirst(SLASH, "");
            }
            ossKey = fileFolder + SLASH + ossKey;
        }

        // 计算文件md5以及文件大小
        String fileMd5;
        long fileSizeDetail;
        String fileSize;
        byte[] bytes;
        try {
            bytes = con.getInputStream().readAllBytes();
            fileMd5 = CoreCommonUtils.getFileMd5Hex(new ByteArrayInputStream(bytes));
            fileSizeDetail = bytes.length;
            fileSize = CoreCommonUtils.getFormatFileSize(fileSizeDetail);
        } catch (IOException e) {
            log.error("------------OssFileServiceImpl------获取文件md5值失败------>upload:{}", e.getMessage());
            throw new ResponseException(Status.ERROR, "获取文件md5值失败:" + e.getMessage());
        }

        // 文件上传到oss
        OSSClient ossClient = AliOssFileUtils.createOssClient();
        String eTag;
        try {
            ObjectMetadata meta = new ObjectMetadata();
            String filename = URLDecoder.decode(fileOriginalName, String.valueOf(StandardCharsets.UTF_8));
            meta.setContentDisposition("attachment; filename=" + filename);
            PutObjectResult putObjectResult = ossClient.putObject(ossProperty.getBucket(), ossKey, new ByteArrayInputStream(bytes), meta);
            eTag = putObjectResult.getETag();
        } catch (IOException e) {
            throw new ResponseException(Status.API_ERROR, "上传文件到oss失败:" + e.getMessage());
        } finally {
            ossClient.shutdown();
        }

        // 构建文件信息
        OssFileEntity entity = new OssFileEntity();
        entity.setFileOriginalFullName(fileOriginalName + fileType);
        entity.setFileType(fileType);
        entity.setFileOriginalName(fileOriginalName);
        entity.setFileFullName(fileFullName);
        entity.setFileDirPrefix(fileFolder);
        entity.setFileMd5(fileMd5);
        entity.setOssTag(eTag);
        entity.setFileSizeDetail(fileSizeDetail);
        entity.setFileSize(fileSize);
        entity.setEndpoint(ossProperty.getEndpoint());
        entity.setBucket(ossProperty.getBucket());
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
        return dtoMap.bToA(entity);
    }


    @Override
    public OssFileUploadBatchDto uploadUrlBatch(List<OssFileVo> vos) throws RuntimeException {
        List<OssFileUploadBatchDto.UploadSuccess> successList = new ArrayList<>();
        List<OssFileUploadBatchDto.UploadFail> failFailList = new ArrayList<>();
        // 结果类型:0-全部成功,1-部分失败,2-全部失败
        int type = 0;
        for (OssFileVo vo : vos) {
            try {
                OssFileDto localFileDto = this.uploadUrlOne(vo);
                OssFileUploadBatchDto.UploadSuccess uploadSuccess = uploadSuccessMap.aToB(localFileDto);
                uploadSuccess.setCallback(vo.getCallback());
                successList.add(uploadSuccess);
            } catch (Exception e) {
                log.error("------------OssFileServiceImpl------------>uploadUrlBatch:{}", e.getMessage());
                e.printStackTrace();
                type = 1;
                OssFileUploadBatchDto.UploadFail uploadFail = uploadFailMap.aToB(vo);
                uploadFail.setFailCause(e.getMessage());
                failFailList.add(uploadFail);
            }
        }
        if (CollectionUtil.isEmpty(successList)) {
            type = 2;
        }
        OssFileUploadBatchDto dto = new OssFileUploadBatchDto();
        dto.setType(type);
        dto.setFailFailList(failFailList);
        dto.setSuccessList(successList);
        return dto;
    }


    @Override
    public String getAccessUrlById(String ossFileId) throws RuntimeException {
        OssFileDto byId = this.getById(ossFileId, true);
        return byId.getOssAccessUrl();
    }


    @Override
    public PageDto<OssFilePageDto> pageByModel(OssFilePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    public OssFileDto getById(String ossFileId, boolean getUrl) throws RuntimeException {
        OssFileEntity byId = super.getById(ossFileId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        OssFileDto ossFileDto = dtoMap.bToA(byId);
        if (getUrl) {
            OSSClient ossClient = AliOssFileUtils.createOssClient(byId.getEndpoint());
            try {
                Date expiration = new Date(System.currentTimeMillis() + ossProperty.getAccessExpireTime() * 1000);
                String ossKey = byId.getFileFullName();
                if (StringUtils.isNotBlank(byId.getFileDirPrefix())) {
                    ossKey = byId.getFileDirPrefix() + SLASH + ossKey;
                }
                ossFileDto.setOssAccessUrl(ossClient.generatePresignedUrl(byId.getBucket(), ossKey, expiration).toString());
            } catch (Exception e) {
                log.error("------------OssFileServiceImpl------获取图片地址失败------>getById:{}", e.getMessage());
                throw new ResponseException(Status.API_ERROR, "获取图片地址失败:" + e.getMessage());
            } finally {
                ossClient.shutdown();
            }

        }
        return ossFileDto;
    }


    @Override
    public void deleteById(String ossFileId) throws RuntimeException {
        // 查询数据是否存在
        OssFileDto byId = this.getById(ossFileId, false);
        // 删除本地文件信息
        int delete = mapper.physicalDeleteById(byId.getOssFileId());
        if (delete <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除文件数据失败");
        }
        // 删除oss文件
        OSSClient ossClient = AliOssFileUtils.createOssClient(byId.getEndpoint());
        String ossKey = byId.getFileFullName();
        if (StringUtils.isNotBlank(byId.getFileDirPrefix())) {
            ossKey = byId.getFileDirPrefix() + SLASH + ossKey;
        }
        try {
            // 判断文件是否存在，如果存在则执行删除
            boolean found = ossClient.doesObjectExist(byId.getBucket(), ossKey);
            if (found) {
                ossClient.deleteObject(byId.getBucket(), ossKey);
            }
        } catch (Exception e) {
            throw new ResponseException(Status.API_ERROR, "删除oss文件失败");
        } finally {
            ossClient.shutdown();
        }
    }


    @Override
    public void deleteBatch(List<String> ossFileIds) throws RuntimeException {
        List<OssFileEntity> entities = this.listByIds(ossFileIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "文件不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getOssFileId()));
//        waitDeleteList.forEach(v -> {
//            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
//            try {
//                this.deleteById(v);
//            } catch (Exception e) {
//                dataSourceTransactionManager.rollback(transactionStatus);
//                throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除发生了异常:" + e.getMessage());
//            }
//            dataSourceTransactionManager.commit(transactionStatus);
//        });
    }
}