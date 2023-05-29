/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.oauth2webflux.utils;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.oauth2webflux.user.IGetLoginUserInfo;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 上下文持有者
 *
 * @author zxh
 * @date 2020-10-07 09:07
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class UserContextUtils {
    private static UserContextUtils utils;
    private final IGetLoginUserInfo loginUserInfo;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 根据token获取登录用户信息
     */
    public static Mono<UserInfo> getUserInfo(String token) {
        return utils.loginUserInfo.getUserInfo(token);
    }


    /**
     * 获取登录用户信息
     */
    public static Mono<UserInfo> getUserInfo() {
        return utils.loginUserInfo.getUserInfo();
    }


    /**
     * 判断当前登录人是否为超管
     */
    public static Mono<Boolean> superRole() {
        return utils.loginUserInfo.superRole();
    }


    /**
     * 获取当前登录人角色信息
     */
    public static Mono<Set<RoleInfo>> getRoleInfos() {
        return utils.loginUserInfo.getRoleInfos();
    }


    /**
     * 获取当前登录人角色编码
     */
    public static Mono<Set<String>> getRoleCodes() {
        return utils.loginUserInfo.getRoleCodes();
    }


    /**
     * 获取当前登录人角色id
     */
    public static Mono<Set<String>> getRoleIds() {
        return utils.loginUserInfo.getRoleIds();
    }


    /**
     * 获取当前登录人用户id
     */
    public static Mono<String> getUserId() {
        return utils.loginUserInfo.getUserId();
    }


    /**
     * 获取当前登录人用户名
     */
    public static Mono<String> getUserName() {
        Mono<String> userName = utils.loginUserInfo.getUserName();
        return userName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人昵称
     */
    public static Mono<String> getNickName() {
        Mono<String> nickName = utils.loginUserInfo.getNickName();
        return nickName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人真实姓名
     */
    public static Mono<String> getRealName() {
        Mono<String> realName = utils.loginUserInfo.getRealName();
        return realName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录机构
     */
    public static Mono<String> getCurrentOrgId() {
        Mono<String> currentOrgId = utils.loginUserInfo.getCurrentOrgId();
        return currentOrgId.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录机构编码
     */
    public static Mono<String> getCurrentOrgCode() {
        Mono<String> currentOrgCode = utils.loginUserInfo.getCurrentOrgCode();
        return currentOrgCode.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录区域编码
     */
    public static Mono<String> getCurrentAreaCode() {
        Mono<String> currentAreaCode = utils.loginUserInfo.getCurrentAreaCode();
        return currentAreaCode.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录租户id
     */
    public static Mono<String> getCurrentTenantId() {
        Mono<String> currentTenantId = utils.loginUserInfo.getCurrentTenantId();
        return currentTenantId.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人电话号码
     */
    public static Mono<String> getPhone() {
        Mono<String> phone = utils.loginUserInfo.getPhone();
        return phone.switchIfEmpty(Mono.just(""));
    }
}
