// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgUserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 机构-用户(RbacOrgUser)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Repository
public interface RbacOrgUserMapper extends BaseMapper<RbacOrgUserEntity> {
    /**
     * 查询用户关联的机构id
     *
     * @param userId 用户id
     * @return List<RbacOrgUserDto> 查询结果
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    List<RbacOrgUserDto> selectUserOrgListByUserId(@Param("userId") String userId);


    /**
     * 通过机构用户id物理删除
     *
     * @param orgUserId 机构用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteById(@Param("id") String orgUserId);


    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteByUserId(@Param("id") String userId);


    /**
     * 通过机构用户id物理批量删除
     *
     * @param idList 机构用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
