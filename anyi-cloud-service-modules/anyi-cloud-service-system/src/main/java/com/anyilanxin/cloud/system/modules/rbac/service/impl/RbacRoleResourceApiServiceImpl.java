/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.system.modules.rbac.service.impl;

import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.system.modules.rbac.entity.RbacRoleResourceApiEntity;
import com.anyilanxin.cloud.system.modules.rbac.mapper.RbacRoleResourceApiMapper;
import com.anyilanxin.cloud.system.modules.rbac.service.IRbacRoleResourceApiService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色-资源表(RbacRoleResourceApi)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleResourceApiServiceImpl extends ServiceImpl<RbacRoleResourceApiMapper, RbacRoleResourceApiEntity> implements IRbacRoleResourceApiService {
    private final RbacRoleResourceApiMapper mapper;

    @Override
    public void saveBatch(String roleId, List<String> apiIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(apiIds)) {
            List<RbacRoleResourceApiEntity> resourceApiEntities = new ArrayList<>(apiIds.size());
            apiIds.forEach(v -> {
                RbacRoleResourceApiEntity entity = RbacRoleResourceApiEntity.builder().roleId(roleId).apiId(v).build();
                resourceApiEntities.add(entity);
            });
            boolean b = this.saveBatch(resourceApiEntities);
            if (!b) {
                throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "保存角色资源关联失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            LambdaQueryWrapper<RbacRoleResourceApiEntity> lambdaQueryWrapper = Wrappers.<RbacRoleResourceApiEntity>lambdaQuery().in(RbacRoleResourceApiEntity::getRoleId, roleIds);
            List<RbacRoleResourceApiEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> roleResourceApiIds = new HashSet<>(list.size());
                list.forEach(v -> roleResourceApiIds.add(v.getRoleResourceApiId()));
                int i = mapper.anyiPhysicalDeleteBatchIds(roleResourceApiIds);
                if (i <= 0) {
                    throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
