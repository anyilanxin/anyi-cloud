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

package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.tree.TreeToolUtils;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgMenuMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgMenuService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuTreeDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacMenuDtoMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构-菜单表(RbacOrgMenu)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgMenuServiceImpl extends ServiceImpl<RbacOrgMenuMapper, RbacOrgMenuEntity> implements IRbacOrgMenuService {
    private final RbacOrgMenuMapper mapper;
    private final RbacMenuDtoMap dtoMap;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(String orgId, Set<String> menuIds) throws RuntimeException {
        if (CollUtil.isEmpty(menuIds)) {
            // 删除所有
            mapper.physicalDeleteById(orgId);
        } else {
            // 删除不包含的资源
            mapper.physicalDeleteNotInIds(orgId, menuIds);
            // 添加新的
            List<RbacOrgMenuEntity> list = new ArrayList<>(menuIds.size());
            menuIds.forEach(v -> {
                RbacOrgMenuEntity resourceApi = RbacOrgMenuEntity.builder().menuId(v).orgId(orgId).build();
                list.add(resourceApi);
            });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存机构功能关联关系失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String orgId) throws RuntimeException {
        mapper.physicalDeleteById(orgId);
    }


    @Override
    public List<RbacMenuTreeDto> getMenuTree(String orgId, String systemId, Integer status) {
        List<RbacMenuEntity> list = mapper.selectByParams(orgId, systemId, status);
        if (CollUtil.isNotEmpty(list)) {
            List<RbacMenuTreeDto> rootList = new ArrayList<>();
            List<RbacMenuTreeDto> subList = new ArrayList<>();
            list.forEach(v -> {
                RbacMenuTreeDto dto = dtoMap.dToV(v);
                if (StringUtils.isBlank(v.getParentId())) {
                    rootList.add(dto);
                } else {
                    subList.add(dto);
                }
            });
            TreeToolUtils<RbacMenuTreeDto> toolUtils = new TreeToolUtils<>(rootList, subList, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacMenuTreeDto permissionTreeDto) {
                    return permissionTreeDto.getMenuId();
                }


                @Override
                public String getParentId(RbacMenuTreeDto permissionTreeDto) {
                    return permissionTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }
}
