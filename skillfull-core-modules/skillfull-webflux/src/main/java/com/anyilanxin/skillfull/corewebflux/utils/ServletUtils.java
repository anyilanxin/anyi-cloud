/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
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
