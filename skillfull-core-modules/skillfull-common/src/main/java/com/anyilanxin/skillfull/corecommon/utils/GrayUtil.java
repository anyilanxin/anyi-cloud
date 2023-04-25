package com.anyilanxin.skillfull.corecommon.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequestContext;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.GRAY_HEADER_KEY;

/**
 * 灰度处理工具类
 *
 * @author zxiaozhou
 * @date 2022-01-06 19:44
 * @since JDK1.8
 */
@Slf4j
public class GrayUtil {

    /**
     * 获取灰度处理后的数据
     * <div>
     *     允许灰度指标:
     *     grayIp:服务id,
     *     grayActive:服务环境(spring.profiles.active)
     *     grayVersion:服务版本(spring.application.version)
     * </div>
     *
     * @param instances ${@link List<ServiceInstance>}
     * @param request   ${@link Request}
     * @return GrayModel ${@link GrayModel}
     * @author zxiaozhou
     * @date 2022-01-06 19:47
     */
    public static GrayModel getInstance(List<ServiceInstance> instances, Request<?> request) {
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        List<ServiceInstance> grayInstances = new ArrayList<>(instances);
        boolean graySuccess = false;
        if (CollUtil.isNotEmpty(headers)) {
            List<String> headerValues = headers.get(GRAY_HEADER_KEY);
            if (CollUtil.isNotEmpty(headerValues)) {
                Stream<ServiceInstance> stream = grayInstances.stream().filter(v -> {
                    Map<String, String> metadata = v.getMetadata();
                    if (CollUtil.isNotEmpty(metadata)) {
                        String enableGray = metadata.get("gray-info.enable-gray");
                        if (StringUtils.isNotBlank(enableGray)) {
                            return Boolean.parseBoolean(enableGray);
                        }
                    }
                    return true;
                });
                for (String headerValue : headerValues) {
                    if (headerValue.startsWith("grayIp=")) {
                        String ip = headerValue.split("=")[1];
                        stream = stream.filter(v -> ip.equals(v.getHost()));
                    }
                    if (headerValue.startsWith("grayActive=")) {
                        String active = headerValue.split("=")[1];
                        stream = stream.filter(v -> {
                            Map<String, String> metadata = v.getMetadata();
                            if (CollUtil.isNotEmpty(metadata)) {
                                String instanceActive = metadata.get("gray-info.active");
                                if (StringUtils.isNotBlank(instanceActive)) {
                                    return instanceActive.equals(active);
                                }
                            }
                            return true;
                        });
                    }
                    if (headerValue.startsWith("grayVersion=")) {
                        String version = headerValue.split("=")[1];
                        stream = stream.filter(v -> {
                            Map<String, String> metadata = v.getMetadata();
                            if (CollUtil.isNotEmpty(metadata)) {
                                String instanceVersion = metadata.get("gray-info.version");
                                if (StringUtils.isNotBlank(instanceVersion)) {
                                    return instanceVersion.equals(version);
                                }
                            }
                            return true;
                        });
                    }
                }
                List<ServiceInstance> finallyGrayInstances = stream.collect(Collectors.toList());
                if (CollUtil.isNotEmpty(finallyGrayInstances)) {
                    graySuccess = true;
                    grayInstances = finallyGrayInstances;
                }
            }
        }
        return new GrayModel(graySuccess, grayInstances);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GrayModel {
        /**
         * 灰度实例获取是否成功
         */
        private boolean graySuccess;
        /**
         * 灰度处理后实例信息
         */
        private List<ServiceInstance> instances;
    }
}
