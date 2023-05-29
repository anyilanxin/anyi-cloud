

package com.anyilanxin.skillfull.storage.modules.storage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 本地文件服务(StorageInfoFile)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-05 09:57:59
 * @since 1.0.0
 */
@Repository
public interface StorageInfoFileMapper extends BaseMapper<StorageInfoFileEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link StorageInfoFilePageVo} 查询条件
     * @param page ${@link Page<StorageInfoFilePageDto>} 分页信息
     * @return IPage<StorageInfoFilePageDto> ${@link IPage<StorageInfoFilePageDto>} 结果
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    IPage<StorageInfoFilePageDto> pageByModel(Page<StorageInfoFilePageDto> page, @Param("query") StorageInfoFilePageVo vo);


    /**
     * 通过文件id物理删除
     *
     * @param fileId ${@link String} 文件id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    int physicalDeleteById(@Param("id") String fileId);


    /**
     * 通过文件id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-04-05 09:57:59
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
