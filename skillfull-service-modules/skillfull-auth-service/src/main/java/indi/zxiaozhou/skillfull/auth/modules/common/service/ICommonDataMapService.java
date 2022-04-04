// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonDataMapEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 数据映射表(CommonDataMap)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-07-28 11:53:28
 * @since JDK1.8
 */
public interface ICommonDataMapService extends BaseService<CommonDataMapEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonDataMapVo} 数据映射表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    void save(CommonDataMapVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo        ${@link CommonDataMapVo} 数据映射表更新
     * @param dataMapId ${@link String} 数据映射id
     * @param vo        ${@link CommonDataMapVo} 数据映射表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    void updateById(String dataMapId, CommonDataMapVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonDataMapPageVo} 数据映射表分页查询Vo
     * @return PageDto<CommonDataMapPageDto> ${@link PageDto<CommonDataMapPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    PageDto<CommonDataMapPageDto> pageByModel(CommonDataMapPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link CommonDataMapQueryVo} 数据映射表条件查询Vo
     * @return List<CommonDataMapDto> ${@link List<CommonDataMapDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    List<CommonDataMapDto> selectListByModel(CommonDataMapQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param dataMapId ${@link String} 数据映射id
     * @throws RuntimeException ${@link RuntimeException}
     * @return CommonDataMapDto ${@link CommonDataMapDto} 查询结果
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    CommonDataMapDto getById(String dataMapId) throws RuntimeException;


    /**
     * 通过dataMapId删除
     *
     * @param dataMapId ${@link String} 数据映射id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    void deleteById(String dataMapId) throws RuntimeException;


    /**
     * 数据映射表批量删除
     *
     * @param dataMapIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    void deleteBatch(List<String> dataMapIds) throws RuntimeException;
}
