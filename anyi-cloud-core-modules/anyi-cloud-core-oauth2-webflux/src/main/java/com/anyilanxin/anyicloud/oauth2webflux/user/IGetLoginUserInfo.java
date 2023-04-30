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

package com.anyilanxin.anyicloud.oauth2webflux.user;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserIdentity;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import java.util.Set;
import reactor.core.publisher.Mono;

/**
 * 获取登录用户信息接口
 *
 * @author 安一老厨
 * @date 2022-04-09 09:33
 * @since 1.0.0
 */
public interface IGetLoginUserInfo {
    /**
     * 根据token获取登录用户信息
     */
    Mono<UserInfo> getUserInfo(String token);


    /**
     * 获取登录用户信息
     */
    Mono<UserInfo> getUserInfo();


    /**
     * 实名状态:0-待提交,1-审核中,2-未通过(审核失败),3-通过(审核成功),默认0
     */
    Mono<Integer> getIdentityStatus();


    /**
     * 实名认证信息
     */
    Mono<UserIdentity> getIdentity();


    /**
     * 判断是否为超级管理员
     */
    Mono<Boolean> superRole();


    /**
     * 获取角色信息
     */
    Mono<Set<RoleInfo>> getRoleInfos();


    /**
     * 获取角色编码
     */
    Mono<Set<String>> getRoleCodes();


    /**
     * 获取角色Id
     */
    Mono<Set<String>> getRoleIds();


    /**
     * 获取用户id
     */
    Mono<String> getUserId();


    /**
     * 获取用户名
     */
    Mono<String> getUserName();


    /**
     * 获取昵称
     */
    Mono<String> getNickName();


    /**
     * 获取用户真实姓名
     */
    Mono<String> getRealName();


    /**
     * 获取当前机构id
     */
    Mono<String> getCurrentOrgId();


    /**
     * 获取当前机构编码
     */
    Mono<String> getCurrentOrgCode();


    /**
     * 获取当前区域编码
     */
    Mono<String> getCurrentAreaCode();


    /**
     * 获取当前租户id
     */
    Mono<String> getCurrentTenantId();


    /**
     * 获取当前用户手机号
     */
    Mono<String> getPhone();
}
