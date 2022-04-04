// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.local.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonDateUtils;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.storage.core.config.properties.LocalFileProperty;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFileVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.mapper.LocalConfigMapper;
import indi.zxiaozhou.skillfull.storage.modules.local.mapper.LocalFileMapper;
import indi.zxiaozhou.skillfull.storage.modules.local.service.ILocalFileService;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalEnableConfigDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileUploadBatchDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.mapstruct.LocalFileDtoMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.SLASH;
import static indi.zxiaozhou.skillfull.storage.core.constant.CommonStorageConstant.LOCAL_FILE;
import static indi.zxiaozhou.skillfull.storage.core.constant.CommonStorageConstant.LOCAL_FILE_PREVIEW;

/**
 * 本地文件服务(LocalFile)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-10-23 14:37:34
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class LocalFileServiceImpl extends ServiceImpl<LocalFileMapper, LocalFileEntity> implements ILocalFileService {
    private final LocalFileDtoMap dtoMap;
    private final LocalFileProperty fileProperty;
    private final LocalFileMapper mapper;
    private final Snowflake snowflake;
    //    private final DataSourceTransactionManager dataSourceTransactionManager;
//    private final TransactionDefinition transactionDefinition;
    private final LocalConfigMapper configMapper;
    @Value("${spring.webflux.base-path:}")
    private String basePath;


    @Override
    public Mono<LocalFileDto> upload(Mono<FilePart> file, final String fileFolder, ServerHttpRequest request) throws RuntimeException {
        LocalEnableConfigDto enableConfigOne = configMapper.getEnableConfigOne();
        if (Objects.isNull(enableConfigOne)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未获取有效的配置信息");
        }
        return file.map(filePart -> {
            // 获取文件基本信息
            String fileOriginalName = filePart.filename();
            String fileType = "";
            if (StringUtils.isNotBlank(fileOriginalName)) {
                fileOriginalName = filePart.filename().substring(0, filePart.filename().lastIndexOf("."));
                fileType = filePart.filename().substring(filePart.filename().lastIndexOf("."));
            }
            String contentType = "";
            MediaType mediaType = filePart.headers().getContentType();
            long contentLength = request.getHeaders().getContentLength();
            if (Objects.nonNull(mediaType)) {
                contentType = mediaType.getType() + SLASH + mediaType.getSubtype();
            }
            // 处理文件保存文件夹
            String fileDiskRelativePath = SLASH + CoreCommonDateUtils.getNowStrDate(CoreCommonDateUtils.YYYYMMDD);
            String localFileFolder = fileFolder;
            if (StringUtils.isNotBlank(localFileFolder)) {
                if (!localFileFolder.startsWith(SLASH)) {
                    localFileFolder = SLASH + localFileFolder;
                }
                if (localFileFolder.endsWith(SLASH)) {
                    localFileFolder = localFileFolder.substring(0, localFileFolder.length() - 1);
                }
                fileDiskRelativePath += localFileFolder;
            }
            // 文件存储
            String saveBootDiskPath = enableConfigOne.getSaveBootDiskPath();
            if (saveBootDiskPath.endsWith(SLASH)) {
                saveBootDiskPath = saveBootDiskPath.substring(0, saveBootDiskPath.length() - 1);
            }
            String saveFileFolder = saveBootDiskPath + SLASH + fileDiskRelativePath;
            String fileName = snowflake.nextIdStr();
            // 创建文件保存路径
            AsynchronousFileChannel channel;
            try {
                Path path = Paths.get(saveFileFolder);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                // 存储文件
                Path filePath = Files.createFile(Paths.get(saveFileFolder + SLASH + fileName + fileType));
                channel = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
            } catch (IOException e) {
                log.error("------------LocalFileServiceImpl------------>upload--->异常消息:{}", e.getMessage());
                throw new ResponseException(Status.ERROR, "文件存储失败:" + e.getMessage());
            }
            DataBufferUtils.write(filePart.content(), channel, 0).doOnComplete(() -> {
                try {
                    channel.close();
                } catch (IOException e) {
                    log.error("------------LocalFileServiceImpl------------>upload--->异常消息:{}", e.getMessage());
                    throw new ResponseException(Status.ERROR, "文件存储失败:" + e.getMessage());
                }
            }).subscribe();
            // 文件信息
            return LocalFileEntity.builder()
                    .fileName(fileName)
                    .contentType(contentType)
                    .fileSizeDetail(contentLength)
                    .fileSize(CoreCommonUtils.getFormatFileSize(contentLength))
                    .fileOriginalName(fileOriginalName)
                    .fileDiskRelativePath(fileDiskRelativePath + SLASH + fileName + fileType)
                    .fileType(fileType)
                    .localConfigId(enableConfigOne.getLocalConfigId())
                    .build();
        }).flatMap(localFile -> {
            boolean save = this.save(localFile);
            if (!save) {
                return Mono.error(new ResponseException(Status.DATABASE_BASE_ERROR, "存储文件信息失败"));
            }
            LocalFileDto dto = dtoMap.dToE(localFile);
            dto.setPreviewPath(basePath + LOCAL_FILE + LOCAL_FILE_PREVIEW + enableConfigOne.getUnifiedPrefix() + dto.getFileDiskRelativePath());
            dtoMap.updateVToE(enableConfigOne, dto);
            return Mono.just(dto);
        });
    }


    @Override
    public LocalFileDto uploadUrlOne(LocalFileVo vo) throws RuntimeException {
        return null;
    }


    @Override
    public LocalFileUploadBatchDto uploadUrlBatch(List<LocalFileVo> vos) throws RuntimeException {
        return null;
    }


    @Override
    public Mono<Void> downloadByPreview(String path, ServerHttpResponse response, boolean isShow) {
        String previewPath = path.replaceFirst(basePath, "").replaceFirst(LOCAL_FILE, "").replaceFirst(LOCAL_FILE_PREVIEW, "");
        log.info("------------LocalFileServiceImpl------previewPath------>downloadByPreview:{}", previewPath);
        LocalFileDto byId = mapper.getFileByPreviewPath(previewPath);

        return sendFile(byId, isShow, response);
    }

    /**
     * 发送文件
     *
     * @param byId     ${@link LocalFileDto}
     * @param isShow   ${@link Boolean} 是否显示操作(显示时会设置content-type)
     * @param response ${@link ServerHttpResponse}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-07-22 11:42
     */
    private Mono<Void> sendFile(LocalFileDto byId, boolean isShow, ServerHttpResponse response) {
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "文件不存在");
        }
        File file = new File(byId.getSaveBootDiskPath() + byId.getFileDiskRelativePath());
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        if (StringUtils.isNotBlank(byId.getContentType())) {
            response.getHeaders().set("Content-Type", byId.getContentType());
        } else {
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        if (!isShow) {
            ContentDisposition contentDisposition = ContentDisposition.attachment().filename(byId.getFileOriginalName() + byId.getFileType(), StandardCharsets.UTF_8).build();
            response.getHeaders().setContentDisposition(contentDisposition);
        }
        response.getHeaders().setContentLength(file.length());
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }


    @Override
    public Mono<Void> downloadById(String localFileId, ServerHttpResponse response, boolean isShow) {
        LocalFileDto byId = mapper.getFileById(localFileId);

        return sendFile(byId, isShow, response);
    }

    @Override
    public PageDto<LocalFilePageDto> pageByModel(LocalFilePageVo vo) throws RuntimeException {
        IPage<LocalFilePageDto> localFilePageDtoIPage = mapper.pageByModel(vo.getPage(), vo, basePath + LOCAL_FILE + LOCAL_FILE_PREVIEW);

        return new PageDto<>(localFilePageDtoIPage);
    }


    @Override
    public LocalFileDto getById(String localFileId) throws RuntimeException {
        LocalFileEntity byId = super.getById(localFileId);
        if (Objects.isNull(byId)) {

            throw new ResponseException(Status.DATABASE_BASE_ERROR, "文件信息不存在");
        }
        LocalFileDto fileDto = mapper.getFileById(localFileId);
        dtoMap.updateDToE(byId, fileDto);
        fileDto.setPreviewPath(basePath + LOCAL_FILE + LOCAL_FILE_PREVIEW + fileDto.getUnifiedPrefix() + fileDto.getFileDiskRelativePath());

        return fileDto;
    }


    @Override
    public void deleteById(String localFileId) throws RuntimeException {
        // 查询数据是否存在
        LocalFileDto byId = this.getById(localFileId);
        // 删除文件数据库信息
        int delete = mapper.physicalDeleteById(localFileId);
        if (delete <= 0) {

            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除文件数据失败");
        }
        // 查看是否是最后一个文件，如果是则删除磁盘文件
        LambdaQueryWrapper<LocalFileEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LocalFileEntity::getFileMd5, byId.getFileMd5());
        List<LocalFileEntity> list = this.list(lambdaQueryWrapper);

        if (list.size() == 1) {
            File file = new File(fileProperty.getUploadFolder() + SLASH + byId.getFileDiskRelativePath());
            if (file.exists() && file.isFile()) {
                boolean fileDelete = file.delete();
                if (!fileDelete) {
                    throw new ResponseException(Status.ERROR, "删除文件失败");
                }
            }
        }
    }


    @Override
    public void deleteBatch(List<String> localFileIds) throws RuntimeException {
        List<LocalFileEntity> localFileEntities = this.listByIds(localFileIds);
        if (CollectionUtil.isEmpty(localFileEntities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "文件不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        localFileEntities.forEach(v -> waitDeleteList.add(v.getLocalFileId()));
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