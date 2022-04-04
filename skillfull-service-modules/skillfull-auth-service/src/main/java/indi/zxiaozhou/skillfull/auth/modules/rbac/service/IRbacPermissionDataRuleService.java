// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRulePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRuleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRuleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionDataRuleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRuleDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRulePageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 权限数据填值规则表(RbacPermissionDataRule)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:41:28
 * @since JDK11
 */
public interface IRbacPermissionDataRuleService extends BaseService<RbacPermissionDataRuleEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacPermissionDataRuleVo} 权限数据填值规则表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    void save(RbacPermissionDataRuleVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo                   ${@link RbacPermissionDataRuleVo} 权限数据填值规则表更新
     * @param permissionDataRuleId ${@link String} 填值规则id
     * @param vo                   ${@link RbacPermissionDataRuleVo} 权限数据填值规则表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    void updateById(String permissionDataRuleId, RbacPermissionDataRuleVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacPermissionDataRulePageVo} 权限数据填值规则表分页查询Vo
     * @return PageDto<RbacPermissionDataRulePageDto> ${@link PageDto<RbacPermissionDataRulePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    PageDto<RbacPermissionDataRulePageDto> pageByModel(RbacPermissionDataRulePageVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacPermissionDataRulePageVo} 权限数据填值规则表分页查询Vo
     * @return PageDto<RbacPermissionDataRulePageDto> ${@link PageDto<RbacPermissionDataRulePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    PageDto<RbacPermissionDataRulePageDto> page(RbacPermissionDataRulePageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacPermissionDataRuleQueryVo} 权限数据填值规则表条件查询Vo
     * @return List<RbacPermissionDataRuleDto> ${@link List<RbacPermissionDataRuleDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    List<RbacPermissionDataRuleDto> selectListByModel(RbacPermissionDataRuleQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param permissionDataRuleId ${@link String} 填值规则id
     * @return RbacPermissionDataRuleDto ${@link RbacPermissionDataRuleDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:41:28
     */
    RbacPermissionDataRuleDto getById(String permissionDataRuleId) throws RuntimeException;


    /**
     * 通过permissionDataRuleId删除
     *
     * @param permissionDataRuleId ${@link String} 填值规则id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String permissionDataRuleId) throws RuntimeException;


    /**
     * 文件批量删除
     *
     * @param permissionDataRuleIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> permissionDataRuleIds) throws RuntimeException;
}