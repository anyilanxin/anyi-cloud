// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.constant.impl.AuthorizedGrantTypes;
import com.anyilanxin.skillfull.coremvc.utils.ServletUtils;
import com.anyilanxin.skillfull.loggingcommon.model.AuthLogModel;
import com.anyilanxin.skillfull.loggingcommon.utils.LogUtils;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullAccessToken;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * oauth2 授权日志处理
 *
 * @author zxiaozhou
 * @date 2022-08-13 19:27
 * @since JDK11
 */
public class Oauth2LogUtils {
    private final static String AUTH_LOG_CONTENT_KEY = "AUTH_LOG_CONTENT";


    /**
     * 前置提交授权日志
     *
     * @param client
     * @param tokenRequest
     * @author zxiaozhou
     * @date 2022-08-13 19:55
     */
    public static void setPreAuthLog(ClientDetails client, TokenRequest tokenRequest) {
        AuthLogModel authLogModel = getAuthLogModel();
        if (Objects.nonNull(authLogModel)) {
            authLogModel.setAuthType(tokenRequest.getGrantType());
            AuthorizedGrantTypes byType = AuthorizedGrantTypes.getByType(tokenRequest.getGrantType());
            if (Objects.nonNull(byType)) {
                authLogModel.setAuthTypeDescribe(byType.getDescribe());
            }
            if (StringUtils.isBlank(authLogModel.getAuthType())) {
                authLogModel.setAuthType("unknown");
                authLogModel.setAuthTypeDescribe("未知授权类型");
            }
            String requestId = ServletUtils.getRequest().getHeader(CommonCoreConstant.X_REQUEST_ID);
            if (StringUtils.isNotBlank(requestId)) {
                authLogModel.setLogCode(requestId);
            }
            authLogModel.setAuthClientCode(client.getClientId());
            authLogModel.setRequestIp(ServletUtils.getIpAddr());
            authLogModel.setRequestStartTime(LocalDateTime.now());
        }
    }


    /**
     * 获取authLog
     *
     * @return AuthLogModel
     * @author zxiaozhou
     * @date 2022-08-13 20:45
     */
    public static AuthLogModel getAuthLogModel() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (Objects.nonNull(request)) {
            Object attribute = request.getAttribute(AUTH_LOG_CONTENT_KEY);
            AuthLogModel authLogModel;
            if (Objects.isNull(attribute)) {
                authLogModel = new AuthLogModel();
                request.setAttribute(AUTH_LOG_CONTENT_KEY, authLogModel);
            } else {
                authLogModel = (AuthLogModel) attribute;
            }
            return authLogModel;
        }
        return null;
    }


    /**
     * 设置用户信息
     *
     * @param userDetails
     * @author zxiaozhou
     * @date 2022-08-13 20:59
     */
    public static void setUserDetailInfo(SkillFullUserDetails userDetails) {
        AuthLogModel authLogModel = Oauth2LogUtils.getAuthLogModel();
        if (Objects.nonNull(authLogModel) && Objects.nonNull(userDetails)) {
            authLogModel.setAuthUserId(userDetails.getUserId());
            authLogModel.setAuthUserName((StringUtils.isNotBlank(userDetails.getRealName())) ? userDetails.getRealName() : StringUtils.isNotBlank(userDetails.getNickName()) ? userDetails.getNickName() : userDetails.getUsername());
        }
    }


    /**
     * 设置客户端信息
     *
     * @param clientDetailInfo
     * @author zxiaozhou
     * @date 2022-08-13 21:00
     */
    public static void setClientDetailInfo(SkillFullClientDetails clientDetailInfo) {
        AuthLogModel authLogModel = Oauth2LogUtils.getAuthLogModel();
        if (Objects.nonNull(authLogModel) && Objects.nonNull(clientDetailInfo)) {
            authLogModel.setAuthClientCode(clientDetailInfo.getClientId());
            authLogModel.setAuthClientName(clientDetailInfo.getClientName());
        }
    }


    /**
     * 后置提交授权日志并发送大日志服务
     *
     * @param success
     * @param msg
     * @param errMsg
     * @author zxiaozhou
     * @date 2022-08-13 19:56
     */
    public static void setPostAuthLog(boolean success, String msg, String errMsg, SkillFullAccessToken token) {
        AuthLogModel authLogModel = getAuthLogModel();
        if (Objects.nonNull(authLogModel)) {
            authLogModel.setRequestEndTime(LocalDateTime.now());
            authLogModel.setAuthStatus(success ? 1 : 0);
            authLogModel.setLogData(msg);
            authLogModel.setExceptionMessage(errMsg);
            if (Objects.nonNull(token)) {
                authLogModel.setLogData(JSONObject.toJSONString(token, SerializerFeature.WriteMapNullValue));
            }
            LogUtils.sendAuthLog(authLogModel);
        }
    }
}
