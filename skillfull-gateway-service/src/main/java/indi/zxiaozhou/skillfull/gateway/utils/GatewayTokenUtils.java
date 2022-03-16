// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.utils;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.ConfigTokenModel;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static indi.zxiaozhou.skillfull.gateway.core.constant.CommonGatewayConstant.GATEWAY_TOKEN_UNIFIED;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 网关token处理工具类
 *
 * @author zxiaozhou
 * @date 2021-07-28 23:04
 * @since JDK1.8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GatewayTokenUtils {
    private static GatewayTokenUtils utils;
    private final ConfigTokenModel configTokenModel;

    /**
     * 子服务统一请求token
     *
     * @param exchange ${@link ServerWebExchange}
     * @return ServerHttpRequest ${@link ServerHttpRequest}
     * @author zxiaozhou
     * @date 2021-06-05 16:15
     */
    public static ServerHttpRequest unifiedToken(ServerWebExchange exchange) {
        URI uri = exchange.getRequest().getURI();
        String query = uri.getQuery();
        Map<String, String> queryMap = CoreCommonUtils.getQueryMap(query);
        String token = queryMap.remove(utils.configTokenModel.getTokenQueryKey());
        // 如果token在请求参数中则统一请求参数否则统一请求头
        if (StringUtils.isNotBlank(token)) {
            queryMap.put(SysBaseConstant.SUB_QUERY_TOKEN_KEY, token);
            String newQueryParam = CoreCommonUtils.queryToString(queryMap);
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .replaceQuery(newQueryParam)
                    .build(true)
                    .toUri();
            exchange.getAttributes().put(GATEWAY_TOKEN_UNIFIED, true);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newUri);
            return exchange.getRequest();
        } else {
            return exchange.getRequest().mutate()
                    .headers(httpHeaders -> {
                        List<String> authorization = httpHeaders.remove(utils.configTokenModel.getTokenHeaderKey());
                        if (CollectionUtil.isNotEmpty(authorization)) {
                            String authToken = authorization.get(0);
                            String bearerToken = authToken.replaceFirst(utils.configTokenModel.getTokenHeaderStartWith(), "");
                            httpHeaders.add(SysBaseConstant.SUB_HEADER_TOKEN_KEY, bearerToken);
                            exchange.getAttributes().put(GATEWAY_TOKEN_UNIFIED, true);
                        } else {
                            if (StringUtils.isNotBlank(token)) {
                                httpHeaders.add(SysBaseConstant.SUB_HEADER_TOKEN_KEY, token);
                                exchange.getAttributes().put(GATEWAY_TOKEN_UNIFIED, true);
                            }
                        }
                    }).build();
        }
    }

    @PostConstruct
    private void init() {
        utils = this;
    }

}
