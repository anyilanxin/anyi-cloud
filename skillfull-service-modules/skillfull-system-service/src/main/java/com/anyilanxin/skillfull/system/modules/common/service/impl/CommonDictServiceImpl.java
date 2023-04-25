/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.common.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonDictEntity;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonDictItemEntity;
import com.anyilanxin.skillfull.system.modules.common.mapper.CommonDictItemMapper;
import com.anyilanxin.skillfull.system.modules.common.mapper.CommonDictMapper;
import com.anyilanxin.skillfull.system.modules.common.service.ICommonDictService;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictPageDto;
import com.anyilanxin.skillfull.system.modules.common.service.mapstruct.CommonDictDtoMap;
import com.anyilanxin.skillfull.system.modules.common.service.mapstruct.CommonDictVoMap;
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
* 数据字典表(CommonDict)业务层实现
*
* @author zxiaozhou
* @date 2020-11-02 09:25:18
* @since JDK11
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonDictServiceImpl extends ServiceImpl<CommonDictMapper, CommonDictEntity>
        implements ICommonDictService {
    private final CommonDictDtoMap dtoMap;
    private final CommonDictVoMap voMap;
    private final CommonDictMapper mapper;
    private final CommonDictItemMapper itemMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonDictVo vo) throws RuntimeException {
        CommonDictEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        log.info("------------CommonDictServiceImpl------------>save:{}", entity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String dictId, CommonDictVo vo) throws RuntimeException {
        // 查询数据是否存在
        CommonDictDto byId = this.getById(dictId);
        // 更新数据
        CommonDictEntity entity = voMap.aToB(vo);
        entity.setDictId(dictId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据字典失败");
        }
        // 判断编码是否有变动，变动则更新子项
        if (!byId.getDictCode().equals(vo.getDictCode())) {
            // 查询是否有子项
            LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CommonDictItemEntity::getDictId, dictId);
            List<CommonDictItemEntity> commonDictItemEntities = itemMapper.selectList(lambdaQueryWrapper);
            // 有子项则更新所有子项编码
            if (CollUtil.isNotEmpty(commonDictItemEntities)) {
                CommonDictItemEntity itemEntity = new CommonDictItemEntity();
                itemEntity.setDictCode(vo.getDictCode());
                int update = itemMapper.update(itemEntity, lambdaQueryWrapper);
                if (update <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据字典子项编码失败");
                }
            }
        }
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class, Error.class},
            readOnly = true)
    public PageDto<CommonDictPageDto> pageByModel(CommonDictPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class, Error.class},
            readOnly = true)
    public CommonDictDto getById(String dictId) throws RuntimeException {
        CommonDictEntity byId = super.getById(dictId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return dtoMap.bToA(byId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String dictId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dictId);
        boolean b = this.removeById(dictId);
        if (!b) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        // 查询是否有子项,有子项则删除子项
        // 查询是否有子项
        LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonDictItemEntity::getDictId, dictId);
        List<CommonDictItemEntity> commonDictItemEntities = itemMapper.selectList(lambdaQueryWrapper);
        // 有子项则更新所有子项编码
        if (CollUtil.isNotEmpty(commonDictItemEntities)) {
            int delete = itemMapper.delete(lambdaQueryWrapper);
            if (delete <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据字典子项失败");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> dictIds) throws RuntimeException {
        List<CommonDictEntity> entities = this.listByIds(dictIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getDictId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateDictState(String dictId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dictId);
        CommonDictEntity entity = new CommonDictEntity();
        entity.setDictId(dictId);
        entity.setDictStatus(type == 1 ? 1 : 0);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新字典状态失败");
        }
    }
}
