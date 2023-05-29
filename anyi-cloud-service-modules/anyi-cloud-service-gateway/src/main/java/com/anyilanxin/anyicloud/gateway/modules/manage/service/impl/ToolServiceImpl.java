

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
