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

package com.anyilanxin.anyicloud.message.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统通告公告管理分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 08:34:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageAnnouncementPageQuery extends AnYiPageQuery {
    @Serial
    private static final long serialVersionUID = 840522470689315064L;

    @Schema(name = "anntId", title = "通知公告id")
    private String anntId;

    @Schema(name = "title", title = "标题")
    private String title;

    @Schema(name = "msgAbstract", title = "摘要")
    private String msgAbstract;

    @Schema(name = "msgContent", title = "内容")
    private String msgContent;

    @Schema(name = "senderUserName", title = "发布人姓名")
    private String senderUserName;

    @Schema(name = "senderUserId", title = "发布人")
    private String senderUserId;

    @Schema(name = "announcementType", title = "通知公告类型：1-系统公告，2-待办事项通知")
    private Integer announcementType;

    @Schema(name = "receiveUserId", title = "接收用户id")
    private String receiveUserId;

    @Schema(name = "receiveAreaCode", title = "接收区域编码")
    private String receiveAreaCode;

    @Schema(name = "receiveOrgId", title = "接收组织机构id")
    private String receiveOrgId;

    @Schema(name = "receiveOrgCode", title = "接收组织机构编码")
    private String receiveOrgCode;

    @Schema(name = "sendType", title = "发布方式：0-手动,1-自动")
    private Integer sendType;

    @Schema(name = "autoSendTime", title = "自动发布时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime autoSendTime;

    @Schema(name = "sendTime", title = "发布时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    @Schema(name = "cancelTime", title = "撤销时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime cancelTime;

    @Schema(name = "sendStatus", title = "发布状态：0未发布，1已发布，2已撤销，默认0")
    private Integer sendStatus;

    @Schema(name = "pageUrl", title = "页面url")
    private String pageUrl;

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

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
