// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFileVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.entity.OssFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFilePageDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileUploadBatchDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * oss文件(OssFile)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-10-23 12:13:31
 * @since JDK11
 */
public interface IOssFileService extends BaseService<OssFileEntity> {
    /**
     * 保存
     *
     * @param file       ${@link MultipartFile} 待上传文件
     * @param fileFolder ${@link String} 存储文件文件夹名称
     * @return OssFileDto ${@link OssFileDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    OssFileDto upload(MultipartFile file, String fileFolder) throws RuntimeException;


    /**
     * 文件上传
     *
     * @param vo ${@link OssFileVo} 待上传文件信息
     * @return OssFileDto ${@link OssFileDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    OssFileDto uploadUrlOne(OssFileVo vo) throws RuntimeException;


    /**
     * 文件上传
     *
     * @param vos ${@link List<OssFileVo>} 待上传文件信息
     * @return OssFileUploadBatchDto ${@link OssFileUploadBatchDto} 上传结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-22 23:36:35
     */
    OssFileUploadBatchDto uploadUrlBatch(List<OssFileVo> vos) throws RuntimeException;


    /**
     * 获取文件访问地址
     *
     * @param ossFileId ${@link String} oss文件id
     * @return String ${@link String}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-26 10:45
     */
    String getAccessUrlById(String ossFileId) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link OssFilePageVo} oss文件分页查询Vo
     * @return PageDto<OssFilePageDto> ${@link PageDto<OssFilePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-23 12:13:31
     */
    PageDto<OssFilePageDto> pageByModel(OssFilePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param ossFileId ${@link String} 文件id
     * @param getUrl    ${@link String} 是否获取访问url
     * @return OssFileDto ${@link OssFileDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-23 12:13:31
     */
    OssFileDto getById(String ossFileId, boolean getUrl) throws RuntimeException;


    /**
     * 通过ossFileId删除
     *
     * @param ossFileId ${@link String} 文件id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String ossFileId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param ossFileIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> ossFileIds) throws RuntimeException;
}