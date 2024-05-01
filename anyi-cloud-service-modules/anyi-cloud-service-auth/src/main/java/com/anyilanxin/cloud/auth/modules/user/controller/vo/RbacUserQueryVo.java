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
package com.anyilanxin.cloud.auth.modules.user.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户表条件查询Request
 *
 * @author zhouxuanhong
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2024-01-23 10:06:20
 * @since JDK11
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacUserQueryVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 310983023745470788L;

    @Schema(title = "用户id")
    private String userId;

    @Schema(title = "用户名")
    private String userName;

    @Schema(title = "开放id")
    private String openId;

    @Schema(title = "用户昵称")
    private String nickName;

    @Schema(title = "真实姓名")
    private String realName;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "密码盐")
    private String salt;

    @Schema(title = "个人简介")
    private String shortProfile;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "生日", type = "string", example = "2020-11-12")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(title = "邮件")
    private String email;

    @Schema(title = "是否初始密码:0-不是,1-是,默认1")
    private Integer isInitialPassword;

    @Schema(title = "电话号码")
    private String phone;

    @Schema(title = "来源")
    private String registerSource;

    @Schema(title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(title = "工号，唯一键")
    private String workNo;

    @Schema(title = "座机号")
    private String telephone;

    @Schema(title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(title = "职位,org_id array")
    private Set<String> positionIds;

    @Schema(title = "负责机构,org_id array")
    private Set<String> managerOrgIds;

    @Schema(title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(title = "当前登录部门id")
    private String currentOrgId;

    @Schema(title = "最后登录IP")
    private String currentLoginIp;

    @Schema(title = "最后登录时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentLoginDate;

    @Schema(title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "创建区域编码")
    private String createAreaCode;

    @Schema(title = "创建职位编码")
    private String createPositionCode;

    @Schema(title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(title = "创建系统编码")
    private String createSystemCode;

    @Schema(title = "创建租户id")
    private String createTenantId;

    @Schema(title = "创建用户id")
    private String createUserId;

    @Schema(title = "创建用户姓名")
    private String createUserName;

    @Schema(title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(title = "更新用户id")
    private String updateUserId;

    @Schema(title = "更新用户姓名")
    private String updateUserName;

    @Schema(title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
