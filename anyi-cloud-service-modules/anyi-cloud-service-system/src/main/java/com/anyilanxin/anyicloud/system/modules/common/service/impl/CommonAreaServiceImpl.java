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

package com.anyilanxin.anyicloud.system.modules.common.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.utils.tree.AnYiTreeToolUtils;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaPageQuery;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.anyicloud.system.modules.common.mapper.CommonAreaMapper;
import com.anyilanxin.anyicloud.system.modules.common.service.ICommonAreaService;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaTreeDto;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonAreaDtoMap;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonAreaPageDtoMap;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonAreaTreeCopyMap;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonAreaVoMap;
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
 * 区域表(CommonArea)业务层实现
 *
 * @author zxh
 * @date 2020-11-02 09:25:04
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonAreaServiceImpl extends ServiceImpl<CommonAreaMapper, CommonAreaEntity> implements ICommonAreaService {
    private final CommonAreaDtoMap dtoMap;
    private final CommonAreaTreeCopyMap treeCopyMap;
    private final CommonAreaVoMap voMap;
    private final CommonAreaPageDtoMap pageDtoMap;
    private final CommonAreaMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonAreaVo vo) throws RuntimeException {
        CommonAreaEntity entity = voMap.aToB(vo);
        // 数据校验
        this.checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据校验
     *
     * @param entity ${@link CommonAreaEntity} 待校验数据
     * @author zxh
     * @date 2021-01-07 20:54
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(CommonAreaEntity entity) {
        // 如果最上层，区域属于第一部分行政区域(前6位有效，每两位为一级)
        if (StringUtils.isBlank(entity.getParentId())) {
            String substring = entity.getAreaId().substring(0, 3).replaceAll("0+$", "");
            if (StringUtils.isNotBlank(substring)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "顶级区域id只能前2为非零");
            }
            entity.setAreaLevel(1);
            entity.setParentId("");
        }
        // 如果存在上级区域id,判断区域id格式
        else {
            String[] parentValidInfo = this.getParentValid(entity.getParentId());
            if (!entity.getAreaId().startsWith(parentValidInfo[0])) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "下级id必须包含上级id末尾除外的部分");
            }
            entity.setAreaLevel(Integer.parseInt(parentValidInfo[1]) + 1);
        }
        // 查询当前区域编码是否已经存在
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getAreaId, entity.getAreaId());
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前区域id已经存在" + entity.getAreaId());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String areaId, CommonAreaVo vo) throws RuntimeException {
        // 查询数据是否存在
        CommonAreaDto byId = this.getById(areaId);
        // 更新数据
        CommonAreaEntity entity = voMap.aToB(vo);
        if (!entity.getParentId().equals(byId.getParentId())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "上级id不允许改动");
        }
        // 数据校验
        this.checkData(entity);
        entity.setAreaId(areaId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonAreaTreeDto> selectList(String parentId, String activateAreaId) throws RuntimeException {
        List<CommonAreaTreeDto> resultList = new ArrayList<>(128);
        // 1. 查询下级并检测是否下级还有下级
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(parentId)) {
            lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, parentId);
        } else {
            lambdaQueryWrapper.and(v -> v.isNull(CommonAreaEntity::getParentId).or().eq(CommonAreaEntity::getParentId, ""));
        }
        List<CommonAreaTreeDto> activateAreaList = new ArrayList<>(1);
        // 如果有需要激活的id，获取整个省区域
        String activateProvinceId = "";
        if (StringUtils.isNotBlank(activateAreaId) && StringUtils.isBlank(parentId)) {
            List<CommonAreaTreeDto> activateChildren = new ArrayList<>(256);
            List<CommonAreaTreeDto> activateParentAreaList = new ArrayList<>(1);
            CommonAreaEntity activateEntity = super.getById(activateAreaId);
            CommonAreaEntity activateProvinceEntity = super.getById(activateEntity.getProvinceId());
            activateProvinceId = activateEntity.getProvinceId();
            activateParentAreaList.add(treeCopyMap.bToA(activateProvinceEntity));
            LambdaQueryWrapper<CommonAreaEntity> activateLambdaQueryWrapper = Wrappers.<CommonAreaEntity>lambdaQuery().likeRight(CommonAreaEntity::getAreaId, getLike(activateAreaId, activateEntity)).ne(CommonAreaEntity::getAreaId, activateEntity.getProvinceId());
            List<CommonAreaEntity> list = this.list(activateLambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                activateChildren.addAll(treeCopyMap.bToA(list));
            }
            AnYiTreeToolUtils<CommonAreaTreeDto> treeToolUtils = new AnYiTreeToolUtils<>(activateParentAreaList, activateChildren, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonAreaTreeDto areaTreeDto) {
                    return areaTreeDto.getAreaId();
                }


                @Override
                public String getParentId(CommonAreaTreeDto areaTreeDto) {
                    return areaTreeDto.getParentId();
                }
            });
            activateAreaList = treeToolUtils.getTree();
            lambdaQueryWrapper.ne(CommonAreaEntity::getAreaId, activateEntity.getProvinceId());
        }
        List<CommonAreaEntity> orgEntities = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(orgEntities)) {
            if (CollUtil.isNotEmpty(activateAreaList)) {
                resultList.addAll(activateAreaList);
            }
            List<String> areaIdsList = new ArrayList<>(orgEntities.size());
            orgEntities.forEach(v -> {
                resultList.add(treeCopyMap.bToA(v));
                areaIdsList.add(v.getAreaId());
            });
            if (StringUtils.isNotBlank(activateProvinceId)) {
                areaIdsList.add(activateProvinceId);
            }
            Map<String, Boolean> checkResults = checkHaveChildren(areaIdsList);
            resultList.forEach(v -> {
                Boolean result = checkResults.get(v.getAreaId());
                v.setHasChildren(Objects.nonNull(result));
                v.setIsLeaf(!Objects.nonNull(result));
            });
            resultList.sort(Comparator.comparing(CommonAreaTreeDto::getAreaId));
        }
        return resultList;
    }


    /**
     * 获取需要激活的like表达式
     *
     * @param activateAreaId
     * @return String
     * @author zxh
     * @date 2022-07-29 00:36
     */
    private String getLike(String activateAreaId, CommonAreaEntity byId) {
        String effectiveProvinceId = byId.getProvinceId().replaceAll("0", " ").trim().replaceAll(" ", "0");
        String effectiveParentId = byId.getParentId().replaceAll("0", " ").trim().replaceAll(" ", "0");
        String effectiveAreaId = activateAreaId.replaceAll("0", " ").trim().replaceAll(" ", "0");
        int lengthEffectiveProvinceId = effectiveProvinceId.length();
        int lengthEffectiveAreaId = effectiveAreaId.length();
        int lengthEffectiveParentId = effectiveParentId.length();
        int likeNum = lengthEffectiveAreaId - lengthEffectiveProvinceId;
        int zeroNum = lengthEffectiveAreaId - lengthEffectiveParentId;
        String likeAreaValue = effectiveProvinceId + String.join("", Collections.nCopies(likeNum, "_"));
        return likeAreaValue + String.join("", Collections.nCopies(zeroNum, "0"));
    }


    /**
     * 检测是否有下级
     *
     * @param areaIdsList 父级区域id列表
     * @return Map<String, Boolean> 结果，键为parentId
     * @author zxh
     * @date 2022-05-02 18:38
     */
    private Map<String, Boolean> checkHaveChildren(List<String> areaIdsList) {
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = Wrappers.<CommonAreaEntity>lambdaQuery().in(CommonAreaEntity::getParentId, areaIdsList);
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        Map<String, Boolean> result = new HashMap<>(areaIdsList.size());
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> result.put(v.getParentId(), true));
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<CommonAreaPageDto> pageByModel(CommonAreaPageQuery vo) throws RuntimeException {
        vo.getAscs().add("areaId");
        IPage<CommonAreaPageDto> page = mapper.pageByModel(PageUtils.getPage(vo), vo);
        List<CommonAreaPageDto> records = page.getRecords();
        /*
         * 1. 获取根节点 2. 构建树形
         */
        // 获取根节点
        if (CollUtil.isNotEmpty(records)) {
            List<CommonAreaPageDto> rootRecords = new ArrayList<>(records.size());
            List<CommonAreaPageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                CommonAreaPageDto pageDto = records.remove(0);
                String parentId = pageDto.getParentId();
                boolean isParent = true;
                if (StringUtils.isNotBlank(parentId)) {
                    for (CommonAreaPageDto dto : records) {
                        if (parentId.equals(dto.getAreaId())) {
                            isParent = false;
                            break;
                        }
                    }
                }

                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    String areaId = getEffectiveAreaId(pageDto.getAreaId(), pageDto.getAreaLevel());
                    List<CommonAreaPageDto> collect = records.stream().filter(v -> v.getAreaId().startsWith(areaId)).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            AnYiTreeToolUtils<CommonAreaPageDto> treeToolUtils = new AnYiTreeToolUtils<>(rootRecords, childRecords, new AnYiTreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonAreaPageDto orgTreePageDto) {
                    return orgTreePageDto.getAreaId();
                }


                @Override
                public String getParentId(CommonAreaPageDto orgTreePageDto) {
                    return orgTreePageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return PageUtils.toPageData(page, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonAreaPageDto> selectPageChildren(String parentId) throws RuntimeException {
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, parentId);
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        List<CommonAreaPageDto> pageDtoList = pageDtoMap.bToA(list);
        // 处理是否含有下级
        if (CollUtil.isNotEmpty(pageDtoList)) {
            Set<String> parentIds = new HashSet<>();
            pageDtoList.forEach(v -> parentIds.add(v.getAreaId()));
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonAreaEntity::getParentId, parentIds);
            List<CommonAreaEntity> children = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(children)) {
                pageDtoList.forEach(v -> {
                    for (CommonAreaEntity entity : children) {
                        if (entity.getParentId().equals(v.getAreaId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return pageDtoList;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonAreaDto getById(String areaId) throws RuntimeException {
        CommonAreaEntity byId = super.getById(areaId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String areaId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(areaId);
        // 判断是否有下级
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, areaId);
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "区域id:" + areaId + "存在下级,请先删除下级");
        }
        boolean b = this.removeById(areaId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> areaIds) throws RuntimeException {
        List<CommonAreaEntity> entities = this.listByIds(areaIds);
        if (CollUtil.isEmpty(entities)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAreaId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    /**
     * 获取有效的上级区域id信息
     *
     * @param parentId ${@link String} 上级区域编码
     * @return String[] ${@link String[]} 0-上级有效区域编码,1-上级区域编码等级
     * @author zxh
     * @date 2021-01-07 23:18
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    String[] getParentValid(String parentId) {
        CommonAreaDto byId = this.getById(parentId);
        // 数据第一部分行政区域(前6位，每两位代表一级)
        if (byId.getAreaLevel() <= 3) {
            parentId = byId.getAreaId().substring(0, byId.getAreaLevel() * 2 + 1);
        }
        // 属于第一部分+第二部分区域(前6位位一级，每两位代表一级，后6位位二级，每3位为一级)
        else {
            parentId = byId.getAreaId().substring(0, 3 * (byId.getAreaLevel() - 1));
        }
        return new String[]{parentId, byId.getAreaLevel() + ""};
    }


    /**
     * 获取有效的区域id
     *
     * @param areaId    ${@link String}
     * @param areaLevel ${@link Integer} 区域级别
     * @return String ${@link String}
     * @author zxh
     * @date 2021-08-22 20:21
     */
    String getEffectiveAreaId(String areaId, int areaLevel) {
        String effectiveAreaId;
        if (areaLevel <= 3) {
            effectiveAreaId = areaId.substring(0, areaLevel * 2 + 1);
        }
        // 属于第一部分+第二部分区域(前6位位一级，每两位代表一级，后6位位二级，每3位为一级)
        else {
            effectiveAreaId = areaId.substring(0, 3 * (areaLevel - 1));
        }
        return effectiveAreaId;
    }
}
