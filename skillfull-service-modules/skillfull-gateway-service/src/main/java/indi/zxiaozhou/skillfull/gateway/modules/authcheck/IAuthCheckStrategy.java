package indi.zxiaozhou.skillfull.gateway.modules.authcheck;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import org.springframework.web.server.ServerWebExchange;

import java.util.Set;

/**
 * 接收子系统数据
 *
 * @author zxiaozhou
 * @date 2020-12-22 16:20
 * @since JDK1.8
 */
public interface IAuthCheckStrategy {
    /**
     * 检测按钮权限
     *
     * @param systemAction ${@link ActionModel} 系统按钮权限配置
     * @param exchange     ${@link ServerWebExchange}
     * @param userAction   ${@link ActionModel} 当前用户按钮权限配置
     * @return Object 结果
     * @author zxiaozhou
     * @date 2020-12-22 16:33
     */
    AuthorizeCheckResult checkAuth(ActionModel systemAction, ActionModel userAction, ServerWebExchange exchange);


    /**
     * 检测请求方法
     *
     * @param systemAction ${@link ActionModel}
     * @param userAction   ${@link ActionModel}
     * @param exchange     ${@link ServerWebExchange}
     * @param result       ${@link AuthorizeCheckResult}
     * @author zxiaozhou
     * @date 2021-10-02 22:46
     */
    default void checkActionMethod(ActionModel systemAction, ActionModel userAction, ServerWebExchange exchange, AuthorizeCheckResult result) {
        System.out.println("----------检测按钮请求方法");
        // 检测请求方法
        if (result.isResult() && systemAction.isActionLimitMethod() && CollectionUtil.isNotEmpty(systemAction.getActionMethodSet())) {
            String method = exchange.getRequest().getMethodValue();
            if (!systemAction.getActionMethodSet().contains(method.toUpperCase())) {
                result.setResult(true);
                result.setErrorMsg("");
                result.setErrorCode(Status.SUCCESS);
            } else {
                if (!userAction.isActionLimitMethod() || CollectionUtil.isNotEmpty(userAction.getActionMethodSet())) {
                    result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
                    result.setErrorCode(Status.ACCESS_DENIED);
                    result.setResult(false);
                } else {
                    Set<String> systemMethods = systemAction.getActionMethodSet();
                    Set<String> userMethods = userAction.getActionMethodSet();
                    systemMethods.retainAll(userMethods);
                    if (CollectionUtil.isNotEmpty(systemMethods)) {
                        result.setResult(true);
                        result.setErrorMsg("");
                        result.setErrorCode(Status.SUCCESS);
                    } else {
                        result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
                        result.setErrorCode(Status.ACCESS_DENIED);
                        result.setResult(false);
                    }
                }
            }
        }
    }
}
