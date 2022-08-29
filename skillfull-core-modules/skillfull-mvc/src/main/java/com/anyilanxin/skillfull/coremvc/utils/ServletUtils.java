// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * core mvc servlet util
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:24
 * @since JDK11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServletUtils {
    private final static String UNKNOWN = "unknown";
    private static ServletUtils utils;
    private final HttpServletRequest request;

    /**
     * clone HttpServletRequest
     *
     * @param request ${@link HttpServletRequest}
     * @return BodyReaderRequestWrapper ${@link BodyReaderRequestWrapper}
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2020-10-22 15:08
     */
    public static String getIpAddr() {
        return getIpAddr(utils.request);
    }


    /**
     * 获取浏览器user agent信息
     *
     * @return String ${@link String}
     * @author zxiaozhou
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
     * @author zxiaozhou
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
