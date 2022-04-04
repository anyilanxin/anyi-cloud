package indi.zxiaozhou.skillfull.gateway.modules.authcheck.impl;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.constant.ActionConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.IAuthCheckStrategy;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

/**
 * 拥有任意一个指定Ip
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Component(ActionConstant.HAS_ANY_IP_ADDRESS)
public class HasAnyIpAddress implements IAuthCheckStrategy {
    @Override
    public AuthorizeCheckResult checkAuth(ActionModel systemAction, ActionModel userAction, ServerWebExchange exchange) {
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
        result.setErrorCode(Status.ACCESS_DENIED);
        LinkedHashSet<URI> originalUris = exchange.getRequiredAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        String originalIp = "";
        if (CollectionUtil.isNotEmpty(originalUris)) {
            URI[] uris = originalUris.toArray(new URI[]{});
            URI originalUri = uris[0];
            originalIp = originalUri.getHost();
        }
        Set<String> actionSet = systemAction.getActionSet();
        if (actionSet.contains(originalIp)) {
            result.setResult(true);
            result.setErrorMsg("");
            result.setErrorCode(Status.SUCCESS);
        }
        // 检测请求方法
//        checkActionMethod(systemAction, userAction, exchange, result);
        return result;
    }
}
