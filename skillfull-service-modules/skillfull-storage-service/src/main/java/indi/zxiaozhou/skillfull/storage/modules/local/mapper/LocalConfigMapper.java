package indi.zxiaozhou.skillfull.storage.modules.local.mapper;

import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalConfigEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalEnableConfigDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 本地文件配置(LocalConfig)持久层
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Repository
public interface LocalConfigMapper extends BaseMapper<LocalConfigEntity> {
    /**
     * 通过本地配置文件id物理删除
     *
     * @param localConfigId ${@link String} 本地配置文件id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    int physicalDeleteById(@Param("id") String localConfigId);


    /**
     * 通过本地配置文件id物理删除
     *
     * @return LocalEnableConfigDto ${@link LocalEnableConfigDto} 配置信息
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    LocalEnableConfigDto getEnableConfigOne();


    /**
     * 通过本地配置文件id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
