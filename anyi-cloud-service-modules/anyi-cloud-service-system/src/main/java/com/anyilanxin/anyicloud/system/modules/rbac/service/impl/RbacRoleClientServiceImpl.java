

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleClientEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleClientMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleClientService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleClientCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleClientPageCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleClientQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色-客户端(RbacRoleClient)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
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
                RbacRoleClientEntity entity = RbacRoleClientEntity.builder().clientDetailId(clientDetailId).roleId(v).build();
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
            LambdaQueryWrapper<RbacRoleClientEntity> lambdaQueryWrapper = Wrappers.<RbacRoleClientEntity>lambdaQuery().in(RbacRoleClientEntity::getClientDetailId, clientDetailIds);
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
