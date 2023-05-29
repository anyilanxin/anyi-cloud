/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacPositionPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacPositionVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacPositionEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacPositionMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacPositionService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacPositionDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacPositionPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacPositionCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacPositionDtoMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 职位表(RbacPosition)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacPositionServiceImpl extends ServiceImpl<RbacPositionMapper, RbacPositionEntity> implements IRbacPositionService {
    private final RbacPositionCopyMap map;
    private final RbacPositionDtoMap dtoMap;
    private final RbacPositionMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacPositionVo vo) throws RuntimeException {
        RbacPositionEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String positionId, RbacPositionVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        // 更新数据
        RbacPositionEntity entity = map.vToE(vo);
        entity.setPositionId(positionId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacPositionDto> getAllList() throws RuntimeException {
        LambdaQueryWrapper<RbacPositionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacPositionEntity::getPositionStatus, 1);
        List<RbacPositionEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            return dtoMap.bToA(list);
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacPositionPageDto> pageByModel(RbacPositionPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacPositionDto getById(String positionId) throws RuntimeException {
        RbacPositionEntity byId = super.getById(positionId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String positionId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        // 删除数据
        boolean b = this.removeById(positionId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> positionIds) throws RuntimeException {
        List<RbacPositionEntity> entities = this.listByIds(positionIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getPositionId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updatePositionState(String positionId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        RbacPositionEntity entity = new RbacPositionEntity();
        entity.setPositionId(positionId);
        entity.setPositionStatus(type);
        boolean b = super.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, type == 0 ? "职位禁用失败" : "职位启用失败");
        }
    }
}
