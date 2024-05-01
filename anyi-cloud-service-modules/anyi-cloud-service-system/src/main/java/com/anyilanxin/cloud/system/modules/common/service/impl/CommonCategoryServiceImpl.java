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

package com.anyilanxin.cloud.system.modules.common.service.impl;

import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.utils.tree.AnYiTreeToolUtils;
import com.anyilanxin.cloud.database.utils.PageUtils;
import com.anyilanxin.cloud.system.modules.common.controller.vo.CommonCategoryPageQuery;
import com.anyilanxin.cloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.cloud.system.modules.common.entity.CommonCategoryEntity;
import com.anyilanxin.cloud.system.modules.common.mapper.CommonCategoryMapper;
import com.anyilanxin.cloud.system.modules.common.service.ICommonCategoryService;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryTreeDto;
import com.anyilanxin.cloud.system.modules.common.service.mapstruct.CommonCategoryDtoMap;
import com.anyilanxin.cloud.system.modules.common.service.mapstruct.CommonCategoryPageDtoMap;
import com.anyilanxin.cloud.system.modules.common.service.mapstruct.CommonCategoryTreeDtoMap;
import com.anyilanxin.cloud.system.modules.common.service.mapstruct.CommonCategoryVoMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 分类字典表(CommonCategory)业务层实现
 *
 * @author zxh
 * @date 2021-01-07 23:40:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCategoryServiceImpl extends ServiceImpl<CommonCategoryMapper, CommonCategoryEntity> implements ICommonCategoryService {
    private final CommonCategoryDtoMap dtoMap;
    private final CommonCategoryTreeDtoMap treeDtoMap;
    private final CommonCategoryPageDtoMap pageDtoMap;
    private final CommonCategoryVoMap voMap;
    private final CommonCategoryMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonCategoryVo vo) throws RuntimeException {
        CommonCategoryEntity entity = voMap.aToB(vo);
        this.checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据检查并返回统一编码
     *
     * @param entity ${@link CommonCategoryEntity} 待检测数据
     * @author zxh
     * @date 2021-01-07 23:43
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(CommonCategoryEntity entity) {
        // 如果是子节点父节点必须传
        if (entity.getIsParent() == 1 && StringUtils.isBlank(entity.getParentId())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "添加子节点时父节点id必填");
        }
        // 查询分类统一编码是否存在
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId("");
            lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, entity.getCategoryCommonCode());
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前分类统一编码:" + entity.getCategoryCommonCode() + "已经存在");
            }
        }
        // 分类编码是否重复
        else {
            CommonCategoryDto byId = this.getById(entity.getParentId());
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, byId.getCategoryCommonCode()).eq(CommonCategoryEntity::getCategoryCode, entity.getCategoryCode());
            // 如果分类id存在则说明为更新
            if (StringUtils.isNotBlank(entity.getCategoryId())) {
                lambdaQueryWrapper.ne(CommonCategoryEntity::getCategoryId, entity.getCategoryId());
            }
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前分类编码:" + entity.getCategoryCode() + "已经存在");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String categoryId, CommonCategoryVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 更新数据
        CommonCategoryEntity entity = voMap.aToB(vo);
        entity.setCategoryId(categoryId);
        this.checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryDto> selectListByCommonCode(String categoryCommonCode) throws RuntimeException {
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, categoryCommonCode);
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        return dtoMap.bToA(list);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryTreeDto> selectTreeListByCommonCode(String categoryCommonCode) throws RuntimeException {
        List<CommonCategoryDto> commonCategoryDtos = this.selectListByCommonCode(categoryCommonCode);
        List<CommonCategoryTreeDto> commonCategoryTreeDtos = treeDtoMap.bToA(commonCategoryDtos);
        // 获取根节点
        if (CollUtil.isNotEmpty(commonCategoryTreeDtos)) {
            List<CommonCategoryTreeDto> rootList = new ArrayList<>();
            for (CommonCategoryTreeDto treeDto : commonCategoryTreeDtos) {
                if (StringUtils.isBlank(treeDto.getParentId())) {
                    rootList.add(treeDto);
                    break;
                }
            }
            // 构建树形
            AnYiTreeToolUtils<CommonCategoryTreeDto> toolUtils = new AnYiTreeToolUtils<>(rootList, commonCategoryTreeDtos, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getCategoryId();
                }


                @Override
                public String getParentId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<CommonCategoryPageDto> pageByModel(CommonCategoryPageQuery vo) throws RuntimeException {
        IPage<CommonCategoryPageDto> page = mapper.pageByModel(PageUtils.getPage(vo), vo);
        List<CommonCategoryPageDto> records = page.getRecords();
        // 判断是否有下级
        if (CollUtil.isNotEmpty(records)) {
            Set<String> parentIds = new HashSet<>();
            records.forEach(v -> parentIds.add(v.getCategoryId()));
            LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonCategoryEntity::getParentId, parentIds);
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                records.forEach(v -> {
                    for (CommonCategoryEntity entity : list) {
                        if (entity.getParentId().equals(v.getCategoryId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return PageUtils.toPageData(page, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryPageDto> selectPageChildren(String parentId) throws RuntimeException {
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getParentId, parentId);
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        List<CommonCategoryPageDto> commonCategoryPageDtos = pageDtoMap.bToA(list);
        // 查询是否有下级
        if (CollUtil.isNotEmpty(commonCategoryPageDtos)) {
            Set<String> parentIds = new HashSet<>();
            commonCategoryPageDtos.forEach(v -> parentIds.add(v.getCategoryId()));
            // 下级数据
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonCategoryEntity::getParentId, parentIds);
            List<CommonCategoryEntity> children = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(children)) {
                commonCategoryPageDtos.forEach(v -> {
                    for (CommonCategoryEntity entity : children) {
                        if (entity.getParentId().equals(v.getCategoryId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return commonCategoryPageDtos;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonCategoryDto getById(String categoryId) throws RuntimeException {
        CommonCategoryEntity byId = super.getById(categoryId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String categoryId) throws RuntimeException {
        // 查询数据是否存在
        CommonCategoryDto byId = this.getById(categoryId);
        // 查看是否存在下级
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getParentId, byId.getCategoryId());
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "存在下级,请先删除下级");
        }
        boolean b = this.removeById(categoryId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> categoryIds) throws RuntimeException {
        List<CommonCategoryEntity> entities = this.listByIds(categoryIds);
        if (CollUtil.isEmpty(entities)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getCategoryId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    @Override
    public List<CommonCategoryTreeDto> selectAllTree() throws RuntimeException {
        // 获取根节点
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.or(v -> v.isNull(CommonCategoryEntity::getParentId).or().eq(CommonCategoryEntity::getParentId, ""));
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        List<CommonCategoryTreeDto> rootList = treeDtoMap.bToA(dtoMap.bToA(list));
        // 获取子节点
        if (CollUtil.isNotEmpty(rootList)) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.isNotNull(CommonCategoryEntity::getParentId);
            list = this.list(lambdaQueryWrapper);
            List<CommonCategoryTreeDto> subList = treeDtoMap.bToA(dtoMap.bToA(list));
            // 构建树
            AnYiTreeToolUtils<CommonCategoryTreeDto> toolUtils = new AnYiTreeToolUtils<>(rootList, subList, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getCategoryId();
                }


                @Override
                public String getParentId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }
}
