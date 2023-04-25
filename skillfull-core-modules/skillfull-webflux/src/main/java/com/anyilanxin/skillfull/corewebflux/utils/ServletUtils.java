package com.anyilanxin.skillfull.corewebflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * core mvc servlet util
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:24
 * @since JDK11
 */
@Slf4j
public class ServletUtils {
    public static final ThreadLocal<ServerHttpRequest> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 本地线程保存ServerHttpRequest
     *
     * @param request ${@link ServerHttpRequest}
     * @author zxiaozhou
     * @date 2021-02-04 10:32
     */
    public static void setServerHttpRequest(ServerHttpRequest request) {
        THREAD_LOCAL.set(request);
    }

    /**
     * 本地线程获取ServerHttpRequest同时删除
     *
     * @return ServerHttpRequest ${@link ServerHttpRequest}
     * @author zxiaozhou
     * @date 2021-02-04 10:32
     */
    public static ServerHttpRequest getServerHttpRequest(boolean remove) {
        ServerHttpRequest httpRequest = getServerHttpRequest();
        if (remove) {
            removeServerHttpRequest();
        }
        return httpRequest;
    }

    /**
     * 本地线程获取ServerHttpRequest不删除
     *
     * @return ServerHttpRequest ${@link ServerHttpRequest}
     * @author zxiaozhou
     * @date 2021-02-04 10:32
     */
    public static ServerHttpRequest getServerHttpRequest() {
        return THREAD_LOCAL.get();
    }


    /**
     * 本地线程变量删除数据
     *
     * @author zxiaozhou
     * @date 2021-02-04 10:32
     */
    public static void removeServerHttpRequest() {
        THREAD_LOCAL.remove();
    }


    /**
     * 获取ip
     *
     * @param request
     * @return String
     * @author zxiaozhou
     * @date 2022-07-13 21:54
     */
    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
