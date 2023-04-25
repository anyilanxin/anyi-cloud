/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.auth.modules.login.controller;

import com.anyilanxin.skillfull.auth.modules.login.service.IAuthCodeService;
import com.anyilanxin.skillfull.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.skillfull.corecommon.annotation.AutoLog;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 授权相关
*
* @author zxiaozhou
* @date 2022-02-19 09:25
* @since JDK1.8
*/
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Code", description = "验证码相关")
@RequestMapping(value = "/code", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeController extends BaseController {
    private final IAuthCodeService service;

    @Operation(
            summary = "获取图片验证码",
            tags = {"v1.0.0"},
            description = "获取图片验证码")
    @GetMapping(value = "/picture")
    @PreAuthorize("permitAll()")
    @AutoLog(note = "获取图片验证码", type = AutoLog.QUERY)
    public Result<ValidateDto> getPictureCode(HttpServletRequest request) {
        return ok(service.getPictureCode(request));
    }

    @Operation(
            summary = "获取登录手机验证码",
            tags = {"v1.0.0"},
            description = "获取登录手机验证码(会验证手机是否存在)")
    @GetMapping(value = "/sms/{phone}")
    @PreAuthorize("permitAll()")
    @Parameter(in = ParameterIn.PATH, description = "电话号码", name = "phone", required = true)
    @AutoLog(note = "获取登录手机验证码(会验证手机是否存在)", type = AutoLog.QUERY)
    public Result<String> getPhoneSmsCode(
            @PathVariable @NotBlank(message = "手机号码不能为空") String phone, HttpServletRequest request) {
        service.getPhoneSmsCode(phone, request);
        return ok("发送短信验证码成功");
    }
}
