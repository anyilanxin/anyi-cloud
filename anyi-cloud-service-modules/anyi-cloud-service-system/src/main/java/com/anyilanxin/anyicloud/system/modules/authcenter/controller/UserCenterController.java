/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.authcenter.controller;

import com.anyilanxin.anyicloud.corecommon.annotation.Anonymous;
import com.anyilanxin.anyicloud.corecommon.annotation.AutoLog;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteTreeModel;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePhoneVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.service.IUserCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户中心
 *
 * @author 安一老厨
 * @date 2022-05-02 09:16
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "UserCenter", description = "用户中心")
@RequestMapping(value = "/rbac-user-center", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCenterController extends BaseController {
    private final IUserCenterService service;

    @Operation(summary = "获取当前用户菜单信息", tags = {"v1.0.0"}, description = "获取当前用户菜单信息")
    @GetMapping(value = "/get/router")
    @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes")
    public Result<List<UserRouteModel>> getRouterInfo(@RequestParam(required = false) String systemCodes) {
        return ok(service.getRouterInfo(systemCodes));
    }


    @Operation(summary = "获取用户路由菜单信息(树形)", tags = {"v1.0.0"}, description = "获取用户路由菜单信息(树形)")
    @GetMapping(value = "/get/router-tree")
    @Parameter(description = "系统编码,多个英文逗号隔开,不传查询所有系统", name = "systemCodes")
    public Result<List<UserRouteTreeModel>> getRouterInfoTree(@RequestParam(required = false) String systemCodes) {
        return ok(service.getRouterInfoTree(systemCodes));
    }


    @Operation(summary = "获取用户机构列表(树形)", tags = {"v1.0.0"}, description = "获取用户机构列表(树形)")
    @GetMapping(value = "/get/user-org-info")
    @AutoLog(note = "获取用户机构列表(树形)", type = AutoLog.QUERY)
    public Result<List<UserOrgTreeInfo>> getUserOrgInfo() {
        return ok(service.getUserOrgInfo());
    }


    @Operation(summary = "修改用户资料", tags = {"v1.0.0"}, description = "修改用户资料")
    @GetMapping(value = "/update/info")
    @AutoLog(note = "获取用户信息", type = AutoLog.QUERY)
    public Result<String> updateUserInfo(@RequestBody @Valid UpdateInfoVo vo) {
        service.updateUserInfo(vo);
        return ok("修改信息用户成功");
    }


    @Operation(summary = "修改用户头像", tags = {"v1.0.0"}, description = "修改用户头像")
    @GetMapping(value = "/update/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        service.updateUserAvatar(file);
        return ok("修改头像成功");
    }


    @Operation(summary = "修改用户手机号", tags = {"v1.0.0"}, description = "修改用户手机号")
    @GetMapping(value = "/update/phone")
    @AutoLog(note = "修改用户手机号", type = AutoLog.QUERY)
    public Result<String> updateUserPhone(@RequestBody @Valid UpdatePhoneVo vo) {
        service.updateUserPhone(vo);
        return ok("修改手机号成功");
    }


    @Operation(summary = "修改密码", tags = {"v1.0.0"}, description = "修改密码")
    @GetMapping(value = "/update/password")
    @AutoLog(note = "修改密码", type = AutoLog.QUERY)
    public Result<String> updatePassword(@RequestBody @Valid UpdatePasswordVo vo) {
        service.updatePassword(vo);
        return ok("修改密码成功");
    }


    @Operation(summary = "找回密码", tags = {"v1.0.0"}, description = "找回密码")
    @GetMapping(value = "/update/find-password")
    @AutoLog(note = "找回密码", type = AutoLog.QUERY)
    @Anonymous
    public Result<String> findPassword(@RequestBody @Valid FindPasswordVo vo) {
        service.findPassword(vo);
        return ok("找回密码成功");
    }


    @Operation(summary = "修改手机号或者找回密码发送短信验证码", tags = {"v1.0.0"}, description = "修改手机号或者找回密码发送短信验证码")
    @Parameter(in = ParameterIn.PATH, description = "手机号", name = "phone", required = true)
    @GetMapping(value = "/send-sms-code/{phone}")
    @Anonymous
    @AutoLog(note = "修改手机号或者找回密码发送短信验证码", type = AutoLog.QUERY)
    public Result<String> sendSmsCode(@PathVariable(required = false) @PathNotBlankOrNull(message = "电话号码不能为空不能为空") String phone) {
        service.sendSmsCode(phone);
        return ok("发送短信验证码成功");
    }
}
