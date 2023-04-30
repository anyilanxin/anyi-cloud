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

package com.anyilanxin.anyicloud.process.modules.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.SelectModel;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.anyicloud.process.modules.base.mapper.ProcessCategoryMapper;
import com.anyilanxin.anyicloud.process.modules.base.service.IProcessCategoryService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.ProcessCategoryPageDto;
import com.anyilanxin.anyicloud.process.modules.base.service.mapstruct.ProcessCategoryCopyMap;
import com.anyilanxin.anyicloud.process.modules.base.service.mapstruct.ProcessCategoryPageCopyMap;
import com.anyilanxin.anyicloud.process.modules.base.service.mapstruct.ProcessCategoryQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 流程类别(ProcessCategory)业务层实现
 *
 * @author 安一老厨
 * @date 2021-11-19 10:47:01
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessCategoryServiceImpl extends ServiceImpl<ProcessCategoryMapper, ProcessCategoryEntity> implements IProcessCategoryService {
    private final ProcessCategoryCopyMap map;
    private final ProcessCategoryPageCopyMap pageMap;
    private final ProcessCategoryQueryCopyMap queryMap;
    private final ProcessCategoryMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ProcessCategoryVo vo) throws RuntimeException {
        ProcessCategoryEntity entity = map.vToE(vo);
        // 数据验证
        checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据校验
     *
     * @param entity ${@link ProcessCategoryEntity}
     * @author 安一老厨
     * @date 2021-11-19 14:25
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(ProcessCategoryEntity entity) {
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, entity.getCategoryCode());
        if (StringUtils.isNotBlank(entity.getCategoryId())) {
            lambdaQueryWrapper.ne(ProcessCategoryEntity::getCategoryId, entity.getCategoryId());
        }
        ProcessCategoryEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前类别编码已经存在:" + entity.getCategoryCode());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String categoryId, ProcessCategoryVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 更新数据
        ProcessCategoryEntity entity = map.vToE(vo);
        entity.setCategoryId(categoryId);
        // 数据验证
        checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo) throws RuntimeException {
        List<ProcessCategoryDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }


    @Override
    public ProcessCategoryDto selectByCode(String categoryCode) {
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, categoryCode);
        return map.eToD(this.getOne(lambdaQueryWrapper));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ProcessCategoryPageDto> pageByModel(ProcessCategoryPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ProcessCategoryDto getById(String categoryId) throws RuntimeException {
        ProcessCategoryEntity byId = super.getById(categoryId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    public List<ProcessCategoryDto> selectListByCodes(Set<String> categoryCodes) {
        if (CollUtil.isNotEmpty(categoryCodes)) {
            LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(ProcessCategoryEntity::getCategoryCode, categoryCodes);
            List<ProcessCategoryEntity> list = this.list(lambdaQueryWrapper);
            return map.eToD(list);
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String categoryId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 删除数据
        boolean b = this.removeById(categoryId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    public List<SelectModel> getModelDesignList() {
        List<SelectModel> result = mapper.getModelDesignList();
        if (CollUtil.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }
}
