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

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleMenuService;
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
 * 角色-菜单表(RbacRoleMenu)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleMenuServiceImpl extends ServiceImpl<RbacRoleMenuMapper, RbacRoleMenuEntity> implements IRbacRoleMenuService {
    private final RbacRoleMenuMapper mapper;

    @Override
    public void saveBatch(String roleId, List<String> menuIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(menuIds)) {
            List<RbacRoleMenuEntity> roleMenuEntities = new ArrayList<>(menuIds.size());
            menuIds.forEach(v -> {
                RbacRoleMenuEntity entity = RbacRoleMenuEntity.builder().roleId(roleId).menuId(v).build();
                roleMenuEntities.add(entity);
            });
            boolean b = this.saveBatch(roleMenuEntities);
            if (!b) {
                throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "保存角色菜单关联失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            LambdaQueryWrapper<RbacRoleMenuEntity> lambdaQueryWrapper = Wrappers.<RbacRoleMenuEntity>lambdaQuery().in(RbacRoleMenuEntity::getRoleId, roleIds);
            List<RbacRoleMenuEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> roleMenuIds = new HashSet<>(list.size());
                list.forEach(v -> roleMenuIds.add(v.getRoleMenuId()));
                int i = mapper.anyiPhysicalDeleteBatchIds(roleMenuIds);
                if (i <= 0) {
                    throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
