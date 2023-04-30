/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserPageCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserQueryCopyMap;
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
 * 机构角色-用户(RbacOrgRoleUser)业务层实现
 *
 * @author 安一老厨
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgRoleUserServiceImpl extends ServiceImpl<RbacOrgRoleUserMapper, RbacOrgRoleUserEntity> implements IRbacOrgRoleUserService {
    private final RbacOrgRoleUserCopyMap map;
    private final RbacOrgRoleUserPageCopyMap pageMap;
    private final RbacOrgRoleUserQueryCopyMap queryMap;
    private final RbacOrgRoleUserMapper mapper;

    @Override
    public void saveBatch(String userId, String orgId, Set<String> orgRoleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(orgRoleIds)) {
            List<RbacOrgRoleUserEntity> orgRoleUserEntities = new ArrayList<>(orgRoleIds.size());
            orgRoleIds.forEach(v -> {
                RbacOrgRoleUserEntity entity = RbacOrgRoleUserEntity.builder().userId(userId).orgId(orgId).orgRoleId(v).build();
                orgRoleUserEntities.add(entity);
            });
            boolean b = this.saveBatch(orgRoleUserEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存机构角色关联失败");
            }
        }
    }


    @Override
    public void deleteByUserId(String userId, String orgId) throws RuntimeException {
        LambdaQueryWrapper<RbacOrgRoleUserEntity> lambdaQueryWrapper = Wrappers.<RbacOrgRoleUserEntity>lambdaQuery().eq(RbacOrgRoleUserEntity::getUserId, userId).eq(RbacOrgRoleUserEntity::getOrgId, orgId);
        List<RbacOrgRoleUserEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Set<String> roleUserIds = new HashSet<>(list.size());
            list.forEach(v -> roleUserIds.add(v.getRoleUserId()));
            int i = mapper.physicalDeleteBatchIds(roleUserIds);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
            }
        }
    }
}
