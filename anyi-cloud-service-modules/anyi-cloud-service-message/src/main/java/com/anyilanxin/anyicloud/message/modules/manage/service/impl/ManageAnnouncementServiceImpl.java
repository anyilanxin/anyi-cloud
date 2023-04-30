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

package com.anyilanxin.anyicloud.message.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.anyicloud.message.modules.manage.mapper.ManageAnnouncementMapper;
import com.anyilanxin.anyicloud.message.modules.manage.service.IManageAnnouncementService;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import com.anyilanxin.anyicloud.message.modules.manage.service.mapstruct.ManageAnnouncementCopyMap;
import com.anyilanxin.anyicloud.message.modules.manage.service.mapstruct.ManageAnnouncementPageCopyMap;
import com.anyilanxin.anyicloud.message.modules.manage.service.mapstruct.ManageAnnouncementQueryCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 系统通告公告管理(ManageAnnouncement)业务层实现
 *
 * @author 安一老厨
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 08:34:22
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageAnnouncementServiceImpl extends ServiceImpl<ManageAnnouncementMapper, ManageAnnouncementEntity> implements IManageAnnouncementService {
    private final ManageAnnouncementCopyMap map;
    private final ManageAnnouncementPageCopyMap pageMap;
    private final ManageAnnouncementQueryCopyMap queryMap;
    private final ManageAnnouncementMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageAnnouncementVo vo) throws RuntimeException {
        ManageAnnouncementEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        // 更新数据
        ManageAnnouncementEntity entity = map.vToE(vo);
        entity.setAnntId(anntId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo) throws RuntimeException {
        List<ManageAnnouncementDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageAnnouncementDto getById(String anntId) throws RuntimeException {
        ManageAnnouncementEntity byId = super.getById(anntId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String anntId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        // 删除数据
        boolean b = this.removeById(anntId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> anntIds) throws RuntimeException {
        List<ManageAnnouncementEntity> entities = this.listByIds(anntIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAnntId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
