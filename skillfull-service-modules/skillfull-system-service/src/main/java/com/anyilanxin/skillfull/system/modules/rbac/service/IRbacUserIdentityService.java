// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserIdentityEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityPageDto;

import java.util.List;

/**
 * 实名信息表(RbacUserIdentity)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacUserIdentityService extends BaseService<RbacUserIdentityEntity> {
    /**
     * 保存
     *
     * @param vo 实名信息表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacUserIdentityVo vo) throws RuntimeException;


    /**
     * 实名审核
     *
     * @param identityId 实名信息id
     * @param vo         实名信息表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void audit(String identityId, RbacUserIdentityVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacUserIdentityPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacUserIdentityPageDto> pageByModel(RbacUserIdentityPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param identityId 实名信息id
     * @return RbacUserIdentityDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacUserIdentityDto getById(String identityId) throws RuntimeException;


    /**
     * 通过identityId删除
     *
     * @param identityId 实名信息id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String identityId) throws RuntimeException;


    /**
     * 实名信息表批量删除
     *
     * @param identityIds 实名信息id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> identityIds) throws RuntimeException;
}
