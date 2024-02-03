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
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgResourceApiMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgResourceApiService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgHasChildrenDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgTreeDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgTreePageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgQueryCopyMap;
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
 * 组织表(RbacOrg)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgServiceImpl extends ServiceImpl<RbacOrgMapper, RbacOrgEntity> implements IRbacOrgService {
    private final RbacOrgCopyMap map;
    private final IRbacRoleService rbacRoleService;
    private final RbacOrgResourceApiMapper apiMapper;
    private final RbacOrgMenuMapper menuMapper;
    private final IRbacOrgMenuService menuService;
    private final IRbacOrgResourceApiService resourceApiService;
    private final RbacOrgQueryCopyMap queryMap;
    private final RbacOrgMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacOrgVo vo) throws RuntimeException {
        RbacOrgEntity entity = map.vToE(vo);
        this.checkData(entity);
        entity.setOrgSysCode(generateCode(vo.getParentId()));
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        // 保存权限信息
        saveAuth(entity.getOrgId(), vo);
    }


    /**
     * 存储机构权限
     *
     * @param orgId
     * @param vo
     * @author zxh
     * @date 2022-07-07 00:27
     */
    public void saveAuth(String orgId, RbacOrgVo vo) {
        // 存储机构功能权限
        menuService.save(orgId, vo.getOrgMenuIds());
        // 存储机构资源权限
        resourceApiService.save(orgId, vo.getOrgResourceIds());
    }


    /**
     * 生成系统code
     *
     * @param newParentId 现在上级id
     * @return String
     * @author zxh
     * @date 2021-03-08 12:00
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    synchronized String generateCode(String newParentId) {
        newParentId = StringUtils.isBlank(newParentId) ? "" : newParentId;
        String code;
        if (StringUtils.isBlank(newParentId)) {
            LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacOrgEntity::getParentId).or().eq(RbacOrgEntity::getParentId, "")).orderByDesc(RbacOrgEntity::getOrgSysCode).last("LIMIT 1");
            RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getOrgSysCode());
        } else {
            // 获取上级code
            RbacOrgDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacOrgEntity::getOrgSysCode, byId.getOrgSysCode() + "___").orderByDesc(RbacOrgEntity::getOrgSysCode).last("LIMIT 1");
            RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getOrgSysCode(), Objects.isNull(one) ? null : one.getOrgSysCode());
        }
        return code;
    }


    /**
     * 入库前数据校验以及生产系统机构编码
     *
     * @param entity 待入库数据
     * @author zxh
     * @date 2021-01-30 23:53
     */
    @Transactional(rollbackFor = {Exception.class, Error.class})
    void checkData(RbacOrgEntity entity) {
        // 查询自定义机构编码是否重复
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacOrgEntity::getOrgCode, entity.getOrgCode());
        if (StringUtils.isNotBlank(entity.getOrgId())) {
            lambdaQueryWrapper.ne(RbacOrgEntity::getOrgId, entity.getOrgId());
        }
        RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前机构编码已经存在:" + entity.getOrgCode());
        }
        // 查询社会信用代码是否使用
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacOrgEntity::getSocialCode, entity.getSocialCode());
        if (StringUtils.isNotBlank(entity.getOrgId())) {
            lambdaQueryWrapper.ne(RbacOrgEntity::getOrgId, entity.getOrgId());
        }
        one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前机构社会信用代码已经存在:" + entity.getOrgCode());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String orgId, RbacOrgVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgId);
        // 更新数据
        RbacOrgEntity entity = map.vToE(vo);
        // 更新是父级不可变更
        entity.setOrgSysCode(null);
        entity.setParentId(null);
        entity.setOrgId(orgId);
        this.checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
        // 保存权限信息
        saveAuth(entity.getOrgId(), vo);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<RbacOrgTreePageDto> pageByModel(RbacOrgPageQuery vo) throws RuntimeException {
        // 分页查询
        vo.getAscs().add("orgSysCode");
        IPage<RbacOrgTreePageDto> pageInfo = mapper.pageByModel(PageUtils.getPage(vo), vo);
        /*
         * 1. 获取根节点 2. 构建树形
         */
        // 获取根节点
        List<RbacOrgTreePageDto> records = pageInfo.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            List<RbacOrgTreePageDto> rootRecords = new ArrayList<>(records.size());
            List<RbacOrgTreePageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                RbacOrgTreePageDto pageDto = records.remove(0);
                String orgSysCode = pageDto.getOrgSysCode();
                boolean isParent = true;
                for (RbacOrgTreePageDto dto : records) {
                    if (orgSysCode.startsWith(dto.getOrgSysCode())) {
                        isParent = false;
                    }
                }
                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    List<RbacOrgTreePageDto> collect = records.stream().filter(v -> v.getOrgSysCode().startsWith(orgSysCode)).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            AnYiTreeToolUtils<RbacOrgTreePageDto> treeToolUtils = new AnYiTreeToolUtils<>(rootRecords, childRecords, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacOrgTreePageDto orgTreePageDto) {
                    return orgTreePageDto.getOrgId();
                }


                @Override
                public String getParentId(RbacOrgTreePageDto orgTreePageDto) {
                    return orgTreePageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return PageUtils.toPageData(pageInfo, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacOrgDto getById(String orgId) throws RuntimeException {
        RbacOrgEntity byId = super.getById(orgId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        RbacOrgDto rbacOrgDto = map.eToD(byId);
        // 查询功能权限
        Set<String> menus = menuMapper.selectMenuListById(orgId);
        if (CollUtil.isEmpty(menus)) {
            menus = Collections.emptySet();
        }
        rbacOrgDto.setOrgMenuIds(menus);
        // 查询资源权限
        Set<String> resourceAuth = apiMapper.selectAllResource(orgId);
        Set<RbacResourceApiPageDto> selectApiInfos = apiMapper.selectAllAllInfoResource(orgId);
        if (CollUtil.isEmpty(resourceAuth)) {
            resourceAuth = Collections.emptySet();
            selectApiInfos = Collections.emptySet();
        }
        rbacOrgDto.setOrgResourceIds(resourceAuth);
        rbacOrgDto.setOrgResourceInfos(selectApiInfos);
        return rbacOrgDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String orgId) throws RuntimeException {
        // 查询数据是否存在
        RbacOrgDto byId = this.getById(orgId);
        // 查询下级
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = Wrappers.<RbacOrgEntity>lambdaQuery().likeRight(RbacOrgEntity::getOrgSysCode, byId.getOrgSysCode());
        List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
        // 获取默认角色id
        List<String> orgIds = new ArrayList<>();
        orgIds.add(orgId);
        // 删除数据
        if (CollUtil.isNotEmpty(list)) {
            boolean remove = this.removeBatchByIds(orgIds);
            if (!remove) {
                throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
            }
        }
    }


    @Override
    public void updateOrgState(String orgId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgId);
        // 更新数据
        RbacOrgEntity entity = new RbacOrgEntity();
        entity.setOrgId(orgId);
        entity.setOrgStatus(type);
        boolean b = super.updateById(entity);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, type == 0 ? "机构禁用失败" : "机构启用失败");
        }
    }


    @Override
    public List<RbacOrgTreeDto> selectOrgTreeAsync(String parentId, String activateOrgId) {
        List<RbacOrgTreeDto> parentOrgList = new ArrayList<>(128);
        List<RbacOrgTreeDto> childOrgList = new ArrayList<>(256);
        // 1. 查询下级并检测是否下级还有下级
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(parentId)) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getParentId, parentId);
        } else {
            lambdaQueryWrapper.and(v -> v.isNull(RbacOrgEntity::getParentId).or().eq(RbacOrgEntity::getParentId, ""));
        }
        List<RbacOrgEntity> orgEntities = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(orgEntities)) {
            parentOrgList.addAll(queryMap.dToE(orgEntities));
            List<String> orgIdsList = new ArrayList<>(orgEntities.size());
            orgEntities.forEach(v -> {
                parentOrgList.add(queryMap.dToE(v));
                orgIdsList.add(v.getOrgId());
            });
            Map<String, Boolean> checkResults = checkHaveChildren(orgIdsList);
            parentOrgList.forEach(v -> {
                Boolean result = checkResults.get(v.getOrgId());
                v.setHasChildren(Objects.nonNull(result));
                v.setIsLeaf(!Objects.nonNull(result));
            });
            // 2. 如果有需要激活的id，获取父级到需要激活的整个树数据
            if (StringUtils.isNotBlank(activateOrgId)) {
                RbacOrgEntity byId = super.getById(activateOrgId);
                if (Objects.nonNull(byId)) {
                    String orgSysCode = byId.getOrgSysCode();
                    String orgSysParentCode = "";
                    for (RbacOrgTreeDto orgTreeDto : parentOrgList) {
                        if (orgSysCode.startsWith(orgTreeDto.getOrgSysCode())) {
                            orgSysParentCode = orgTreeDto.getOrgSysCode();
                            break;
                        }
                    }
                    if (StringUtils.isNotBlank(orgSysParentCode)) {
                        lambdaQueryWrapper = Wrappers.<RbacOrgEntity>lambdaQuery().ne(RbacOrgEntity::getOrgId, orgSysParentCode).likeRight(RbacOrgEntity::getOrgSysCode, orgSysParentCode);
                        List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
                        if (CollUtil.isNotEmpty(list)) {
                            childOrgList.addAll(queryMap.dToE(list));
                        }
                    }
                }
            }
        }
        // 3. 构建树
        AnYiTreeToolUtils<RbacOrgTreeDto> treeToolUtils = new AnYiTreeToolUtils<>(parentOrgList, childOrgList, new AnYiTreeToolUtils.TreeId<>() {
            @Override
            public String getId(RbacOrgTreeDto orgTreeDto) {
                return orgTreeDto.getOrgId();
            }


            @Override
            public String getParentId(RbacOrgTreeDto orgTreeDto) {
                return orgTreeDto.getParentId();
            }
        });
        return treeToolUtils.getTree();
    }


    /**
     * 检测是否有下级
     *
     * @param orgIdsList 父级机构id列表
     * @return Map<String, Boolean> 结果，键为parentId
     * @author zxh
     * @date 2022-05-02 18:38
     */
    private Map<String, Boolean> checkHaveChildren(List<String> orgIdsList) {
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = Wrappers.<RbacOrgEntity>lambdaQuery().in(RbacOrgEntity::getParentId, orgIdsList);
        List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
        Map<String, Boolean> result = new HashMap<>(orgIdsList.size());
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> result.put(v.getParentId(), true));
        }
        return result;
    }


    @Override
    public List<RbacOrgHasChildrenDto> selectOrgList(Integer type, String parentId) {
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        if (StringUtils.isNotBlank(parentId)) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getParentId, parentId);
        }
        List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
        List<RbacOrgHasChildrenDto> rbacOrgTreeDtos = queryMap.dToV(list);
        // 处理是否有下级的问题
        if (CollUtil.isNotEmpty(rbacOrgTreeDtos)) {
            List<String> parentIds = new ArrayList<>();
            rbacOrgTreeDtos.forEach(v -> parentIds.add(v.getOrgId()));
            Map<String, Boolean> checkResults = checkHaveChildren(parentIds);
            if (CollUtil.isNotEmpty(checkResults)) {
                rbacOrgTreeDtos.forEach(v -> {
                    Boolean result = checkResults.get(v.getOrgId());
                    v.setHasChildren(Objects.nonNull(result));
                    v.setIsLeaf(!Objects.nonNull(result));
                });
            }

        }
        return rbacOrgTreeDtos;
    }


    @Override
    public List<RbacOrgTreeDto> selectOrgTreeList(Integer type) throws RuntimeException {
        // 获取父级
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(v -> v.isNull(RbacOrgEntity::getParentId).or().eq(RbacOrgEntity::getParentId, ""));
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        List<RbacOrgEntity> parentList = this.list(lambdaQueryWrapper);
        // 获取所有非父级
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.isNotNull(RbacOrgEntity::getParentId).ne(RbacOrgEntity::getParentId, "");
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        List<RbacOrgEntity> children = this.list(lambdaQueryWrapper);
        // 组装树形
        if (CollUtil.isNotEmpty(parentList)) {
            AnYiTreeToolUtils<RbacOrgTreeDto> utils = new AnYiTreeToolUtils<>(queryMap.dToE(parentList), queryMap.dToE(children), new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacOrgTreeDto rbacOrgTreeDto) {
                    return rbacOrgTreeDto.getOrgId();
                }


                @Override
                public String getParentId(RbacOrgTreeDto rbacOrgTreeDto) {
                    return rbacOrgTreeDto.getParentId();
                }
            });
            return utils.getTree();
        }
        return Collections.emptyList();
    }
}
