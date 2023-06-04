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
package com.anyilanxin.anyicloud.coremvc.utils;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * core mvc servlet util
 *
 * @author zxh
 * @date 2020-10-07 09:24
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServletUtils {
    private static final String UNKNOWN = "unknown";
    private static ServletUtils utils;
    private final HttpServletRequest request;

    /**
     * clone HttpServletRequest
     *
     * @param request ${@link HttpServletRequest}
     * @return BodyReaderRequestWrapper ${@link BodyReaderRequestWrapper}
     * @author zxh
     * @date 2021-01-13 02:06
     */
    public static BodyReaderRequestWrapper cloneRequest(HttpServletRequest request) {
        if (isAjaxRequest(request)) {
            try {
                return new BodyReaderRequestWrapper(request);
            } catch (IOException e) {
                log.error("------------ServletUtils------创建BodyReaderRequestWrapper失败------>cloneRequest--->\n异常消息:{}", e.getMessage());
            }
        }
        return null;
    }


    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return Objects.nonNull(getRequestAttributes()) ? getRequestAttributes().getRequest() : null;
    }


    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return Objects.nonNull(getRequestAttributes()) ? getRequestAttributes().getResponse() : null;
    }


    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return Objects.nonNull(getRequest()) ? getRequest().getSession() : null;
    }


    public static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 是否是Ajax异步请求
     *
     * @param request ${@link HttpServletRequest}
     * @author zxh
     * @date 2021-01-13 01:53
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (Objects.nonNull(request)) {
            String contentType = request.getContentType();
            if (StringUtils.isNotBlank(contentType) && contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.debug("------------IPUtils------IPUtils ERROR------>getIpAddr:{}", e);
        }
        return ip;
    }


    /**
     * 获取ip信息
     *
     * @return String ${@link String} ip信息
     * @author zxh
     * @date 2020-10-22 15:08
     */
    public static String getIpAddr() {
        return getIpAddr(utils.request);
    }


    /**
     * 获取浏览器user agent信息
     *
     * @return String ${@link String}
     * @author zxh
     * @date 2020-11-02 12:10
     */
    public static String getUserAgent() {
        return getUserAgent(utils.request);
    }


    /**
     * 获取浏览器user agent信息
     *
     * @param request ${@link HttpServletRequest}
     * @return String ${@link String}
     * @author zxh
     * @date 2020-11-02 12:10
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }


    @PostConstruct
    private void init() {
        utils = this;
    }
}
