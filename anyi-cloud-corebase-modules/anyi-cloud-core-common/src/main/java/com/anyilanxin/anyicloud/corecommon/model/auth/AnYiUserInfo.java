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

package com.anyilanxin.anyicloud.corecommon.model.auth;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * 自定义用户信息
 *
 * @author zxh
 * @date 2022-02-14 17:16
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
public class AnYiUserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1654336717080L;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "superAdmin", title = "是否为超级管理员")
    private boolean superAdmin;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "identityStatus", title = "实名状态:0-待提交,1-审核中,2-未通过(审核失败),3-通过(审核成功),默认0")
    private int identityStatus;

    @Schema(name = "identity", title = "实名认证信息")
    private UserIdentity identity;

    @Schema(name = "loginFailErrorNum", title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日", type = "string", example = "2020-12-21")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "phone", title = "电话号码")
    private String phone;

    @Schema(name = "currentOrgId", title = "当前机构id")
    private String currentOrgId;

    @Schema(name = "currentOrgName", title = "当前机构名称")
    private String currentOrgName;

    @Schema(name = "currentOrgCode", title = "当前机构code")
    private String currentOrgCode;

    @Schema(name = "currentAreaCode", title = "当前区域编码")
    private String currentAreaCode;

    @Schema(name = "currentAreaName", title = "当前区域名称")
    private String currentAreaName;

    @Schema(name = "orgInfo", title = "机构基本信息")
    private OrgSimpleInfo orgInfo;

    @Schema(name = "currentTenantId", title = "当前租户id")
    private String currentTenantId;

    @Schema(name = "roleInfos", title = "当前角色信息")
    private Set<RoleInfo> roleInfos;

    @Schema(name = "roleCodes", title = "当前角色编码信息")
    private Set<String> roleCodes;

    @Schema(name = "roleIds", title = "当前角色id信息")
    private Set<String> roleIds;

    @Schema(name = "agentInfos", title = "代理人信息")
    private Set<UserAgent> agentInfos;

    @Schema(name = "extendInfo", title = "扩展信息")
    private Map<String, Object> extendInfo;
}
