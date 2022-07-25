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

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.ConfigAttribute;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.matcher.AntPathRequestMatcher;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.matcher.RequestMatcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.*;

/**
 * 权限相关工具类
 *
 * @author zxiaozhou
 * @date 2022-03-03 23:17
 * @since JDK1.8
 */
public class AuthorizeUtils {

    /**
     * 获取网关配置白名单
     */
    public static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> createWhiteList(CustomSecurityProperties securityProperties) {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> configAttributes = new LinkedHashMap<>(64);
        if (CollUtil.isNotEmpty(securityProperties.getCommonWhiteList())) {
            Set<String> whiteList = securityProperties.getCommonWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        }
        if (CommonCoreConstant.PRO.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getProWhiteList())) {
            Set<String> whiteList = securityProperties.getProWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        } else if (CommonCoreConstant.TEST.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getTestWhiteList())) {
            Set<String> whiteList = securityProperties.getTestWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        } else if (CommonCoreConstant.DEV.equalsIgnoreCase(securityProperties.getActive()) && CollUtil.isNotEmpty(securityProperties.getDevWhiteList())) {
            Set<String> whiteList = securityProperties.getDevWhiteList();
            whiteList.forEach(v -> {
                LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkedHashMap = urlToAttribute(v);
                if (CollUtil.isNotEmpty(linkedHashMap)) {
                    configAttributes.putAll(linkedHashMap);
                }
            });
        }
        return configAttributes;
    }

    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> urlToAttribute(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> configAttributeInfo = new LinkedHashMap<>();
        url = url.trim();
        String[] urlInfoArray = url.split(";");
        url = urlInfoArray[0];
        if (urlInfoArray.length == 2) {
            String methodInfo = urlInfoArray[1];
            String[] methods = methodInfo.split("[,，]");
            for (String method : methods) {
                if (StringUtils.isNotBlank(method)) {
                    HttpMethod httpMethod = HttpMethod.resolve(method.toUpperCase());
                    AntPathRequestMatcher matcher;
                    if (Objects.isNull(httpMethod)) {
                        matcher = new AntPathRequestMatcher(url);
                    } else {
                        matcher = new AntPathRequestMatcher(url, httpMethod.name());
                    }
                    List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
                    configAttributeInfo.put(matcher, configAttributes);
                } else {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
                    List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
                    configAttributeInfo.put(matcher, configAttributes);
                }
            }
        } else {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            List<ConfigAttribute> configAttributes = SecurityConfig.createList("permitAll()");
            configAttributeInfo.put(matcher, configAttributes);
        }
        return configAttributeInfo;
    }
}
