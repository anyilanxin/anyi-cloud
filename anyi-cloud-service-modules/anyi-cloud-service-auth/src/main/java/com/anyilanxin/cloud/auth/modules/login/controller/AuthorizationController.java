/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.cloud.auth.modules.login.controller;


import com.anyilanxin.cloud.auth.modules.login.service.IAuthorizationService;
import com.anyilanxin.cloud.corecommon.annotation.AutoLog;
import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

/**
 * 认证服务器相关自定接口
 *
 * @author vains
 */
@Controller
@RequiredArgsConstructor
public class AuthorizationController extends AnYiBaseController {
    private final IAuthorizationService service;

    @GetMapping("/activate")
    public String activate(@RequestParam(value = "user_code", required = false) String userCode) {
        return service.activate(userCode);
    }


    @GetMapping("/activate/redirect")
    public String activateRedirect(@RequestParam(value = "user_code", required = false) String userCode) {
        return service.activateRedirect(userCode);
    }


    @GetMapping("/activated")
    public String activated() {
        return "device-activated";
    }


    @GetMapping(value = "/", params = "success")
    public String success() {
        return "device-activated";
    }


    @ResponseBody
    @GetMapping(value = "/")
    public String index() {
        return service.index();
    }


    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        return service.login(model, session);
    }


    @GetMapping(value = "/oauth2/consent")
    public String consent(Principal principal, Model model,
                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                          @RequestParam(OAuth2ParameterNames.STATE) String state,
                          @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        return service.consent(principal, model, clientId, scope, state, userCode);
    }


    @SneakyThrows
    @ResponseBody
    @GetMapping(value = "/oauth2/consent/redirect")
    public AnYiResult<String> consentRedirect(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                              @RequestParam(OAuth2ParameterNames.STATE) String state,
                                              @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                              @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        return ok(service.consentRedirect(request, response, scope, state, clientId, userCode));
    }


    @ResponseBody
    @GetMapping(value = "/oauth2/consent/parameters")
    public AnYiResult<Map<String, Object>> consentParameters(Principal principal,
                                                             @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                                             @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                                             @RequestParam(OAuth2ParameterNames.STATE) String state,
                                                             @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {
        return ok(service.consentParameters(principal, clientId, scope, state, userCode));
    }


    @Operation(summary = "取消授权", tags = {"v1.0.0"}, description = "取消授权")
    @GetMapping(value = "/oauth/logout")
    @PreAuthorize("isAnonymous()")
    @ResponseBody
    public AnYiResult<String> logOut() {
        service.logOut();
        return ok("取消授权成功");
    }


    @Operation(summary = "获取当前用户信息", tags = {"v1.0.0"}, description = "获取当前用户信息")
    @GetMapping(value = "/oauth/user-info")
    @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    @ResponseBody
    public AnYiResult<AnYiUserInfo> getUserInfo(@RequestParam(required = false) String orgId) {
        return ok(service.getUserInfo(orgId));
    }

}
