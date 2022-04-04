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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateRoleDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;

import java.util.List;

/**
 * 角色关联关系表(RbacCorrelateRole)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:37:26
 * @since JDK11
 */
public interface IRbacCorrelateRoleService extends BaseService<RbacCorrelateRoleEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacCorrelateRoleVo} 角色关联关系表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:37:26
     */
    void save(RbacCorrelateRoleVo vo) throws RuntimeException;


    /**
     * 更新角色关联
     *
     * @param vo ${@link RbacCorrelateRoleVo} 角色关联关系表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:37:26
     */
    void updateRoleCorrelate(RbacCorrelateRoleVo vo) throws RuntimeException;


    /**
     * 删除角色权限关联
     *
     * @param correlateId ${@link String} 管理关系id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-03-09 09:47
     */
    void deleteRolePermission(String correlateId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacCorrelateRoleQueryVo} 角色关联关系表条件查询Vo
     * @return List<RbacCorrelateRoleDto> ${@link List<RbacCorrelateRoleDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-11-02 09:37:26
     */
    List<RbacCorrelateRoleDto> selectListByModel(RbacCorrelateRoleQueryVo vo) throws RuntimeException;
}