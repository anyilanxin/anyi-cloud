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

package com.anyilanxin.anyicloud.logging.modules.receive.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.service.IOperateService;
import com.anyilanxin.anyicloud.logging.core.constant.LoggingCommonConstant;
import com.anyilanxin.anyicloud.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.anyicloud.logging.modules.receive.service.IReceiveService;
import com.anyilanxin.anyicloud.logging.modules.receive.service.mapstruct.AuthModelCopyMap;
import com.anyilanxin.anyicloud.logging.modules.receive.service.mapstruct.OperateModelCopyMap;
import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.model.OperateLogModel;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 日志接收service
 *
 * @author 安一老厨
 * @date 2022-08-13 10:58
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveServiceImpl implements IReceiveService {
    private final IOperateService operateService;
    private final AuthModelCopyMap authDataCopyMap;
    private final OperateModelCopyMap operateCopyMap;
    private final IAuthDataService authDataService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveAuth(AuthLogModel model) throws RuntimeException {
        AuthDataEntity authDataEntity = authDataCopyMap.bToA(model);
        setUserInfo(authDataEntity);
        stringRedisTemplate.opsForList().leftPush(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX, JSONObject.toJSONString(authDataEntity, JSONWriter.Feature.WriteMapNullValue));
        // 触发入库
        triggerAuthLog();
    }


    @Override
    public void saveAuthBatch(List<AuthLogModel> models) throws RuntimeException {
        List<String> authLogs = new ArrayList<>(models.size());
        models.forEach(v -> {
            AuthDataEntity authDataEntity = authDataCopyMap.bToA(v);
            setUserInfo(authDataEntity);
            authLogs.add(JSONObject.toJSONString(authDataEntity, JSONWriter.Feature.WriteMapNullValue));
        });
        stringRedisTemplate.opsForList().leftPushAll(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX, authLogs);
        // 触发入库
        triggerAuthLog();
    }


    @Override
    public void saveOperate(OperateLogModel model) throws RuntimeException {
        OperateEntity operateLogModel = operateCopyMap.bToA(model);
        setUserInfo(operateLogModel);
        stringRedisTemplate.opsForList().leftPush(LoggingCommonConstant.OPERATE_LOG_KEY_PREFIX, JSONObject.toJSONString(operateLogModel, JSONWriter.Feature.WriteMapNullValue));
        // 触发入库
        triggerOperateLog();
    }


    @Override
    public void saveOperateBatch(List<OperateLogModel> models) throws RuntimeException {
        List<String> operateLogs = new ArrayList<>(models.size());
        models.forEach(v -> {
            OperateEntity operateLogModel = operateCopyMap.bToA(v);
            setUserInfo(operateLogModel);
            operateLogs.add(JSONObject.toJSONString(operateLogModel, JSONWriter.Feature.WriteMapNullValue));
        });
        stringRedisTemplate.opsForList().leftPushAll(LoggingCommonConstant.OPERATE_LOG_KEY_PREFIX, operateLogs);
        // 触发入库
        triggerOperateLog();
    }


    /**
     * 触发授权日志入库
     *
     * @author 安一老厨
     * @date 2022-08-13 11:10
     */
    private void triggerAuthLog() {
        authDataService.storage();
    }


    /**
     * 触发操作日志入库
     *
     * @author 安一老厨
     * @date 2022-08-13 11:10
     */
    private void triggerOperateLog() {
        operateService.storage();
    }


    private <T extends BaseEntity> void setUserInfo(T data) {
        data.setCreateTime(LocalDateTime.now());
        try {
            UserInfo userInfo = UserContextUtils.getUserInfo();
            data.setCreateUserId(userInfo.getUserId());
            data.setCreateAreaCode(userInfo.getCurrentAreaCode());
            data.setCreateUserName(userInfo.getUserName());
            data.setCreateOrgSysCode(userInfo.getCurrentOrgCode());
        } catch (Exception ignore) {
        }
    }
}
