

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.tree.TreeToolUtils;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuTreeDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacMenuDtoMap;
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
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
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
