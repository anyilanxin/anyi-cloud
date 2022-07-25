// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleClientEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleClientMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacRoleClientService;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacRoleClientCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacRoleClientPageCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacRoleClientQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleClient)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleClientServiceImpl extends ServiceImpl<RbacRoleClientMapper, RbacRoleClientEntity> implements IRbacRoleClientService {
    private final RbacRoleClientCopyMap map;
    private final RbacRoleClientPageCopyMap pageMap;
    private final RbacRoleClientQueryCopyMap queryMap;
    private final RbacRoleClientMapper mapper;


    @Override
    public void saveBatch(String clientDetailId, Set<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            List<RbacRoleClientEntity> roleClientEntities = new ArrayList<>(roleIds.size());
            roleIds.forEach(v -> {
                RbacRoleClientEntity entity = RbacRoleClientEntity.builder()
                        .clientDetailId(clientDetailId)
                        .roleId(v)
                        .build();
                roleClientEntities.add(entity);
            });
            boolean b = this.saveBatch(roleClientEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存客户端角色关联失败");
            }
        }
    }


    @Override
    public void deleteBatch(List<String> clientDetailIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(clientDetailIds)) {
            LambdaQueryWrapper<RbacRoleClientEntity> lambdaQueryWrapper = Wrappers.<RbacRoleClientEntity>lambdaQuery()
                    .in(RbacRoleClientEntity::getClientDetailId, clientDetailIds);
            List<RbacRoleClientEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> roleClientIds = new HashSet<>(list.size());
                list.forEach(v -> roleClientIds.add(v.getRoleClient()));
                int i = mapper.physicalDeleteBatchIds(roleClientIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
