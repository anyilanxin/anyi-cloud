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

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户-代理人表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserAgentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -35405650609720859L;

    @Schema(name = "userId", title = "用户名id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "用户名id不能为空")
    private String userId;

    @Schema(name = "agentUserId", title = "代理人用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "代理人用户id不能为空")
    private String agentUserId;

    @Schema(name = "isLimit", title = "是否限制时间:0-不限制,1-限制，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "是否限制时间:0-不限制,1-限制，默认0不能为空")
    private Integer isLimit;

    @Schema(name = "agentStartTime", title = "代理开始时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime agentStartTime;

    @Schema(name = "agentEndTime", title = "代理结束时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime agentEndTime;

    @Schema(name = "agentStatus", title = "状态：0-无效，1-有效,默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "状态：0-无效，1-有效,默认0不能为空")
    private Integer agentStatus;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "唯一索引帮助字段,默认1，如果删除该值为主键不能为空")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "创建时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "删除状态:0-正常,1-已删除,默认0不能为空")
    private Integer delFlag;

}
