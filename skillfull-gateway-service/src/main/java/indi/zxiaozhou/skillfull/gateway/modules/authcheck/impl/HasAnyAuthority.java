package indi.zxiaozhou.skillfull.gateway.modules.authcheck.impl;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.constant.ActionConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.IAuthCheckStrategy;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashSet;
import java.util.Set;

/**
 * 拥有任意一个指定的权限
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Component(ActionConstant.HAS_ANY_AUTHORITY)
@Slf4j
public class HasAnyAuthority implements IAuthCheckStrategy {
    @Override
    public AuthorizeCheckResult checkAuth(ActionModel systemAction, ActionModel userAction, ServerWebExchange exchange) {
        log.info("------------HasAnyAuthority--------99999999999---->checkAuth:{}", "权限");
        Set<String> systemActions = new HashSet<>(systemAction.getActionSet());
        Set<String> userActions = new HashSet<>(userAction.getActionSet());
        systemActions.retainAll(userActions);
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
        result.setErrorCode(Status.ACCESS_DENIED);
        if (CollectionUtil.isNotEmpty(systemActions)) {
            result.setResult(true);
            result.setErrorMsg("");
            result.setErrorCode(Status.SUCCESS);
        }
        // 检测请求方法
//        checkActionMethod(systemAction, userAction, exchange, result);
        return result;
    }
}
