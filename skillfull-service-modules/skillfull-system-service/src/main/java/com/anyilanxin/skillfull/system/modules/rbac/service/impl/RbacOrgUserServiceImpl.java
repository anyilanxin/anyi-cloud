/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacJoinOrgVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 机构-用户(RbacOrgUser)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
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
                RbacOrgUserEntity userEntity = RbacOrgUserEntity.builder()
                        .orgId(vo.getOrgId())
                        .userId(v)
                        .build();
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
        lambdaQueryWrapper.eq(RbacOrgUserEntity::getUserId, userId)
                .eq(RbacOrgUserEntity::getOrgId, orgId);
        RbacOrgUserEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户未关联当前机构");
        }
        mapper.physicalDeleteById(one.getOrgUserId());
        // 删除机构用户角色关联
        orgRoleUserService.deleteByUserId(userId, orgId);
    }
}
