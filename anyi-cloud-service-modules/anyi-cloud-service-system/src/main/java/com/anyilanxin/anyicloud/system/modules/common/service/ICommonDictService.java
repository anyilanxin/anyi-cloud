

package com.anyilanxin.anyicloud.system.modules.common.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictPageDto;

import java.util.List;

/**
 * 数据字典表(CommonDict)业务层接口
 *
 * @author zxh
 * @date 2020-11-02 09:25:18
 * @since 1.0.0
 */
public interface ICommonDictService extends BaseService<CommonDictEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonDictVo} 数据字典表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-11-02 09:25:18
     */
    void save(CommonDictVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link CommonDictVo} 数据字典表更新
     * @param dictId ${@link String} 字典id
     * @param vo     ${@link CommonDictVo} 数据字典表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-11-02 09:25:18
     */
    void updateById(String dictId, CommonDictVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonDictPageVo} 数据字典表分页查询Vo
     * @return PageDto<CommonDictPageDto> ${@link PageDto< CommonDictPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-11-02 09:25:18
     */
    PageDto<CommonDictPageDto> pageByModel(CommonDictPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param dictId ${@link String} 字典id
     * @return CommonDictDto ${@link CommonDictDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-11-02 09:25:18
     */
    CommonDictDto getById(String dictId) throws RuntimeException;


    /**
     * 通过dictId删除
     *
     * @param dictId ${@link String} 字典id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-08-28 12:20
     */
    void deleteById(String dictId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param dictIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> dictIds) throws RuntimeException;


    /**
     * 修改字典状态
     *
     * @param dictId ${@link String} 字典id
     * @param type   ${@link Integer} 操作类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-11-05 17:38
     */
    void updateDictState(String dictId, Integer type) throws RuntimeException;
}
