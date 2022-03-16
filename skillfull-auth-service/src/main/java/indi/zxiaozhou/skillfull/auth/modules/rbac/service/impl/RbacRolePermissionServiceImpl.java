// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRolePermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacRoleMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacRolePermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacRolePermissionService;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.SysBaseType;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色-权限表(RbacRolePermission)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:29:16
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacRolePermissionServiceImpl extends ServiceImpl<RbacRolePermissionMapper, RbacRolePermissionEntity> implements IRbacRolePermissionService {
    private final RbacRolePermissionMapper mapper;
    private final RbacRoleMapper rbacRoleMapper;


    @Override
    public void save(RbacRolePermissionVo vo) throws RuntimeException {
        String roleId = vo.getRoleId();
        // 判断是否为超级管理员
        RbacRoleEntity rbacRoleEntity = rbacRoleMapper.selectById(roleId);
        if (rbacRoleEntity.getRoleCode().equals(SysBaseType.SUPER_ROLE.getType())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "超级管理员角色拥有所有权限,不需要授权");
        }
        if (CollectionUtil.isNotEmpty(vo.getPermissionIds())) {
            List<RbacRolePermissionEntity> rolePermissionEntityList = new ArrayList<>();
            vo.getPermissionIds().forEach(v -> {
                RbacRolePermissionEntity entity = new RbacRolePermissionEntity();
                entity.setRoleId(roleId);
                entity.setPermissionId(v);
                rolePermissionEntityList.add(entity);
            });
            boolean b = this.saveBatch(rolePermissionEntityList);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色权限关联信息失败");
            }
        }
    }


    @Override
    public void updatePermission(RbacRolePermissionVo vo) throws RuntimeException {
        this.deleteRolePermission(vo.getRoleId());
        this.save(vo);
    }


    @Override
    public void deleteRolePermission(String roleId) throws RuntimeException {
        LambdaQueryWrapper<RbacRolePermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacRolePermissionEntity::getRoleId, roleId);
        List<RbacRolePermissionEntity> rbacRolePermissionEntities = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(rbacRolePermissionEntities)) {
            int i = mapper.physicalDeleteById(roleId);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除角色关联信息失败");
            }
        }
    }
}