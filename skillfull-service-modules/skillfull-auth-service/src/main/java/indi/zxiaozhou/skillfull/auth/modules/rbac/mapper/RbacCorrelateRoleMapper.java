// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.mapper;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateRoleDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色关联关系表(RbacCorrelateRole)持久层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:37:26
 * @since JDK11
 */
@Repository
public interface RbacCorrelateRoleMapper extends BaseMapper<RbacCorrelateRoleEntity> {


    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacCorrelateRoleQueryVo} 查询条件
     * @return List<RbacCorrelateRoleDto> ${@link List<RbacCorrelateRoleDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:37:26
     */
    List<RbacCorrelateRoleDto> selectListByModel(RbacCorrelateRoleQueryVo vo);


    /**
     * 通过组织id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 通过角色关联关系id物理删除
     *
     * @param correlateRoleId ${@link String} 角色关联关系id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String correlateRoleId);
}