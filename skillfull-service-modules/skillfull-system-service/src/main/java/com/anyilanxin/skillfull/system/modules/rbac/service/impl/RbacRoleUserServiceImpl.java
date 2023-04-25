/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacRoleUserService;
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
* 角色-客户端(RbacRoleUser)业务层实现
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-07-02 23:01:21
* @since JDK1.8
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleUserServiceImpl extends ServiceImpl<RbacRoleUserMapper, RbacRoleUserEntity>
        implements IRbacRoleUserService {
    private final RbacRoleUserMapper mapper;

    @Override
    public void saveBatch(String userId, Set<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            List<RbacRoleUserEntity> roleUserEntities = new ArrayList<>(roleIds.size());
            roleIds.forEach(
                    v -> {
                        RbacRoleUserEntity entity =
                                RbacRoleUserEntity.builder().userId(userId).roleId(v).build();
                        roleUserEntities.add(entity);
                    });
            boolean b = this.saveBatch(roleUserEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色关联失败");
            }
        }
    }

    @Override
    public void deleteBatch(List<String> userIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(userIds)) {
            LambdaQueryWrapper<RbacRoleUserEntity> lambdaQueryWrapper =
                    Wrappers.<RbacRoleUserEntity>lambdaQuery().in(RbacRoleUserEntity::getUserId, userIds);
            List<RbacRoleUserEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> userRoleIds = new HashSet<>(list.size());
                list.forEach(v -> userRoleIds.add(v.getRoleUserId()));
                int i = mapper.physicalDeleteBatchIds(userRoleIds);
                if (i <= 0) {
                    throw new ResponseException(
                            Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
