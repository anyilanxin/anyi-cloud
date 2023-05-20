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

package com.anyilanxin.anyicloud.corecommon.utils;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

/**
 * 灰度处理工具类
 *
 * @author zxh
 * @date 2022-01-06 19:44
 * @since 1.0.0
 */
@Slf4j
public class GrayUtil {

    /**
     * 获取灰度处理后的数据 <div> 允许灰度指标: grayIp:服务id, grayActive:服务环境(spring.profiles.active)
     * grayVersion:服务版本(spring.application.version) </div>
     *
     * @param instances ${@link List<ServiceInstance>}
     * @param request   ${@link Request}
     * @return GrayModel ${@link GrayModel}
     * @author zxh
     * @date 2022-01-06 19:47
     */
    public static GrayModel getInstance(List<ServiceInstance> instances, Request<?> request) {
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        List<ServiceInstance> grayInstances = new ArrayList<>(instances);
        boolean graySuccess = false;
        if (CollUtil.isNotEmpty(headers)) {
            List<String> headerValues = headers.get(CommonCoreConstant.GRAY_HEADER_KEY);
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
