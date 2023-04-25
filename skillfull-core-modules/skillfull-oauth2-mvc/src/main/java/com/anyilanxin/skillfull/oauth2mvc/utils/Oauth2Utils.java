package com.anyilanxin.skillfull.oauth2mvc.utils;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.oauth2mvc.config.properties.CustomSecurityProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * oauth2工具类
 *
 * @author zxiaozhou
 * @date 2022-03-01 23:57
 * @since JDK1.8
 */
public class Oauth2Utils {
    /**
     * 获取白名单访问的接口
     *
     * @return Set<String> ${@link Set <String>}
     * @author zxiaozhou
     * @date 2022-02-19 13:11
     */
    public static Set<WhiteListInfo> getWhiteList(ApplicationContext applicationContext, CustomSecurityProperties properties) {
        String active = properties.getActive();
        // 处理匿名用户访问
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<WhiteListInfo> whiteList = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            Secured secured = handlerMethod.getMethodAnnotation(Secured.class);
            if (Objects.isNull(preAuthorize) && Objects.isNull(secured)) {
                continue;
            }
            if (Objects.nonNull(preAuthorize) && (preAuthorize.value().contains("isAnonymous") || preAuthorize.value().contains("permitAll"))) {
                WhiteListInfo whiteListInfo = requestMappingToWhite(infoEntry.getKey());
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                    continue;
                }
            }
            if (Objects.nonNull(secured)) {
                String[] securedValues = secured.value();
                if (Objects.nonNull(securedValues) && securedValues.length > 0 && Arrays.asList(securedValues).contains("ROLE_ANONYMOUS")) {
                    WhiteListInfo whiteListInfo = requestMappingToWhite(infoEntry.getKey());
                    if (Objects.nonNull(whiteListInfo)) {
                        whiteList.add(whiteListInfo);
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(properties.getCommonWhiteList())) {
            properties.getCommonWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        }
        if (CommonCoreConstant.PRO.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getProWhiteList())) {
            properties.getProWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        } else if (CommonCoreConstant.TEST.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getTestWhiteList())) {
            properties.getTestWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        } else if (CommonCoreConstant.DEV.equalsIgnoreCase(active) && CollUtil.isNotEmpty(properties.getDevWhiteList())) {
            properties.getDevWhiteList().forEach(v -> {
                WhiteListInfo whiteListInfo = urlInfoToWhite(v);
                if (Objects.nonNull(whiteListInfo)) {
                    whiteList.add(whiteListInfo);
                }
            });
        }
        // 放开所有actuator端点
        WhiteListInfo whiteListInfo = new WhiteListInfo();
        whiteListInfo.setMethods(Set.of(HttpMethod.GET));
        whiteListInfo.setUrls(Set.of("/actuator/**"));
        whiteList.add(whiteListInfo);
        whiteListInfo = new WhiteListInfo();
        whiteListInfo.setMethods(Set.of(HttpMethod.GET));
        whiteListInfo.setUrls(Set.of("/actuator"));
        whiteList.add(whiteListInfo);
        return whiteList;
    }

    /**
     * 白名单信息
     *
     * @author zxiaozhou
     * @date 2022-02-23 22:12
     * @since JDK1.8
     */
    @Getter
    @Setter
    public static class WhiteListInfo {
        /**
         * url信息
         */
        private Set<String> urls;

        /**
         * url对应请求方法
         */
        private Set<HttpMethod> methods;
    }


    /**
     * RequestMappingInfo转WhiteListInfo
     *
     * @param mappingInfo ${@link RequestMappingInfo}
     * @return WhiteListInfo ${@link WhiteListInfo}
     * @author zxiaozhou
     * @date 2022-02-23 22:23
     */
    private static WhiteListInfo requestMappingToWhite(RequestMappingInfo mappingInfo) {
        PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
        if (Objects.nonNull(pathPatternsCondition) && CollUtil.isNotEmpty(pathPatternsCondition.getPatternValues())) {
            WhiteListInfo whiteListInfo = new WhiteListInfo();
            whiteListInfo.setUrls(pathPatternsCondition.getPatternValues());
            RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
            if (CollUtil.isNotEmpty(methodsCondition.getMethods())) {
                Set<RequestMethod> methods = methodsCondition.getMethods();
                Set<HttpMethod> methodSet = new HashSet<>(methods.size());
                for (RequestMethod requestMethod : methods) {
                    String name = requestMethod.name();
                    HttpMethod httpMethod = HttpMethod.resolve(name.toUpperCase());
                    if (Objects.nonNull(httpMethod)) {
                        methodSet.add(httpMethod);
                    }
                }
                if (CollUtil.isNotEmpty(methodSet)) {
                    whiteListInfo.setMethods(methodSet);
                }
            }
            return whiteListInfo;
        }
        return null;
    }


    /**
     * config配置url info to WhiteListInfo
     *
     * @param urlInfo ${@link String}
     * @return WhiteListInfo ${@link WhiteListInfo}
     * @author zxiaozhou
     * @date 2022-02-23 22:26
     */
    private static WhiteListInfo urlInfoToWhite(String urlInfo) {
        if (StringUtils.isNotBlank(urlInfo)) {
            WhiteListInfo whiteListInfo = new WhiteListInfo();
            String[] urlInfoArray = urlInfo.split(";");
            whiteListInfo.setUrls(Set.of(urlInfoArray[0]));
            if (urlInfoArray.length == 2) {
                String methodInfo = urlInfoArray[1];
                String[] methods = methodInfo.split("[,，]");
                Set<HttpMethod> methodSet = new HashSet<>(methods.length);
                for (String method : methods) {
                    if (StringUtils.isNotBlank(method)) {
                        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
                        methodSet.add(httpMethod);
                    }
                }
                if (CollUtil.isNotEmpty(methodSet)) {
                    whiteListInfo.setMethods(methodSet);
                }
            }
            return whiteListInfo;
        }
        return null;

    }

}
