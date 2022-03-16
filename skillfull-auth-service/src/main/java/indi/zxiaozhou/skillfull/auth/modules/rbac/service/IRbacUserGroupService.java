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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserGroupEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 用户组(RbacUserGroup)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:13:08
 * @since JDK1.8
 */
public interface IRbacUserGroupService extends BaseService<RbacUserGroupEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacUserGroupVo} 用户组保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    void save(RbacUserGroupVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo          ${@link RbacUserGroupVo} 用户组更新
     * @param userGroupId ${@link String} 用户组id
     * @param vo          ${@link RbacUserGroupVo} 用户组更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    void updateById(String userGroupId, RbacUserGroupVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacUserGroupPageVo} 用户组分页查询Vo
     * @return PageDto<RbacUserGroupPageDto> ${@link PageDto<RbacUserGroupPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    PageDto<RbacUserGroupPageDto> pageByModel(RbacUserGroupPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacUserGroupQueryVo} 用户组条件查询Vo
     * @return List<RbacUserGroupDto> ${@link List<RbacUserGroupDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    List<RbacUserGroupDto> selectListByModel(RbacUserGroupQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param userGroupId ${@link String} 用户组id
     * @return RbacUserGroupDto ${@link RbacUserGroupDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    RbacUserGroupDto getById(String userGroupId) throws RuntimeException;


    /**
     * 通过userGroupId删除
     *
     * @param userGroupId ${@link String} 用户组id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    void deleteById(String userGroupId) throws RuntimeException;


    /**
     * 用户组批量删除
     *
     * @param userGroupIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    void deleteBatch(List<String> userGroupIds) throws RuntimeException;
}