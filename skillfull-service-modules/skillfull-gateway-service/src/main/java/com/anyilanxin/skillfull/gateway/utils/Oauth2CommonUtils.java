// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.ConfigAttribute;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.matcher.AntPathRequestMatcher;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.matcher.RequestMatcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author zxiaozhou
 * @date 2022-03-03 14:19
 * @since JDK1.8
 */
public class Oauth2CommonUtils {

    /**
     * resourceAction 转换为security Attribute
     *
     * @param userAll             ${@link Boolean} 是否用全url,网关时需要使用下游全url
     * @param resourcePermissions ${@link List<ResourceActionModel>}
     * @return LinkedHashMap<RequestMatcher, Collection < ConfigAttribute>> ${@link LinkedHashMap< RequestMatcher , Collection< ConfigAttribute >>}
     * @author zxiaozhou
     * @date 2022-03-03 14:23
     */
    public static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> resourceActionToAttribute(List<ResourceActionModel> resourcePermissions, boolean userAll) {
        if (CollectionUtil.isEmpty(resourcePermissions)) {
            return new LinkedHashMap<>();
        }
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> authConfigAttributeMap = new LinkedHashMap<>(resourcePermissions.size());
        resourcePermissions.forEach(v -> {
            String requestMethodInfo = v.getRequestMethod();
            String apiUri = v.getApiUri();
            String apiUriAll = v.getApiUriAll();
            String permissionExpress = v.getPermissionExpress();
            if (v.getAuthType() != 3) {
                if (StringUtils.isNotBlank(requestMethodInfo)) {
                    String[] requestMethods = requestMethodInfo.split("[,，]");
                    for (String requestMethod : requestMethods) {
                        HttpMethod httpMethod = HttpMethod.resolve(requestMethod.toUpperCase());
                        if (Objects.nonNull(httpMethod)) {
                            AntPathRequestMatcher matcher = new AntPathRequestMatcher(userAll ? apiUriAll : apiUri, httpMethod.name());
                            List<ConfigAttribute> configAttributes = SecurityConfig.createList(permissionExpress);
                            authConfigAttributeMap.put(matcher, configAttributes);
                        }
                    }
                } else {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(userAll ? apiUriAll : apiUri);
                    List<ConfigAttribute> configAttributes = SecurityConfig.createList(permissionExpress);
                    authConfigAttributeMap.put(matcher, configAttributes);
                }
            }
        });
        return authConfigAttributeMap;
    }
}
