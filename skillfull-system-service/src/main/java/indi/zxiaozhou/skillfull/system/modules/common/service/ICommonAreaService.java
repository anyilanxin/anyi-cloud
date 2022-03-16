// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonAreaPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonAreaVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonAreaEntity;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonAreaDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonAreaPageDto;

import java.util.List;

/**
 * 区域表(CommonArea)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:04
 * @since JDK11
 */
public interface ICommonAreaService extends BaseService<CommonAreaEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonAreaVo} 区域表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:04
     */
    void save(CommonAreaVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link CommonAreaVo} 区域表更新
     * @param areaId ${@link String} 区域id
     * @param vo     ${@link CommonAreaVo} 区域表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:04
     */
    void updateById(String areaId, CommonAreaVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonAreaPageVo} 区域表分页查询Vo
     * @return PageDto<CommonAreaPageDto> ${@link PageDto<CommonAreaPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:04
     */
    PageDto<CommonAreaPageDto> pageByModel(CommonAreaPageVo vo) throws RuntimeException;


    /**
     * 区域查询下级
     *
     * @param parentId ${@link String} 上级区域id
     * @return List<CommonAreaPageDto> ${@link  List<CommonAreaPageDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-07 20:13
     */
    List<CommonAreaPageDto> selectPageChildren(String parentId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param parentId ${@link String} 上级区域id
     * @return List<CommonAreaDto> ${@link List<CommonAreaDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:04
     */
    List<CommonAreaDto> selectList(String parentId) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param areaId ${@link String} 区域id
     * @return CommonAreaDto ${@link CommonAreaDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:04
     */
    CommonAreaDto getById(String areaId) throws RuntimeException;


    /**
     * 通过areaId删除
     *
     * @param areaId ${@link String} 区域id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String areaId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param areaIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> areaIds) throws RuntimeException;
}