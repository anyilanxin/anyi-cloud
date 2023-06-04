/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.auth.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;
import com.anyilanxin.anyicloud.coremvc.utils.ServletUtils;
import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.utils.LogUtils;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullAccessToken;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * oauth2 授权日志处理
 *
 * @author zxh
 * @date 2022-08-13 19:27
 * @since 1.0.0
 */
public class Oauth2LogUtils {
    private static final String AUTH_LOG_CONTENT_KEY = "AUTH_LOG_CONTENT";

    /**
     * 前置提交授权日志
     *
     * @param client
     * @param tokenRequest
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
