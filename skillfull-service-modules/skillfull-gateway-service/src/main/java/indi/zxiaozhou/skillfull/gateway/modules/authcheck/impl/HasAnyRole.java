package indi.zxiaozhou.skillfull.gateway.modules.authcheck.impl;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.RoleModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.ActionConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.gateway.core.constant.CommonGatewayConstant;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.IAuthCheckStrategy;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 拥有任意一个指定角色
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Component(ActionConstant.HAS_ANY_ROLE)
public class HasAnyRole implements IAuthCheckStrategy {
    @Override
    public AuthorizeCheckResult checkAuth(ActionModel systemAction, ActionModel userAction, ServerWebExchange exchange) {
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
        result.setErrorCode(Status.ACCESS_DENIED);
        LoginUserInfoModel userAndAuthModel = exchange.getAttribute(CommonGatewayConstant.GATEWAY_USER_INFO);
        if (Objects.nonNull(userAndAuthModel)) {
            Set<RoleModel> roleInfo = userAndAuthModel.getRoleInfo();
            if (CollectionUtil.isNotEmpty(roleInfo)) {
                Set<String> roleCodes = new HashSet<>(roleInfo.size());
                Set<String> systemActions = new HashSet<>(systemAction.getActionSet());
                roleInfo.forEach(v -> roleCodes.add(v.getRoleCode()));
                systemActions.retainAll(roleCodes);
                if (CollectionUtil.isNotEmpty(systemActions)) {
                    result.setResult(true);
                    result.setErrorMsg("");
                    result.setErrorCode(Status.SUCCESS);
                }
            }
        }
        // 检测请求方法
//        checkActionMethod(systemAction, userAction, exchange, result);
        return result;
    }
}
