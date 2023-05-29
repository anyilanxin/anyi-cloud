

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacJoinOrgVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 机构-用户(RbacOrgUser)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgUserServiceImpl extends ServiceImpl<RbacOrgUserMapper, RbacOrgUserEntity> implements IRbacOrgUserService {
    private final RbacOrgUserMapper mapper;
    private final IRbacOrgRoleUserService orgRoleUserService;

    @Override
    public void joinOrg(RbacJoinOrgVo vo) {
        if (CollUtil.isNotEmpty(vo.getUserIds())) {
            List<RbacOrgUserEntity> orgUserEntities = new ArrayList<>(vo.getUserIds().size());
            vo.getUserIds().forEach(v -> {
                RbacOrgUserEntity userEntity = RbacOrgUserEntity.builder().orgId(vo.getOrgId()).userId(v).build();
                orgUserEntities.add(userEntity);
            });
            boolean b = this.saveBatch(orgUserEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存用户机构关联失败");
            }
        }
    }


    @Override
    public void removeOrg(String userId, String orgId) {
        // 删除机构用户关联
        LambdaQueryWrapper<RbacOrgUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacOrgUserEntity::getUserId, userId).eq(RbacOrgUserEntity::getOrgId, orgId);
        RbacOrgUserEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户未关联当前机构");
        }
        mapper.physicalDeleteById(one.getOrgUserId());
        // 删除机构用户角色关联
        orgRoleUserService.deleteByUserId(userId, orgId);
    }
}
