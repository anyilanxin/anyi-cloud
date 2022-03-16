// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.local.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFileVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileUploadBatchDto;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 本地文件服务(LocalFile)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-10-23 14:37:33
 * @since JDK11
 */
public interface ILocalFileService extends BaseService<LocalFileEntity> {
    /**
     * 文件上传
     *
     * @param fileFolder ${@link String} 存储文件文件夹名称
     * @return LocalFileDto ${@link LocalFileDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    Mono<LocalFileDto> upload(Mono<FilePart> file, String fileFolder, ServerHttpRequest request) throws RuntimeException;

    /**
     * 文件上传
     *
     * @param vo ${@link LocalFileVo} 待上传文件信息
     * @return LocalFileDto ${@link LocalFileDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    LocalFileDto uploadUrlOne(LocalFileVo vo) throws RuntimeException;


    /**
     * 文件上传
     *
     * @param vos ${@link List<LocalFileVo>} 待上传文件信息
     * @return OssFileUploadBatchDto ${@link LocalFileUploadBatchDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    LocalFileUploadBatchDto uploadUrlBatch(List<LocalFileVo> vos) throws RuntimeException;


    /**
     * 文件下载
     *
     * @param localFileId ${@link String} 文件id
     * @param response    ${@link ServerHttpResponse}
     * @author zxiaozhou
     * @date 2020-10-23 14:33
     */
    Mono<Void> downloadById(String localFileId, ServerHttpResponse response, boolean isShow);


    /**
     * 文件下载
     *
     * @param path     ${@link String} 前端访问地址
     * @param response ${@link ServerHttpResponse}
     * @author zxiaozhou
     * @date 2020-10-23 14:33
     */
    Mono<Void> downloadByPreview(String path, ServerHttpResponse response, boolean isShow);


    /**
     * 分页查询
     *
     * @param vo ${@link LocalFilePageVo} 本地文件服务分页查询Vo
     * @return PageDto<LocalFilePageDto> ${@link PageDto<LocalFilePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-23 14:37:33
     */
    PageDto<LocalFilePageDto> pageByModel(LocalFilePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param localFileId ${@link String} 文件id
     * @return LocalFileDto ${@link LocalFileDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-23 14:37:33
     */
    LocalFileDto getById(String localFileId) throws RuntimeException;


    /**
     * 通过localFileId删除
     *
     * @param localFileId ${@link String} 文件id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String localFileId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param localFileIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> localFileIds) throws RuntimeException;
}