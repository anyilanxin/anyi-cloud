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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 权限表(Permission)持久层
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:14
 * @since JDK11
 */
@Repository
public interface RbacPermissionMapper extends BaseMapper<RbacPermissionEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacPermissionPageVo} 查询条件
     * @param page ${@link Page< RbacPermissionPageDto >} 分页信息
     * @return IPage<RbacPermissionPageDto> ${@link IPage< RbacPermissionPageDto >} 结果
     * @author zxiaozhou
     * @date 2020-09-26 17:16:14
     */
    IPage<RbacPermissionPageDto> pageByModel(Page<RbacPermissionPageDto> page, @Param("query") RbacPermissionPageVo vo);


    /**
     * 通过权限id物理删除
     *
     * @param permissionId ${@link String} 权限id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String permissionId);
}