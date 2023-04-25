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
package com.anyilanxin.skillfull.coreredis.utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
* core mvc公共工具类
*
* @author zxiaozhou
* @date 2021-08-19 16:34
* @since JDK1.8
*/
@Component
@RequiredArgsConstructor
public class CoreRedisCommonUtils {
    private static CoreRedisCommonUtils utils;
    private final Environment environment;
    private final StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    private void init() {
        utils = this;
    }

    /**
    * 创建redis锁
    *
    * @param key ${@link String}
    * @param timeout ${@link Long} 过期时间(单位:s),默认10s,只能大于0，当小于等于0时为10s
    * @author zxiaozhou
    * @date 2021-08-19 18:06
    */
    public static boolean createRedisServiceLock(String key, long timeout) {
        if (timeout <= 0) {
            timeout = 10L;
        }
        if (StringUtils.isNotBlank(key)) {
            String timeoutKey = utils.environment.getProperty("spring.application.name", "") + "_" + key;
            Boolean ifAbsent =
                    utils.stringRedisTemplate.opsForValue().setIfAbsent(timeoutKey, "阻拦其他客户端消费该消息");
            if (Objects.nonNull(ifAbsent) && Boolean.FALSE.equals(ifAbsent)) {
                return true;
            }
            utils
                    .stringRedisTemplate
                    .opsForValue()
                    .set(timeoutKey, timeoutKey + "超时key", timeout, TimeUnit.SECONDS);
            return false;
        }
        return true;
    }
}
