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

package com.anyilanxin.anyicloud.coremvc.utils;

import com.anyilanxin.anyicloud.coremvc.config.properties.CoreWebMvcProperty;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * core mvc公共工具类
 *
 * @author zxh
 * @date 2021-08-19 16:34
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class CoreMvcCommonUtils {
    private static CoreMvcCommonUtils utils;
    private final StringRedisTemplate stringRedisTemplate;
    private final CoreWebMvcProperty property;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 创建redis 服务级锁
     *
     * @param key     ${@link String}
     * @param timeout ${@link Long} 过期时间(单位:s),默认10s,只能大于0，当小于等于0时为10s
     * @author zxh
     * @date 2021-08-19 18:06
     */
    public static boolean createRedisServiceLock(String key, long timeout) {
        if (timeout <= 0) {
            timeout = 10L;
        }
        if (StringUtils.isNotBlank(key)) {
            String timeoutKey = utils.property.getServiceName() + "_" + key;
            Boolean ifAbsent = utils.stringRedisTemplate.opsForValue().setIfAbsent(timeoutKey, "阻拦其他客户端消费该消息");
            if (Objects.nonNull(ifAbsent) && Boolean.FALSE.equals(ifAbsent)) {
                return true;
            }
            utils.stringRedisTemplate.opsForValue().set(timeoutKey, timeoutKey + "超时key", timeout, TimeUnit.SECONDS);
            return false;
        }
        return true;
    }
}
