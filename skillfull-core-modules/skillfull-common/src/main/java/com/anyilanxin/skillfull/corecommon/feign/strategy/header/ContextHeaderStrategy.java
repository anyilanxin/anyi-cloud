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

package com.anyilanxin.skillfull.corecommon.feign.strategy.header;

import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import feign.RequestTemplate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token设置策略上下文
 *
 * @author zxiaozhou
 * @date 2019-02-03 21:33
 * @since JDK11
 */
@Component
@Slf4j
public class ContextHeaderStrategy {
    private static final Map<String, ISetHeaderStrategy> HEADER_STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public ContextHeaderStrategy(Map<String, ISetHeaderStrategy> headerStrategy) {
        HEADER_STRATEGY.putAll(headerStrategy);
    }


    public void setHeader(String strategy, RequestTemplate template) {
        // 先调用默认的
        ISetHeaderStrategy iSetHeaderStrategy = HEADER_STRATEGY.get(SysBaseConstant.FEIGN_DEFAULT);
        if (Objects.nonNull(iSetHeaderStrategy)) {
            try {
                iSetHeaderStrategy.setHeader(template);
            } catch (Exception e) {
                log.error("------------ContextHeaderStrategy-----feign设置请求头异常------->setHeader--->异常消息:{}", e.getMessage());
            }
        }
        // 在调用其他的
        ISetHeaderStrategy setTokenStrategy = HEADER_STRATEGY.get(strategy);
        if (setTokenStrategy != null) {
            try {
                setTokenStrategy.setHeader(template);
            } catch (Exception e) {
                log.error("------------ContextHeaderStrategy-----feign设置请求头异常------->setHeader--->异常消息:{}", e.getMessage());
            }
        } else {
            log.error("----------ContextStrategy---------->doStrategy:{}", "Api调用未找到设置请求头的方法");
        }
    }
}
