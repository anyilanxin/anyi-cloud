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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
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
package com.anyilanxin.anyicloud.gateway.modules.manage.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.model.system.ConfigDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.model.system.UserDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonDateUtils;
import com.anyilanxin.anyicloud.corecommon.utils.encryption.RSAUtils;
import com.anyilanxin.anyicloud.corewebflux.utils.CoreWebFluxStringUtils;
import com.anyilanxin.anyicloud.gateway.modules.manage.service.IToolService;
import com.anyilanxin.anyicloud.gateway.modules.manage.service.mapstruct.SecurityToWebSecurityMap;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 工具类服务实现
 *
 * @author zxh
 * @date 2020-09-28 10:04
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ToolServiceImpl implements IToolService {
    private static final long WAIT_INVALID_SECONDS = 60 * 4;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SecurityToWebSecurityMap securityMap;
    private final ConfigDataSecurityModel configDataSecurityModel;

    @Override
    public WebSecurityModel getBaseSecurity() {
        UserDataSecurityModel userDataSecurityModel = getSecurityModel(null);
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + userDataSecurityModel.getSerialNumber(), userDataSecurityModel, userDataSecurityModel.getValidityInSeconds(), TimeUnit.SECONDS);
        return securityMap.eToD(userDataSecurityModel);
    }


    @Override
    public WebSecurityModel getRefreshBaseSecurity(String serialNumber) {
        // 旧数据移到待失效区,并设置更短的快速失效时间
        Object waitInvalidData = redisTemplate.opsForValue().get(CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + serialNumber);
        if (Objects.nonNull(waitInvalidData) && waitInvalidData instanceof UserDataSecurityModel) {
            redisTemplate.opsForValue().set(CoreCommonCacheConstant.USER_DATA_SECURITY_WAIT_INVALID_CACHE + serialNumber, waitInvalidData, WAIT_INVALID_SECONDS, TimeUnit.SECONDS);
            redisTemplate.delete(CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + serialNumber);
        } else {
            throw new ResponseException(Status.VERIFICATION_FAILED, "未查询到当前序列的数据");
        }
        UserDataSecurityModel userDataSecurityModel = getSecurityModel(serialNumber);
        // 加入缓存
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + userDataSecurityModel.getSerialNumber(), userDataSecurityModel, userDataSecurityModel.getValidityInSeconds(), TimeUnit.SECONDS);
        return securityMap.eToD(userDataSecurityModel);
    }


    /**
     * 创建新的密钥信息
     *
     * @return SecurityModel ${@link UserDataSecurityModel}
     * @author zxh
     * @date 2021-07-13 17:16
     */
    private UserDataSecurityModel getSecurityModel(String serialNumber) {
        RSAUtils.RsaKey rsaKey = RSAUtils.getRsaKey();
        UserDataSecurityModel userDataSecurityModel = securityMap.vToE(configDataSecurityModel);
        userDataSecurityModel.setBase64PrivateKey(rsaKey.getBase64PrivateKey());
        userDataSecurityModel.setBase64PublicKey(rsaKey.getBase64PublicKey());
        LocalDateTime expiresAt = Instant.ofEpochMilli(System.currentTimeMillis() + userDataSecurityModel.getValidityInSeconds() * 1000).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        userDataSecurityModel.setExpiresAt(expiresAt);
        if (StringUtils.isNotBlank(serialNumber)) {
            userDataSecurityModel.setSerialNumber(serialNumber);
        } else {
            String timeInfo = CoreCommonDateUtils.dateToStr(LocalDateTime.now(), CoreCommonDateUtils.YYYYMMDDHHMMSS);
            userDataSecurityModel.setSerialNumber(timeInfo + CoreWebFluxStringUtils.getSnowflakeId());
        }
        return userDataSecurityModel;
    }
}
