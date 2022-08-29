// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacPositionPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacPositionVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacPositionEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacPositionDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacPositionPageDto;

import java.util.List;

/**
 * 职位表(RbacPosition)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
public interface IRbacPositionService extends BaseService<RbacPositionEntity> {
    /**
     * 保存
     *
     * @param vo 职位表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void save(RbacPositionVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param positionId 职位id
     * @param vo         职位表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void updateById(String positionId, RbacPositionVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacPositionPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    PageDto<RbacPositionPageDto> pageByModel(RbacPositionPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @return List<RbacPositionDto>  查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    List<RbacPositionDto> getAllList() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param positionId 职位id
     * @return RbacPositionDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    RbacPositionDto getById(String positionId) throws RuntimeException;


    /**
     * 通过positionId删除
     *
     * @param positionId 职位id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteById(String positionId) throws RuntimeException;


    /**
     * 职位表批量删除
     *
     * @param positionIds 职位id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> positionIds) throws RuntimeException;


    /**
     * 修改职位状态
     *
     * @param positionId ${@link String} 职位id
     * @param type       ${@link Integer} 类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-30 00:39
     */
    void updatePositionState(String positionId, Integer type) throws RuntimeException;
}
