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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacRolePageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色表(RbacRole)持久层
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:44:02
 * @since JDK11
 */
@Repository
public interface RbacRoleMapper extends BaseMapper<RbacRoleEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacRolePageVo} 查询条件
     * @param page ${@link Page<RbacRolePageDto>} 分页信息
     * @return IPage<RbacRolePageDto> ${@link IPage<RbacRolePageDto>} 结果
     * @author zxiaozhou
     * @date 2020-10-08 13:44:02
     */
    IPage<RbacRolePageDto> pageByModel(Page<RbacRolePageDto> page, @Param("query") RbacRolePageVo vo);
    

    /**
     * 通过角色id物理删除
     *
     * @param roleId ${@link String} 角色id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String roleId);
}