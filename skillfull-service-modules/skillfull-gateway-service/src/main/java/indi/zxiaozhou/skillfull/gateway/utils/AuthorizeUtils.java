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
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corewebflux.utils.ContextHolderUtils;
import indi.zxiaozhou.skillfull.gateway.core.constant.CommonGatewayConstant;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.AuthCheckContent;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.AuthorizeGatewayFilterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 鉴权工具
 *
 * @author zxiaozhou
 * @date 2021-07-28 22:44
 * @since JDK1.8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizeUtils {
    private static AuthorizeUtils utils;
    private final AuthCheckContent checkContent;

    /**
     * 权限检测
     *
     * @param config ${@link AuthorizeGatewayFilterFactory.Config}
     * @return exchange ${@link ServerWebExchange}
     * @author zxiaozhou
     * @date 2021-06-05 16:15
     */
    public static AuthorizeCheckResult checkAuth(AuthorizeGatewayFilterFactory.Config config, ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        result.setResult(false);
        if (Objects.nonNull(route)) {
            // 验证特殊url
            GatewayCommonUtils.CheckUrlInfo haveUrl = GatewayCommonUtils.isHaveUrl(exchange, config.getSpecialUrl());
            // 没有特殊url直接验证权限
            if (!haveUrl.isResult()) {
                // 验证登录与按钮
                result = checkLoginAndButton(exchange, config);
            }
            // 有特殊url
            else {
                // 如果是白名单
                if (haveUrl.getSpecialUrlType() == 1) {
                    result.setResult(haveUrl.isResult());
                    // 白名单验证不成功则验证按钮
                    if (!result.isResult()) {
                        // 验证登录与按钮
                        result = checkLoginAndButton(exchange, config);
                    }
                }
                // 如果是黑名单
                else if (haveUrl.getSpecialUrlType() == 2) {
                    result.setResult(!haveUrl.isResult());
                    // 黑名单验证成功则验证按钮
                    if (!result.isResult()) {
                        // 验证登录与按钮
                        result = checkLoginAndButton(exchange, config);
                    }
                }
                // 其余
                else {
                    result = checkLoginAndButton(exchange, config);
                }
            }

        }
        return result;
    }

    /**
     * 检测登录与按钮
     *
     * @param exchange ServerWebExchange信息
     * @param config   配置信息
     * @author zxiaozhou
     * @date 2021-09-23 09:13
     */
    private static AuthorizeCheckResult checkLoginAndButton(ServerWebExchange exchange, AuthorizeGatewayFilterFactory.Config config) {
        AuthorizeCheckResult result = checkLogin(config, exchange);
        if (result.isResult() && CollectionUtil.isNotEmpty(config.getActions())) {
            // 验证按钮权限
            result = utils.checkContent.checkAuth(config, exchange);
            if (!result.isResult()) {
                result.setErrorCode(Status.ACCESS_DENIED);
                result.setErrorMsg("当前操作未得到授权");
            }
        }
        return result;
    }

    /**
     * 检测登录权限
     *
     * @param config   ${@link AuthorizeGatewayFilterFactory.Config} 配置信息
     * @param exchange ${@link ServerWebExchange} ServerWebExchange信息
     * @return AuthCheckResult ${@link AuthorizeCheckResult} 检查结果
     * @author zxiaozhou
     * @date 2021-05-06 13:09
     */
    private static AuthorizeCheckResult checkLogin(AuthorizeGatewayFilterFactory.Config config, ServerWebExchange exchange) {
        AuthorizeCheckResult result = new AuthorizeCheckResult();
        ServerHttpRequest request = exchange.getRequest();
        if (config.getType() >= 1) {
            result.setResult(true);
            result.setErrorMsg("");
            // 获取token,请求头中获取没有再在query中获取
            String token = request.getHeaders().getFirst(SysBaseConstant.SUB_HEADER_TOKEN_KEY);
            if (StringUtils.isBlank(token)) {
                token = request.getQueryParams().getFirst(SysBaseConstant.SUB_QUERY_TOKEN_KEY);
            }
            if (StringUtils.isNotBlank(token)) {
                // 验证token是否有效
                try {
                    // 上下文放入用户信息
                    LoginUserInfoModel userAndAuthModel = ContextHolderUtils.getUserAndAuthModel(token);
                    LoginOnlineInfoModel loginOnlineInfoModel = ContextHolderUtils.getLoginOnlineInfoModel(token);
                    exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_USER_INFO, userAndAuthModel);
                    exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_USER_ONLINE, loginOnlineInfoModel);
                } catch (ResponseException e) {
                    log.error("------------AuthorizeUtils------token无效------>checkLogin--->\n异常消息:{}", e.getMessage());
                    result.setResult(false);
                    result.setErrorMsg(e.getMessage());
                    result.setErrorCode(Status.TOKEN_EXPIRED);
                    return result;
                }
            } else {
                result.setResult(false);
                result.setErrorCode(Status.TOKEN_EXPIRED);
                result.setErrorMsg("未登录,请先登录");
            }
        } else {
            result.setResult(true);
        }
        return result;
    }

    @PostConstruct
    private void init() {
        utils = this;
    }
}
