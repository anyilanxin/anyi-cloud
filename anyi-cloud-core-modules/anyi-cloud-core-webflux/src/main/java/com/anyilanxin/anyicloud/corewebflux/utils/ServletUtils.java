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
package com.anyilanxin.anyicloud.corewebflux.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * core mvc servlet util
 *
 * @author zxh
 * @date 2020-10-07 09:24
 * @since 1.0.0
 */
@Slf4j
public class ServletUtils {
    public static final ThreadLocal<ServerHttpRequest> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 本地线程保存ServerHttpRequest
     *
     * @param request ${@link ServerHttpRequest}
     * @author zxh
     * @date 2021-02-04 10:32
     */
    public static void setServerHttpRequest(ServerHttpRequest request) {
        THREAD_LOCAL.set(request);
    }


    /**
     * 本地线程获取ServerHttpRequest同时删除
     *
     * @return ServerHttpRequest ${@link ServerHttpRequest}
     * @author zxh
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
     * @author zxh
     * @date 2021-02-04 10:32
     */
    public static ServerHttpRequest getServerHttpRequest() {
        return THREAD_LOCAL.get();
    }


    /**
     * 本地线程变量删除数据
     *
     * @author zxh
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
     * @author zxh
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
