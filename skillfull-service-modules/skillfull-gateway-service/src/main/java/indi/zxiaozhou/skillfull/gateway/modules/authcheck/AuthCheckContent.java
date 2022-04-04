package indi.zxiaozhou.skillfull.gateway.modules.authcheck;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.ActionBackendModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.ActionConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.gateway.core.constant.CommonGatewayConstant;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.AuthorizeGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 接收子系统数据上下文
 *
 * @author zxiaozhou
 * @date 2020-12-22 16:20
 * @since JDK1.8
 */
@Component
@Slf4j
public class AuthCheckContent {
    private static final Map<String, IAuthCheckStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public AuthCheckContent(final Map<String, IAuthCheckStrategy> strategyMap) {
        strategyMap.forEach(STRATEGY::put);
    }


    /**
     * 检测按钮权限
     *
     * @param exchange ${@link ServerWebExchange} 请求ServerWebExchange
     * @param config   ${@link AuthorizeGatewayFilterFactory.Config} 权限检测配置
     * @return Object 结果
     * @author zxiaozhou
     * @date 2020-12-23 10:50
     */
    public AuthorizeCheckResult checkAuth(AuthorizeGatewayFilterFactory.Config config, ServerWebExchange exchange) {
        log.info("------------AuthCheckContent------AuthorizeCheckResult------>checkAuth:{}", "AuthorizeCheckResult开始按钮权限");
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        result.setErrorMsg(Status.ACCESS_DENIED.getMessage());
        result.setErrorCode(Status.ACCESS_DENIED);
        LoginUserInfoModel userAndAuthModel = (LoginUserInfoModel) (exchange.getAttributes().get(CommonGatewayConstant.GATEWAY_USER_INFO));
        ActionBackendModel permissionAction = userAndAuthModel.getActionInfo();
        if (config.getType() == 2) {
            URI uri = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
            result.setUri(uri.getPath());
            String path = uri.getPath();
            String method = exchange.getRequest().getMethodValue();
            path = CoreCommonUtils.getUri(path);
            // 编写检测代码
            Map<String, Set<ActionModel>> actions = config.getActions();
            if (CollectionUtil.isNotEmpty(actions)) {
                Set<Map.Entry<String, Set<ActionModel>>> entries = actions.entrySet();
                boolean isHaveAction = false;
                for (Map.Entry<String, Set<ActionModel>> entry : entries) {
                    String key = entry.getKey();
                    AntPathMatcher matcher = new AntPathMatcher();
                    if (matcher.match(key, path)) {
                        isHaveAction = true;
                        List<ActionModel> systemAction = new ArrayList<>(entry.getValue());
                        String serviceId = systemAction.get(0).getServiceId();
                        Set<ActionModel> effectiveSystemActions = getEffectiveActions(entry.getValue(), method);
                        if (CollectionUtil.isEmpty(effectiveSystemActions)) {
                            result.setErrorMsg("");
                            result.setErrorCode(null);
                            result.setResult(true);
                            return result;
                        } else {
                            if (Objects.nonNull(permissionAction)) {
                                Map<String, Map<String, Set<ActionModel>>> userActions = permissionAction.getUserActions();
                                if (CollectionUtil.isNotEmpty(userActions)) {
                                    Map<String, Set<ActionModel>> stringActionModelMap = userActions.get(serviceId);
                                    log.info("------------AuthCheckContent-------stringActionModelMap----000000000----->checkAuth:\n{}", stringActionModelMap);
                                    if (CollectionUtil.isNotEmpty(stringActionModelMap)) {
                                        Set<ActionModel> userAction = stringActionModelMap.get(key);
                                        Set<ActionModel> effectiveUserActions = getEffectiveActions(userAction, method);
                                        if (CollectionUtil.isNotEmpty(effectiveUserActions)) {
                                            for (ActionModel effectiveSystemAction : effectiveSystemActions) {
                                                for (ActionModel effectiveUserAction : effectiveUserActions) {
                                                    // 如果没有找到具体的处理策略则默认HAS_AUTHORITY策略
                                                    IAuthCheckStrategy iAuthCheckStrategy = null;
                                                    if (StringUtils.isNotBlank(effectiveSystemAction.getCheckStrategy())) {
                                                        iAuthCheckStrategy = STRATEGY.get(effectiveSystemAction.getCheckStrategy());
                                                    }
                                                    if (Objects.isNull(iAuthCheckStrategy)) {
                                                        iAuthCheckStrategy = STRATEGY.get(ActionConstant.HAS_ANY_AUTHORITY);
                                                    }
                                                    AuthorizeCheckResult checkResult = iAuthCheckStrategy.checkAuth(effectiveSystemAction, effectiveUserAction, exchange);
                                                    checkResult.setUri(uri.getPath());
                                                    log.info("------------AuthCheckContent------checkResult--99999---->checkAuth:\n{}", checkResult);
                                                    if (checkResult.isResult()) {
                                                        return checkResult;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                result.setResult(!isHaveAction);
            } else {
                result.setResult(true);
            }
        } else {
            result.setResult(true);
        }
        return result;
    }


    /**
     * 获取有效的action
     *
     * @param actions ${@link Set<ActionModel>}
     * @param method  ${@link String}
     * @return Set<ActionModel> ${@link Set<ActionModel>}
     * @author zxiaozhou
     * @date 2021-10-02 23:53
     */
    private Set<ActionModel> getEffectiveActions(Set<ActionModel> actions, String method) {
        Set<ActionModel> effectiveActions = new HashSet<>(actions.size());
        actions.forEach(v -> {
            if (!v.isActionLimitMethod()) {
                effectiveActions.add(v);
            } else {
                System.out.println("----------method.toUpperCase()------" + method.toUpperCase());
                if (v.getActionMethodSet().contains(method.toUpperCase())) {
                    effectiveActions.add(v);
                }
            }
        });
        log.info("------------AuthCheckContent------------>getEffectiveActions:\n{}", effectiveActions);
        return effectiveActions;
    }
}
