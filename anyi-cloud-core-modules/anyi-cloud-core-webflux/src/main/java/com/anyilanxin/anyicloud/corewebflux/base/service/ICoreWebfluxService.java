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

package com.anyilanxin.anyicloud.corewebflux.base.service;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.LOCK_EXPIRES;
import static com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 公共接口定义
 *
 * @author 安一老厨
 * @date 2021-07-27 10:09
 * @since 1.0.0
 */
public interface ICoreWebfluxService {
    String REDIS_LOCK_KEY = CommonCoreConstant.SYSTEM_PREFIX + "CONSTANT_DICT_LOCK_";

    /**
     * 加载常量字典
     *
     * @param force ${@link Boolean} 是否强制刷新:true-强制立马刷新,false-如果上次刷新距离本次5分钟,当前刷新无效
     * @author 安一老厨
     * @date 2021-07-27 10:10
     */
    void loadConstantDict(boolean force);


    /**
     * 加载常量字典默认实现
     *
     * @param force         ${@link Boolean} 是否强制刷新:true-强制立马刷新,false-如果上次刷新距离本次5分钟,当前刷新无效
     * @param redisTemplate ${@link RedisTemplate} RedisTemplate实例
     * @param serviceName   ${@link String} 服务名
     * @author 安一老厨
     * @date 2021-09-15 22:02
     */
    static void loadConstantDict(boolean force, RedisTemplate<String, Object> redisTemplate, String serviceName) {
        if (!force) {
            Object redisLockValue = redisTemplate.opsForValue().get(ICoreWebfluxService.REDIS_LOCK_KEY + serviceName);
            if (Objects.nonNull(redisLockValue)) {
                return;
            }
        }
        Map<String, List<ConstantDictModel>> constantDict = CoreCommonUtils.createOrGetConstantDict(serviceName, BOOT_BASE_SCAN_PACKAGE);
        if (CollUtil.isNotEmpty(constantDict)) {
            constantDict.forEach((k, v) -> redisTemplate.opsForValue().set(CoreCommonCacheConstant.ENGINE_CONSTANT_DICT_CACHE + k, v));
        }
        redisTemplate.opsForValue().set(ICoreWebfluxService.REDIS_LOCK_KEY + serviceName, true, LOCK_EXPIRES, TimeUnit.SECONDS);
    }
}
