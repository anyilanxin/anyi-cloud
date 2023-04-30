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

package com.anyilanxin.anyicloud.logging.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.logging.core.constant.LoggingCommonConstant;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.AuthDataPageDto;
import com.anyilanxin.anyicloud.logging.modules.manage.service.mapstruct.AuthDataCopyMap;
import com.anyilanxin.anyicloud.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.anyicloud.logging.modules.manage.mapper.AuthDataMapper;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.AuthDataDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录日志(AuthData)业务层实现
 *
 * @author 安一老厨
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-01-26 21:53:03
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDataServiceImpl extends ServiceImpl<AuthDataMapper, AuthDataEntity> implements IAuthDataService {
    private final AuthDataCopyMap map;
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthDataMapper mapper;
    private final Snowflake snowflake;

    @Value("${app.log-delete-type:1}")
    private Integer deleteLogType;

    @Value("${app.log-auth-save-min:10}")
    private Integer authSaveMin;

    @Override
    @Async
    public void storage() {
        Long size = stringRedisTemplate.opsForList().size(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX);
        int saveMax = 200;
        if (Objects.nonNull(size)) {
            // 循环读取100条
            if (size < saveMax) {
                saveMax = size.intValue();
            }
            if (size >= authSaveMin) {
                List<AuthDataEntity> logEntityList = new ArrayList<>(saveMax);
                for (int i = 0; i < saveMax; i++) {
                    String logStr = stringRedisTemplate.opsForList().rightPop(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX);
                    if (StringUtils.isNotBlank(logStr)) {
                        AuthDataEntity logModel = JSONObject.parseObject(logStr, AuthDataEntity.class);
                        logModel.setDelFlag(0);
                        logEntityList.add(logModel);
                    }
                }
                if (CollUtil.isNotEmpty(logEntityList)) {
                    mapper.insertBatchSomeColumn(logEntityList);
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<AuthDataPageDto> pageByModel(AuthDataPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AuthDataDto getById(String authLogId) throws RuntimeException {
        AuthDataEntity byId = super.getById(authLogId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String authLogId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(authLogId);
        // 删除数据
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteById(authLogId);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "物理删除数据失败");
            }
        } else {
            boolean b = this.removeById(authLogId);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> authLogIds) throws RuntimeException {
        List<AuthDataEntity> entities = this.listByIds(authLogIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAuthLogId()));
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteBatchIds(waitDeleteList);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量物理删除数据失败");
            }
        } else {
            boolean b = this.removeByIds(waitDeleteList);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
            }
        }
    }
}
