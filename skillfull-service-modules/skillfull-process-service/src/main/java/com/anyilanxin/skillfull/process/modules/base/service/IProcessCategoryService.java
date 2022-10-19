// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service;

import com.anyilanxin.skillfull.corecommon.model.common.SelectModel;
import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryPageDto;

import java.util.List;
import java.util.Set;

/**
 * 流程类别(ProcessCategory)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
public interface IProcessCategoryService extends BaseService<ProcessCategoryEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ProcessCategoryVo} 流程类别保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    void save(ProcessCategoryVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link ProcessCategoryVo} 流程类别更新
     * @param categoryId ${@link String} 类别id
     * @param vo         ${@link ProcessCategoryVo} 流程类别更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    void updateById(String categoryId, ProcessCategoryVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ProcessCategoryPageVo} 流程类别分页查询Vo
     * @return PageDto<ProcessCategoryPageDto> ${@link PageDto< ProcessCategoryPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    PageDto<ProcessCategoryPageDto> pageByModel(ProcessCategoryPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ProcessCategoryQueryVo} 流程类别条件查询Vo
     * @return List<ProcessCategoryDto> ${@link List< ProcessCategoryDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo) throws RuntimeException;


    /**
     * 通过编码查询所有类表
     *
     * @param categoryCodes ${@link Set<String>}
     * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
     * @author zxiaozhou
     * @date 2021-11-19 10:58
     */
    List<ProcessCategoryDto> selectListByCodes(Set<String> categoryCodes);


    /**
     * 通过编码查询类表
     *
     * @param categoryCode ${@link String}
     * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
     * @author zxiaozhou
     * @date 2021-11-19 10:58
     */
    ProcessCategoryDto selectByCode(String categoryCode);


    /**
     * 通过id查询详情
     *
     * @param categoryId ${@link String} 类别id
     * @return ProcessCategoryDto ${@link ProcessCategoryDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    ProcessCategoryDto getById(String categoryId) throws RuntimeException;


    /**
     * 通过categoryId删除
     *
     * @param categoryId ${@link String} 类别id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    void deleteById(String categoryId) throws RuntimeException;


    /**
     * 获取建模流程类别下拉列表
     *
     * @return List<SelectModel>
     * @author zxiaozhou
     * @date 2022-10-19 07:41
     */
    List<SelectModel> getModelDesignList();
}
