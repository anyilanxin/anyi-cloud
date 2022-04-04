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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRulePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRuleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionDataRuleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRuleDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRulePageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限数据填值规则表(RbacPermissionDataRule)持久层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:41:27
 * @since JDK11
 */
@Repository
public interface RbacPermissionDataRuleMapper extends BaseMapper<RbacPermissionDataRuleEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacPermissionDataRulePageVo} 查询条件
     * @param page ${@link Page<RbacPermissionDataRulePageDto>} 分页信息
     * @return IPage<RbacPermissionDataRulePageDto> ${@link IPage<RbacPermissionDataRulePageDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:41:27
     */
    IPage<RbacPermissionDataRulePageDto> pageByModel(Page<RbacPermissionDataRulePageDto> page, @Param("query") RbacPermissionDataRulePageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacPermissionDataRuleQueryVo} 查询条件
     * @return List<RbacPermissionDataRuleDto> ${@link List<RbacPermissionDataRuleDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:41:27
     */
    List<RbacPermissionDataRuleDto> selectListByModel(RbacPermissionDataRuleQueryVo vo);


    /**
     * 通过填值规则id物理删除
     *
     * @param permissionDataRuleId ${@link String} 填值规则id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String permissionDataRuleId);
}