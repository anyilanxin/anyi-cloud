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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleMenuMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleMenuService;
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
 * 机构角色-菜单表(RbacOrgRoleMenu)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgRoleMenuServiceImpl
    extends ServiceImpl<RbacOrgRoleMenuMapper, RbacOrgRoleMenuEntity>
    implements IRbacOrgRoleMenuService {
  private final RbacOrgRoleMenuMapper mapper;

  @Override
  public void saveBatch(String orgRoleId, List<String> menuIds) throws RuntimeException {
    if (CollUtil.isNotEmpty(menuIds)) {
      List<RbacOrgRoleMenuEntity> roleMenuEntities = new ArrayList<>(menuIds.size());
      menuIds.forEach(
          v -> {
            RbacOrgRoleMenuEntity entity =
                RbacOrgRoleMenuEntity.builder().orgRoleId(orgRoleId).menuId(v).build();
            roleMenuEntities.add(entity);
          });
      boolean b = this.saveBatch(roleMenuEntities);
      if (!b) {
        throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色菜单关联失败");
      }
    }
  }

  @Override
  public void deleteBatch(List<String> orgRoleId) throws RuntimeException {
    if (CollUtil.isNotEmpty(orgRoleId)) {
      LambdaQueryWrapper<RbacOrgRoleMenuEntity> lambdaQueryWrapper =
          Wrappers.<RbacOrgRoleMenuEntity>lambdaQuery()
              .in(RbacOrgRoleMenuEntity::getOrgRoleId, orgRoleId);
      List<RbacOrgRoleMenuEntity> list = this.list(lambdaQueryWrapper);
      if (CollUtil.isNotEmpty(list)) {
        Set<String> roleMenuIds = new HashSet<>(list.size());
        list.forEach(v -> roleMenuIds.add(v.getOrgRoleMenuId()));
        int i = mapper.physicalDeleteBatchIds(roleMenuIds);
        if (i <= 0) {
          throw new ResponseException(
              Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
      }
    }
  }
}
