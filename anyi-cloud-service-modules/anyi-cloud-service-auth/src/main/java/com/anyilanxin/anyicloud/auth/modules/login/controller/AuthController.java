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
package com.anyilanxin.anyicloud.auth.modules.login.controller;

import com.anyilanxin.anyicloud.auth.modules.login.service.IAuthService;
import com.anyilanxin.anyicloud.corecommon.annotation.AutoLog;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 09:25
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "权限相关")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController extends BaseController {
    private final IAuthService service;

    @Operation(summary = "取消授权", tags = {"v1.0.0"}, description = "取消授权")
    @GetMapping(value = "/oauth/logout")
    @PreAuthorize("permitAll()")
    public Result<String> logOut() {
        service.logOut();
        return ok("取消授权成功");
    }


    @Operation(summary = "获取当前用户信息", tags = {"v1.0.0"}, description = "获取当前用户信息")
    @GetMapping(value = "/oauth/user-info")
    @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    public Result<UserInfo> getUserInfo(@RequestParam(required = false) String orgId) {
        return ok(service.getUserInfo(orgId));
    }

    // @Operation(summary = "授权异常处理", tags = {"v1.0.0"}, description = "授权异常处理")
    // @GetMapping(value = "/auth/error")
    // public Result<String> error(Map<String, String> params) {
    // log.info("------------AuthController------------>error:\n{}", params);
    // return ok("取消授权成功");
    // }
}
