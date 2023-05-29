/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.feign;

import com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant;
import com.anyilanxin.anyicloud.corecommon.feign.strategy.header.ContextHeaderStrategy;
import com.anyilanxin.anyicloud.corecommon.feign.strategy.safety.ContextSafetyStrategy;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Feign拦截器
 *
 * @author zxh
 * @date 2019-04-24 14:19
 * @since 1.0.0
 */
@Component
@Slf4j
public class FeignInterceptorCommon implements RequestInterceptor {
    private ContextHeaderStrategy contextHeaderStrategy;
    private ContextSafetyStrategy contextSafetyStrategy;

    /**
     * 本处不能使用构造器注入,因为会造成feign拦截器失效
     *
     * @param contextHeaderStrategy ${@link ContextHeaderStrategy} 请求头设置策略上下文
     * @author zxh
     * @date 2019-05-15 23:00
     */
    @Autowired
    private void setContextHeaderStrategy(final ContextHeaderStrategy contextHeaderStrategy) {
        this.contextHeaderStrategy = contextHeaderStrategy;
    }


    /**
     * 本处不能使用构造器注入,因为会造成feign拦截器失效
     *
     * @param contextSafetyStrategy ${@link ContextSafetyStrategy} 数据安全处理策略上下文
     * @author zxh
     * @date 2019-06-14 13:39
     */
    @Autowired
    private void setContextSafetyStrategy(final ContextSafetyStrategy contextSafetyStrategy) {
        this.contextSafetyStrategy = contextSafetyStrategy;
    }


    /**
     * token设置
     *
     * @param template ${@link RequestTemplate}
     * @author zxh
     * @date 2019-05-15 17:52
     */
    @Override
    public void apply(RequestTemplate template) {
        // 获取基本参数
        Map<String, String> strategies = getParams(template);
        contextHeaderStrategy.setHeader(SysBaseConstant.FEIGN_DEFAULT, template);
        // 处理请求头设置
        String headerStrategy = strategies.get("headerStrategy");
        if (StringUtils.isNotBlank(headerStrategy)) {
            contextHeaderStrategy.setHeader(headerStrategy, template);
        }
        // 处理数据安全
        String safetyStrategy = strategies.get("safetyStrategy");
        if (StringUtils.isNotBlank(safetyStrategy)) {
            contextSafetyStrategy.handleSafety(safetyStrategy, template);
        }
    }


    /**
     * 获取策略基本信息
     *
     * @param template ${@link RequestTemplate}
     * @return Map ${@link Map}
     * @author zxh
     * @date 2019-06-14 12:11
     */
    private Map<String, String> getParams(RequestTemplate template) {
        Map<String, Collection<String>> queries = new LinkedHashMap<>(template.queries());
        Map<String, String> strategies = new HashMap<>(2);
        if (!queries.isEmpty()) {
            // 获取请求头设置策略
            Collection<String> headerStrategy = queries.remove("headerStrategy");
            if (!CollectionUtils.isEmpty(headerStrategy)) {
                String strategy = new ArrayList<>(headerStrategy).get(0);
                if (StringUtils.isNotBlank(strategy)) {
                    log.debug("----------FeignInterceptor---------->getParams:{}", "执行feign拦截器,当前需要设置请求头,调用策略" + strategy);
                    strategies.put("headerStrategy", strategy);
                }
            }
            // 获取数据安全设置策略
            Collection<String> safetyStrategy = queries.remove("safetyStrategy");
            if (!CollectionUtils.isEmpty(safetyStrategy)) {
                String strategy = new ArrayList<>(safetyStrategy).get(0);
                if (StringUtils.isNotBlank(strategy)) {
                    log.debug("----------FeignInterceptor---------->getParams:{}", "执行feign拦截器,当前数据需要安全处理,调用策略" + strategy);
                    strategies.put("safetyStrategy", strategy);
                }
            }
        }
        // 重置请求地址参数
        template.queries(null);
        template.queries(queries);
        return strategies;
    }
}
