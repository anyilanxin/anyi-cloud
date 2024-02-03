/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.oauth2commonmvc;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.model.auth.ResourceActionModel;
import com.anyilanxin.anyicloud.oauth2common.config.AuthConfigAttributeModel;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * url动态权限配置读取redis实现
 *
 * @author zxh
 * @date 2022-03-01 15:28
 * @since 1.0.0
 */
public class RedisRequestConfigMappingService implements RequestConfigMappingService {
    private final StringRedisTemplate stringRedisTemplate;
    private final SecurityExpressionHandler<RequestAuthorizationContext> expressionHandler = AnYiSecurityAuthorizationManager.expressionHandler;
    private final AuthConfigAttributeModel authConfigAttributeModel;
    private final String applicationName;
    private final boolean isGateway;

    public RedisRequestConfigMappingService(StringRedisTemplate stringRedisTemplate,
                                            AuthConfigAttributeModel authConfigAttributeModel,
                                            String applicationName,
                                            boolean isGateway) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.authConfigAttributeModel = authConfigAttributeModel;
        this.applicationName = applicationName;
        this.isGateway = isGateway;
    }


    @Override
    public LinkedHashMap<AntPathRequestMatcher, List<Expression>> getRequestConfigMappings() {
        LinkedHashMap<AntPathRequestMatcher, List<Expression>> authConfigAttribute = authConfigAttributeModel.getAuthConfigAttribute();
        // 读取内存中的权限信息
        if (Objects.nonNull(authConfigAttribute)) {
            return authConfigAttribute;
        } else {
            authConfigAttribute = new LinkedHashMap<>();
        }
        // 从redis中加载并缓存
        String resourceActionInfos = stringRedisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_AUTH_ACTION_CACHE_PREFIX + applicationName);
        if (StringUtils.isNotBlank(resourceActionInfos)) {
            List<ResourceActionModel> resourcePermissions = JSONObject.parseObject(resourceActionInfos, new TypeReference<List<ResourceActionModel>>() {
            });
            // 读取特定资源的权限
            if (CollUtil.isNotEmpty(resourcePermissions)) {
                for (ResourceActionModel actionModel : resourcePermissions) {
                    String apiUri = actionModel.getApiUri();
                    String requestMethod = actionModel.getRequestMethod();
                    Integer authType = actionModel.getAuthType();
                    Integer requireAuth = actionModel.getRequireAuth();
                    String resourceCode = actionModel.getResourceCode();
                    RequestMethod method = RequestMethod.resolve(requestMethod);
                    AntPathRequestMatcher requestMatcher;
                    if (Objects.nonNull(method)) {
                        requestMatcher = new AntPathRequestMatcher(apiUri, method.name());
                    } else {
                        requestMatcher = new AntPathRequestMatcher(apiUri);
                    }
                    List<Expression> expressions = authConfigAttribute.get(requestMatcher);
                    if (CollUtil.isEmpty(expressions)) {
                        expressions = new ArrayList<>();
                    }
                    // 不健全直接加载白名单放过
                    if (requireAuth == 0) {
                        expressions.add(getExpression(actionModel));
                    } else {
                        if (isGateway && authType == 2) {// 仅仅网关
                            expressions.add(getExpression(actionModel));
                        } else {
                            if (isGateway && (authType == 1 || applicationName.equals(resourceCode))) { // 全局
                                expressions.add(getExpression(actionModel));
                            } else if (authType == 3 && applicationName.equals(resourceCode)) { // 仅具体服务
                                expressions.add(getExpression(actionModel));
                            }
                        }
                    }
                    authConfigAttribute.put(requestMatcher, expressions);
                }
            }
        } else {
            authConfigAttributeModel.setAuthConfigAttribute(new LinkedHashMap<>());
        }
        authConfigAttributeModel.setAuthConfigAttribute(authConfigAttribute);
        return authConfigAttribute;
    }


    public Expression getExpression(ResourceActionModel actionModel) {
        String permissionExpress = actionModel.getPermissionExpress();
        Integer requireAuth = actionModel.getRequireAuth();
        Expression expression;
        if (requireAuth == 0) {
            expression = expressionHandler.getExpressionParser().parseExpression("isAnonymous()||permitAll()");
        } else {
            expression = expressionHandler.getExpressionParser().parseExpression(permissionExpress);
        }
        return expression;
    }
}
