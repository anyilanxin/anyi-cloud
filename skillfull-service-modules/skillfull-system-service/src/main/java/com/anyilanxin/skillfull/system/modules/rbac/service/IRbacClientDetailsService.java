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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacClientDetailsEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsPageDto;

import java.util.List;

/**
 * 授权客户端信息(RbacClientDetails)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacClientDetailsService extends BaseService<RbacClientDetailsEntity> {
    /**
     * 保存
     *
     * @param vo 授权客户端信息保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacClientDetailsVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param clientDetailId 客户端信息id
     * @param vo             授权客户端信息更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void updateById(String clientDetailId, RbacClientDetailsVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacClientDetailsPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacClientDetailsPageDto> pageByModel(RbacClientDetailsPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param clientDetailId 客户端信息id
     * @return RbacClientDetailsDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacClientDetailsDto getById(String clientDetailId) throws RuntimeException;


    /**
     * 通过clientDetailId删除
     *
     * @param clientDetailId 客户端信息id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String clientDetailId) throws RuntimeException;


    /**
     * 授权客户端信息批量删除
     *
     * @param clientDetailIds 客户端信息id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> clientDetailIds) throws RuntimeException;


    /**
     * 通过客户端id修改状态
     *
     * @param clientDetailId 客户端信息id
     * @param type           类型:0-禁用,1-启用
     * @author zxiaozhou
     * @date 2022-06-03 02:32
     */
    void updateState(String clientDetailId, Integer type);


    /**
     * 更新或添加权限
     *
     * @param clientDetailId
     * @param vo
     * @author zxiaozhou
     * @date 2022-07-10 11:29
     */
    void updateAuth(String clientDetailId, RbacClientAuthVo vo);
}
