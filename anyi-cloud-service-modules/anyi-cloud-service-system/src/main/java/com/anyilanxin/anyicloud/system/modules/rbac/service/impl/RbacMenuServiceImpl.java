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
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.utils.CodeUtil;
import com.anyilanxin.anyicloud.corecommon.utils.tree.AnYiTreeToolUtils;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.system.core.constant.impl.MenuType;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuTreeDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacMenuCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacMenuDtoMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单表(RbacMenu)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacMenuServiceImpl extends ServiceImpl<RbacMenuMapper, RbacMenuEntity> implements IRbacMenuService {
    private final RbacMenuCopyMap map;
    private final RbacMenuDtoMap dtoMap;
    private final RbacMenuMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacMenuVo vo) throws RuntimeException {
        RbacMenuEntity entity = map.vToE(vo);
        checkMenu(entity);
        entity.setMenuSysCode(generateCode(null, vo.getParentId(), false));
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据检查
     *
     * @author zxh
     * @date 2022-06-02 21:29
     */
    private void checkMenu(RbacMenuEntity entity) {
        // 目录与菜单公共部分
        if (entity.getMenuType() == MenuType.CATALOGUE.getType()) {
            if (StringUtils.isBlank(entity.getPath())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "地址不能为空");
            }
            if (StringUtils.isBlank(entity.getComponent())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "前端组件不能为空");
            }
            if (entity.isShowTag()) {
                if (StringUtils.isBlank(entity.getType())) {
                    throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "tag类型不能为空，并且只能为:primary、error、warn、success");
                }
            }
            if (StringUtils.isBlank(entity.getPathName())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "路由名称不能为空");
            }
            // 唯一性校验:同一个系统，通一个父级，路径唯一
            LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = Wrappers.<RbacMenuEntity>lambdaQuery().eq(RbacMenuEntity::getSystemId, entity.getSystemId()).eq(RbacMenuEntity::getPath, entity.getPath());
            if (StringUtils.isBlank(entity.getParentId())) {
                lambdaQueryWrapper.isNull(RbacMenuEntity::getParentId);
            } else {
                lambdaQueryWrapper.eq(RbacMenuEntity::getParentId, entity.getParentId());
            }
            if (StringUtils.isNotBlank(entity.getMenuId())) {
                lambdaQueryWrapper.ne(RbacMenuEntity::getMenuId, entity.getMenuId());
            }
            List<RbacMenuEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前路径已经存在:" + entity.getPath());
            }
        }
        // 菜单部分
        if (entity.getMenuType() == MenuType.MENU.getType()) {
            if (entity.isIframe()) {
                if (Objects.isNull(entity.getIframeType())) {
                    throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "外链接类型不能为空");
                } else if (entity.getIframeType() != 0 && entity.getIframeType() != 1) {
                    throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "外链接类型只能为0、1");
                }
                if (StringUtils.isBlank(entity.getPath())) {
                    throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "地址不能为空");
                }
            }
        }
        // 按钮部分
        else if (entity.getMenuType() == MenuType.BUTTON.getType()) {
            if (StringUtils.isBlank(entity.getButtonActionTag())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "权限标识不能为空");
            }
            if (StringUtils.isBlank(entity.getParentId()) && StringUtils.isBlank(entity.getMenuId())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "上级id不能为空");
            }
            if (StringUtils.isBlank(entity.getButtonExpress())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "鉴权表达式不能为空");
            }
        }
    }


    /**
     * 生成系统code
     *
     * @param oldParentId ${@link String} 历史上级id
     * @param newParentId ${@link String} 现在上级id
     * @param isUpdate    ${@link Boolean} false-新建,true-更新
     * @return String ${@link String}
     * @author zxh
     * @date 2021-03-08 12:00
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    synchronized String generateCode(String oldParentId, String newParentId, boolean isUpdate) {
        oldParentId = StringUtils.isBlank(oldParentId) ? "" : oldParentId;
        newParentId = StringUtils.isBlank(newParentId) ? "" : newParentId;
        if (isUpdate && oldParentId.equals(newParentId)) {
            return null;
        }
        String code;
        if (StringUtils.isBlank(newParentId)) {
            LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacMenuEntity::getParentId).or().eq(RbacMenuEntity::getParentId, "")).orderByDesc(RbacMenuEntity::getMenuSysCode).last("LIMIT 1");
            RbacMenuEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getMenuSysCode());
        } else {
            // 获取上级code
            RbacMenuDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacMenuEntity::getMenuSysCode, byId.getMenuSysCode() + "____").orderByDesc(RbacMenuEntity::getMenuSysCode).last("LIMIT 1");
            RbacMenuEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getMenuSysCode(), Objects.isNull(one) ? null : one.getMenuSysCode());
        }
        return code;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String menuId, RbacMenuVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(menuId);
        // 更新数据
        RbacMenuEntity entity = map.vToE(vo);
        entity.setMenuId(menuId);
        checkMenu(entity);
        entity.setParentId(null);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<RbacMenuPageDto> pageByModel(RbacMenuPageQuery vo) throws RuntimeException {
        vo.getAscs().add("menuSysCode");
        IPage<RbacMenuPageDto> pageInfo = mapper.pageByModel(PageUtils.getPage(vo), vo);
        /*
         * 1. 获取根节点 2. 构建树形
         */
        // 获取根节点
        List<RbacMenuPageDto> records = pageInfo.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            List<RbacMenuPageDto> rootRecords = new ArrayList<>(records.size());
            List<RbacMenuPageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                RbacMenuPageDto pageDto = records.remove(0);
                // 只要当前值是其他数据最小开头那么该值就是根节点，否则是子节点
                String permissionSysCode = pageDto.getMenuSysCode();
                boolean isParent = true;
                for (RbacMenuPageDto dto : records) {
                    if (permissionSysCode.startsWith(dto.getMenuSysCode())) {
                        isParent = false;
                        break;
                    }
                }
                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    List<RbacMenuPageDto> collect = records.stream().filter(v -> v.getMenuSysCode().startsWith(permissionSysCode)).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            AnYiTreeToolUtils<RbacMenuPageDto> treeToolUtils = new AnYiTreeToolUtils<>(rootRecords, childRecords, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacMenuPageDto rbacMenuPageDto) {
                    return rbacMenuPageDto.getMenuId();
                }


                @Override
                public String getParentId(RbacMenuPageDto rbacMenuPageDto) {
                    return rbacMenuPageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return PageUtils.toPageData(pageInfo, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacMenuDto getById(String menuId) throws RuntimeException {
        RbacMenuEntity byId = super.getById(menuId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String menuId) throws RuntimeException {
        // 查询是否有下级，如果有不能删除
        LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = Wrappers.<RbacMenuEntity>lambdaQuery().eq(RbacMenuEntity::getParentId, menuId);
        List<RbacMenuEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "请先删除下级");
        }
        // 查询数据是否存在
        this.getById(menuId);
        // 删除数据
        boolean b = this.removeById(menuId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> menuIds) throws RuntimeException {
        menuIds.forEach(this::deleteById);
    }


    @Override
    public List<RbacMenuTreeDto> getMenuTree(String type, String systemId, Integer status) {
        LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(RbacMenuEntity::getOrderNo);
        if (StringUtils.isNotBlank(type)) {
            lambdaQueryWrapper.in(RbacMenuEntity::getMenuType, Arrays.asList(type.split("[,，]")));
        }
        if (status == 1) {
            lambdaQueryWrapper.le(RbacMenuEntity::getMenuStatus, 1);
        }
        if (StringUtils.isNotBlank(systemId)) {
            lambdaQueryWrapper.eq(RbacMenuEntity::getSystemId, systemId);
        }
        List<RbacMenuEntity> list = this.list(lambdaQueryWrapper);
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
            AnYiTreeToolUtils<RbacMenuTreeDto> toolUtils = new AnYiTreeToolUtils<>(rootList, subList, new AnYiTreeToolUtils.TreeId<>() {
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
