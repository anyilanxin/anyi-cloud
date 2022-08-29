// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage.modules.storage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageInfoUrlModel;
import com.anyilanxin.skillfull.storagerpc.model.StorageModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 本地文件服务(StorageInfoFile)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-05 09:57:59
 * @since JDK1.8
 */
public interface IStorageInfoFileService extends BaseService<StorageInfoFileEntity> {
    /**
     * 分页查询
     *
     * @param vo ${@link StorageInfoFilePageVo} 本地文件服务分页查询Vo
     * @return PageDto<StorageInfoFilePageDto> ${@link PageDto< StorageInfoFilePageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-05 09:57:59
     */
    PageDto<StorageInfoFilePageDto> pageByModel(StorageInfoFilePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param fileId ${@link String} 文件id
     * @return StorageInfoModel ${@link StorageInfoModel} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-05 09:57:59
     */
    StorageInfoModel getById(String fileId) throws RuntimeException;


    /**
     * 通过fileId删除
     *
     * @param fileId ${@link String} 文件id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-05 09:57:59
     */
    void deleteById(String fileId) throws RuntimeException;


    /**
     * 本地文件服务批量删除
     *
     * @param fileIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-05 09:57:59
     */
    void deleteBatch(List<String> fileIds) throws RuntimeException;

    /**
     * 单个存储
     *
     * @param file          ${@link MultipartFile}
     * @param fileDirPrefix ${@link String}
     * @param request       ${@link HttpServletRequest}
     * @return StorageInfoModel ${@link StorageInfoModel}
     * @author zxiaozhou
     * @date 2022-04-05 10:19
     */
    StorageInfoModel storage(MultipartFile file, String fileDirPrefix, HttpServletRequest request);


    /**
     * 批量存储
     *
     * @param fileDirPrefix ${@link String}
     * @param request       ${@link HttpServletRequest}
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
     * @return List<StorageInfoUrlModel> ${@link List< StorageInfoUrlModel >}
     * @author zxiaozhou
     * @date 2022-04-05 10:19
     */
    List<StorageInfoUrlModel> storageBatchUrl(StorageModel model);
}
