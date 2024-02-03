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

package com.anyilanxin.anyicloud.process.modules.manage.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.DesignDraftPageQuery;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.DesignDraftVo;
import com.anyilanxin.anyicloud.process.modules.manage.entity.DesignDraftEntity;
import com.anyilanxin.anyicloud.process.modules.manage.mapper.DesignDraftMapper;
import com.anyilanxin.anyicloud.process.modules.manage.service.IDesignDraftService;
import com.anyilanxin.anyicloud.process.modules.manage.service.IProcessCategoryService;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.DesignDraftDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.DesignDraftPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessCategoryDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct.DesignDraftCopyMap;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 流程模型草稿(DesignDraft)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-04-26 15:00:46
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DesignDraftServiceImpl extends ServiceImpl<DesignDraftMapper, DesignDraftEntity> implements IDesignDraftService {
    private final DesignDraftCopyMap map;
    private final DesignDraftMapper mapper;
    private final IProcessCategoryService categoryService;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(DesignDraftVo vo) throws RuntimeException {
        var entity = map.vToE(vo);
        var result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String draftId, DesignDraftVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(draftId);
        // 更新数据
        var entity = map.vToE(vo);
        entity.setDraftId(draftId);
        var result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<DesignDraftPageDto> pageByModel(DesignDraftPageQuery vo) throws RuntimeException {
        IPage<DesignDraftPageDto> designDraftPageDtoIPage = mapper.pageByModel(PageUtils.getPage(vo), vo);
        List<DesignDraftPageDto> records = designDraftPageDtoIPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Set<String> categoryCode = new HashSet<>(records.size());
            records.forEach(v -> categoryCode.add(v.getCategory()));
            Map<String, ProcessCategoryDto> stringProcessCategoryDtoMap = categoryService.selectMapByCodes(categoryCode);
            if (CollUtil.isNotEmpty(stringProcessCategoryDtoMap)) {
                records.forEach(v -> {
                    ProcessCategoryDto processCategoryDto = stringProcessCategoryDtoMap.get(v.getCategory());
                    if (Objects.nonNull(processCategoryDto)) {
                        v.setCategoryName(processCategoryDto.getCategoryName());
                    }
                });
            }
        }
        return PageUtils.toPageData(designDraftPageDtoIPage, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public DesignDraftDto getById(String draftId) throws RuntimeException {
        var byId = super.getById(draftId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        DesignDraftDto designDraftDto = map.eToD(byId);
        ProcessCategoryDto processCategoryDto = categoryService.selectByCode(designDraftDto.getCategory());
        if (Objects.nonNull(processCategoryDto)) {
            designDraftDto.setCategoryName(processCategoryDto.getCategoryName());
        }
        return designDraftDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String draftId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(draftId);
        // 删除数据
        var b = this.removeById(draftId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> draftIds) throws RuntimeException {
        var entities = this.listByIds(draftIds);
        if (CollUtil.isEmpty(entities)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        var waitDeleteList = new ArrayList<String>();
        entities.forEach(v -> waitDeleteList.add(v.getDraftId()));
        var i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
