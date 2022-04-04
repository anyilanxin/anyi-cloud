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
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonDictItemPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonDictItemVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonDictItemEntity;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonDictItemDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonDictItemPageDto;

import java.util.List;

/**
 * 数据字典配置项表(CommonDictItem)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:26
 * @since JDK11
 */
public interface ICommonDictItemService extends BaseService<CommonDictItemEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonDictItemVo} 数据字典配置项表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:26
     */
    void save(CommonDictItemVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link CommonDictItemVo} 数据字典配置项表更新
     * @param itemId ${@link String} 字典项id
     * @param vo     ${@link CommonDictItemVo} 数据字典配置项表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:26
     */
    void updateById(String itemId, CommonDictItemVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonDictItemPageVo} 数据字典配置项表分页查询Vo
     * @return PageDto<CommonDictItemPageDto> ${@link PageDto<CommonDictItemPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:26
     */
    PageDto<CommonDictItemPageDto> pageByModel(CommonDictItemPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param dictCode ${@link String} 字典编码
     * @return List<CommonDictItemDto> ${@link List<CommonDictItemDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:18
     */
    List<CommonDictItemDto> selectListByCode(String dictCode) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param itemId ${@link String} 字典项id
     * @return CommonDictItemDto ${@link CommonDictItemDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:25:26
     */
    CommonDictItemDto getById(String itemId) throws RuntimeException;


    /**
     * 通过itemId删除
     *
     * @param itemId ${@link String} 字典项id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String itemId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param itemIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> itemIds) throws RuntimeException;


    /**
     * 修改字典项状态
     *
     * @param itemId ${@link String} 字典项id
     * @param type   ${@link Integer} 操作类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-05 17:38
     */
    void updateDictItemState(String itemId, Integer type) throws RuntimeException;
}