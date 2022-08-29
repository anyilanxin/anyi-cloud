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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacEnalbeUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserPageDto;

import java.util.List;

/**
 * 用户表(RbacUser)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacUserService extends BaseService<RbacUserEntity> {
    /**
     * 保存
     *
     * @param vo 用户表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacUserVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param userId 用户id
     * @param vo     用户表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void updateById(String userId, RbacUserVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacUserPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacUserPageDto> pageByModel(RbacUserPageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return RbacUserDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacUserDto getById(String userId, String orgId) throws RuntimeException;


    /**
     * 通过userId删除
     *
     * @param userId 用户id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String userId) throws RuntimeException;


    /**
     * 用户表批量删除
     *
     * @param userIds 用户id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> userIds) throws RuntimeException;


    /**
     * 通过用户d修改状态
     *
     * @param userId 用户id
     * @param type   类型:1-激活,2-冻结
     * @author zxiaozhou
     * @date 2022-06-03 02:35
     */
    void updateState(String userId, Integer type);


    /**
     * 重置密码
     *
     * @param userId 用户id
     * @return String 新密码
     * @author zxiaozhou
     * @date 2022-06-03 02:38
     */
    String resetPassword(String userId);


    /**
     * 分页查询可关联的用户信息
     *
     * @param vo
     * @return PageDto<RbacUserPageDto>
     * @author zxiaozhou
     * @date 2022-08-02 15:44
     */
    PageDto<RbacUserPageDto> selectEnableUserPage(RbacEnalbeUserPageVo vo);

}
