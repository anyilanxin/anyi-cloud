// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corewebflux.utils;

import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.socket.HandshakeInfo;

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
     * 获取token
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-02-04 10:53
     */
    public static String getSubToken() {
        return getSubToken(getServerHttpRequest());
    }


    /**
     * 获取请求头token
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-07 09:56
     */
    public static String getSubToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(SysBaseConstant.SUB_HEADER_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getQueryParams().getFirst(SysBaseConstant.SUB_QUERY_TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            token = "";
        }
        return token;
    }


    /**
     * 获取请求头token
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-07 09:56
     */
    public static String getSubToken(HandshakeInfo handshakeInfo) {
        String token = handshakeInfo.getHeaders().getFirst(SysBaseConstant.SUB_HEADER_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = CoreCommonUtils.getQueryMap(handshakeInfo.getUri().getQuery()).get(SysBaseConstant.SUB_QUERY_TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            token = "";
        }
        return token;
    }
}
