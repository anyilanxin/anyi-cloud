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
package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageCustomFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageCustomFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.mapper.ManageCustomFilterMapper;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageCustomFilterService;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageSpecialUrlService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterDetailDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterListDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageSpecialUrlDto;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ManageCustomFilterCopyDetailMap;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ManageCustomFilterCopyMap;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ManageCustomFilterSimpleCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 自定义过滤器(ManageCustomFilter)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:15
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageCustomFilterServiceImpl extends ServiceImpl<ManageCustomFilterMapper, ManageCustomFilterEntity> implements IManageCustomFilterService {
    private final ManageCustomFilterCopyMap map;
    private final ManageCustomFilterCopyDetailMap detailMap;
    private final ManageCustomFilterSimpleCopyMap simpleCopyMap;
    private final ManageCustomFilterMapper mapper;
    private final IManageSpecialUrlService specialUrlService;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageCustomFilterVo vo) throws RuntimeException {
        ManageCustomFilterEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        // 保存特殊url
        specialUrlService.deleteAndSave(vo.getSpecialUrls(), entity.getCustomFilterId());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String customFilterId, ManageCustomFilterVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(customFilterId);
        // 更新数据
        ManageCustomFilterEntity entity = map.vToE(vo);
        entity.setCustomFilterId(customFilterId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
        // 保存特殊url
        specialUrlService.deleteAndSave(vo.getSpecialUrls(), customFilterId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageCustomFilterDetailDto getById(String customFilterId) throws RuntimeException {
        ManageCustomFilterEntity byId = super.getById(customFilterId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        ManageCustomFilterDetailDto manageCustomFilterDto = detailMap.eToD(byId);
        manageCustomFilterDto.setSpecialUrls(specialUrlService.selectByCustomFilterId(customFilterId));
        return manageCustomFilterDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String customFilterId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(customFilterId);
        // 删除数据
        boolean b = this.removeById(customFilterId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        // 删除特殊url
        specialUrlService.deleteByCustomFilterId(customFilterId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByServiceId(String serviceId) throws RuntimeException {
        LambdaQueryWrapper<ManageCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageCustomFilterEntity::getServiceId, serviceId);
        List<ManageCustomFilterEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Set<String> customFilterIds = new HashSet<>();
            list.forEach(v -> customFilterIds.add(v.getCustomFilterId()));
            boolean remove = this.remove(lambdaQueryWrapper);
            if (!remove) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除自定义过滤器失败");
            }
            // 删除自定义过滤器中特殊url
            specialUrlService.deleteByCustomFilterIds(customFilterIds);
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageCustomFilterListDto> selectList(String serviceId) {
        LambdaQueryWrapper<ManageCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageCustomFilterEntity::getServiceId, serviceId);
        List<ManageCustomFilterListDto> list = map.eToD(this.list(lambdaQueryWrapper));
        if (CollUtil.isNotEmpty(list)) {
            Set<String> customFilterIds = new HashSet<>(list.size());
            list.forEach(v -> customFilterIds.add(v.getCustomFilterId()));
            Map<String, List<ManageSpecialUrlDto>> stringListMap = specialUrlService.selectByCustomFilterIds(customFilterIds);
            if (CollUtil.isNotEmpty(stringListMap)) {
                list.forEach(v -> {
                    List<ManageSpecialUrlDto> manageSpecialUrlDtos = stringListMap.get(v.getCustomFilterId());
                    if (CollUtil.isNotEmpty(manageSpecialUrlDtos)) {
                        List<ManageSpecialUrlDto> whiteSpecialUrls = new ArrayList<>(manageSpecialUrlDtos.size());
                        List<ManageSpecialUrlDto> blackSpecialUrls = new ArrayList<>(manageSpecialUrlDtos.size());
                        manageSpecialUrlDtos.forEach(sv -> {
                            if (sv.getSpecialUrlType() == 1) {
                                whiteSpecialUrls.add(sv);
                            } else {
                                blackSpecialUrls.add(sv);
                            }
                        });
                        v.setWhiteSpecialUrls(whiteSpecialUrls);
                        v.setBlackSpecialUrls(blackSpecialUrls);
                    }
                });
            }
        }
        return list;
    }


    @Override
    public Map<String, List<ManageCustomFilterSimpleDto>> selectListRouterIds(Set<String> routerIds, String serviceId) {
        if (CollectionUtil.isEmpty(routerIds)) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<ManageCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageCustomFilterEntity::getServiceId, serviceId)
                .eq(ManageCustomFilterEntity::getFilterStatus, 1);
        List<ManageCustomFilterEntity> list = this.list(lambdaQueryWrapper);
        List<ManageCustomFilterSimpleDto> manageCustomFilterSimpleDtos = simpleCopyMap.aToB(list);
        Map<String, List<ManageCustomFilterSimpleDto>> result = new HashMap<>(routerIds.size());
        routerIds.forEach(v -> result.put(v, manageCustomFilterSimpleDtos));
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateStatus(String customFilterId, Integer state) {
        ManageCustomFilterEntity byId = super.getById(customFilterId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        ManageCustomFilterEntity waitUpdate = ManageCustomFilterEntity.builder()
                .filterStatus(state)
                .customFilterId(customFilterId)
                .build();
        boolean b = this.updateById(waitUpdate);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "修改状态失败");
        }
    }


    @Override
    public List<ManageCustomFilterSimpleDto> selectSimpleList(String serviceId) {
        LambdaQueryWrapper<ManageCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageCustomFilterEntity::getServiceId, serviceId)
                .eq(ManageCustomFilterEntity::getFilterStatus, 1);
        return simpleCopyMap.aToB(this.list(lambdaQueryWrapper));
    }
}
