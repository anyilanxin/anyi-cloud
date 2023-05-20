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

package com.anyilanxin.anyicloud.system.modules.common.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictItemPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictItemVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictEntity;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictItemEntity;
import com.anyilanxin.anyicloud.system.modules.common.mapper.CommonDictItemMapper;
import com.anyilanxin.anyicloud.system.modules.common.mapper.CommonDictMapper;
import com.anyilanxin.anyicloud.system.modules.common.service.ICommonDictItemService;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictItemDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictItemPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonDictItemDtoMap;
import com.anyilanxin.anyicloud.system.modules.common.service.mapstruct.CommonDictItemVoMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据字典配置项表(CommonDictItem)业务层实现
 *
 * @author zxh
 * @date 2020-11-02 09:25:26
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonDictItemServiceImpl extends ServiceImpl<CommonDictItemMapper, CommonDictItemEntity> implements ICommonDictItemService {
    private final CommonDictItemDtoMap dtoMap;
    private final CommonDictItemVoMap voMap;
    private final CommonDictItemMapper mapper;
    private final CommonDictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonDictItemVo vo) throws RuntimeException {
        CommonDictItemEntity entity = voMap.aToB(vo);
        // 查询当前值是否已经存在
        LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.or(v -> v.eq(CommonDictItemEntity::getItemText, vo.getItemText()).or().eq(CommonDictItemEntity::getItemValue, vo.getItemValue())).eq(CommonDictItemEntity::getDictId, vo.getDictId());
        List<CommonDictItemEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前字典项值或字典项名称已经存在,请重新输入");
        }
        // 查询当前字典编码
        CommonDictEntity commonDictEntity = dictMapper.selectById(vo.getDictId());
        if (Objects.isNull(commonDictEntity)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "字典类型不存在或被删除,请返回从新选择再添加");
        }
        entity.setDictCode(commonDictEntity.getDictCode());
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据字典项失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String itemId, CommonDictItemVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(itemId);
        // 查询字典项是否已经存在
        LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.or(v -> v.eq(CommonDictItemEntity::getItemText, vo.getItemText()).or().eq(CommonDictItemEntity::getItemValue, vo.getItemValue())).eq(CommonDictItemEntity::getDictId, vo.getDictId()).ne(CommonDictItemEntity::getItemId, itemId);
        List<CommonDictItemEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前字典项值或字典项名称已经存在,请重新输入");
        }
        // 更新数据
        CommonDictItemEntity entity = voMap.aToB(vo);
        entity.setItemId(itemId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据字典项失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonDictItemDto> selectListByCode(String dictCode) throws RuntimeException {
        return mapper.selectListByCode(dictCode);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonDictItemPageDto> pageByModel(CommonDictItemPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonDictItemDto getById(String itemId) throws RuntimeException {
        CommonDictItemEntity byId = super.getById(itemId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String itemId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(itemId);
        boolean b = this.removeById(itemId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据字典项失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> itemIds) throws RuntimeException {
        List<CommonDictItemEntity> entities = this.listByIds(itemIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getItemId()));
        boolean b = this.removeByIds(waitDeleteList);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据字典项失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateDictItemState(String itemId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(itemId);
        CommonDictItemEntity entity = new CommonDictItemEntity();
        entity.setItemId(itemId);
        entity.setItemStatus(type == 1 ? 1 : 0);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新字典项状态失败");
        }
    }
}
