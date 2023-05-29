

package com.anyilanxin.anyicloud.corewebflux.feign.header;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant;
import com.anyilanxin.anyicloud.corecommon.feign.strategy.header.ISetHeaderStrategy;
import com.anyilanxin.anyicloud.corewebflux.utils.ServletUtils;
import feign.RequestTemplate;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @author zxh
 * @date 2021-05-23 01:18
 * @since 1.0.0
 */
@Component(value = SysBaseConstant.FEIGN_DEFAULT)
public class InnerAllHeaderImpl implements ISetHeaderStrategy {
    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public void setHeader(RequestTemplate template) {
        ServerHttpRequest serverHttpRequest = ServletUtils.getServerHttpRequest();
        if (Objects.isNull(serverHttpRequest)) {
            return;
        }
        Map<String, Set<String>> headers = getHeaders(serverHttpRequest);
        if (CollUtil.isNotEmpty(headers)) {
            headers.forEach((key, value) -> {
                // 需要排除Content-Length，否则造成数据传输长度异常
                if (StringUtils.isNotBlank(key) && CollUtil.isNotEmpty(value) && !key.equalsIgnoreCase(CONTENT_LENGTH)) {
                    template.header(key, value);
                }
            });
        }
    }


    /**
     * 获取请求头信息
     *
     * @param request ${@link ServerHttpRequest}
     * @return Map<String, Set < String>> ${@link Map<String, Set<String>>}
     * @author zxh
     * @date 2021-05-23 20:54
     */
    private Map<String, Set<String>> getHeaders(ServerHttpRequest request) {
        Map<String, Set<String>> map = new LinkedHashMap<>();
        HttpHeaders headers = request.getHeaders();
        headers.forEach((k, v) -> map.put(k, new HashSet<>(v)));
        return map;
    }
}
