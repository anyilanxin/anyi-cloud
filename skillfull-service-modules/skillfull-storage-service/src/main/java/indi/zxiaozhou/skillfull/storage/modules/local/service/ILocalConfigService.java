package indi.zxiaozhou.skillfull.storage.modules.local.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalConfigVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalConfigEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalConfigDto;

import java.util.List;

/**
 * 本地文件配置(LocalConfig)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
public interface ILocalConfigService extends BaseService<LocalConfigEntity> {
    /**
     * 保存
     *
     * @param vo ${@link LocalConfigVo} 本地文件配置保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    void save(LocalConfigVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo            ${@link LocalConfigVo} 本地文件配置更新
     * @param localConfigId ${@link String} 本地配置文件id
     * @param vo            ${@link LocalConfigVo} 本地文件配置更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    void updateById(String localConfigId, LocalConfigVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @return List<LocalConfigDto> ${@link List<LocalConfigDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    List<LocalConfigDto> selectListByModel() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param localConfigId ${@link String} 本地配置文件id
     * @return LocalConfigDto ${@link LocalConfigDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    LocalConfigDto getById(String localConfigId) throws RuntimeException;


    /**
     * 通过localConfigId删除
     *
     * @param localConfigId ${@link String} 本地配置文件id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-21 16:10:05
     */
    void deleteById(String localConfigId) throws RuntimeException;
}
