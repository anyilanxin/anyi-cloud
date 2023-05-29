

package com.anyilanxin.anyicloud.system.modules.common.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonCategoryEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryTreeDto;

import java.util.List;

/**
 * 分类字典表(CommonCategory)业务层接口
 *
 * @author zxh
 * @date 2021-01-07 23:40:21
 * @since 1.0.0
 */
public interface ICommonCategoryService extends BaseService<CommonCategoryEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonCategoryVo} 分类字典表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    void save(CommonCategoryVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link CommonCategoryVo} 分类字典表更新
     * @param categoryId ${@link String} 分类id
     * @param vo         ${@link CommonCategoryVo} 分类字典表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    void updateById(String categoryId, CommonCategoryVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonCategoryPageVo} 分类字典表分页查询Vo
     * @return PageDto<CommonCategoryPageDto> ${@link PageDto< CommonCategoryPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    PageDto<CommonCategoryPageDto> pageByModel(CommonCategoryPageVo vo) throws RuntimeException;


    /**
     * 根据统一分类id查询分类
     *
     * @param categoryCommonCode ${@link String} 统一分类编码
     * @return List<CommonCategoryDto> ${@link List< CommonCategoryDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    List<CommonCategoryDto> selectListByCommonCode(String categoryCommonCode) throws RuntimeException;


    /**
     * 根据统一分类id查询分类(树形)
     *
     * @param categoryCommonCode ${@link String} 统一分类编码
     * @return List<CommonCategoryTreeDto> ${@link List< CommonCategoryTreeDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    List<CommonCategoryTreeDto> selectTreeListByCommonCode(String categoryCommonCode) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param categoryId ${@link String} 分类id
     * @return CommonCategoryDto ${@link CommonCategoryDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    CommonCategoryDto getById(String categoryId) throws RuntimeException;


    /**
     * 通过categoryId删除
     *
     * @param categoryId ${@link String} 分类id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-08-28 12:20
     */
    void deleteById(String categoryId) throws RuntimeException;


    /**
     * 分类字典表批量删除
     *
     * @param categoryIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> categoryIds) throws RuntimeException;


    /**
     * 分类查询下级
     *
     * @param parentId ${@link String} 上级id
     * @return List<CommonCategoryPageDto> ${@link List<CommonCategoryPageDto> }
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-08 00:01
     */
    List<CommonCategoryPageDto> selectPageChildren(String parentId) throws RuntimeException;


    /**
     * 查询所有分类编码
     *
     * @return List<CommonCategoryTreeDto> ${@link List<CommonCategoryTreeDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-03-12 07:44
     */
    List<CommonCategoryTreeDto> selectAllTree() throws RuntimeException;
}
