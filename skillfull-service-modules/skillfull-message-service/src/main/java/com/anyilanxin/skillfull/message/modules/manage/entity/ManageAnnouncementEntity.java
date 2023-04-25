/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.message.modules.manage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 系统通告公告管理(ManageAnnouncement)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 11:49:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("msg_manage_announcement")
public class ManageAnnouncementEntity extends BaseEntity {
    private static final long serialVersionUID = 989377393842305039L;

    @TableId
    private String anntId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String msgAbstract;

    /**
     * 内容
     */
    private String msgContent;

    /**
     * 发布人姓名
     */
    private String senderUserName;

    /**
     * 发布人
     */
    private String senderUserId;

    /**
     * 通知公告类型：1-系统公告，2-待办事项通知
     */
    private Integer announcementType;

    /**
     * 接收用户id
     */
    private String receiveUserId;

    /**
     * 接收区域编码
     */
    private String receiveAreaCode;

    /**
     * 接收组织机构id
     */
    private String receiveOrgId;

    /**
     * 接收组织机构编码
     */
    private String receiveOrgCode;

    /**
     * 发布方式：0-手动,1-自动
     */
    private Integer sendType;

    /**
     * 自动发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime autoSendTime;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    /**
     * 撤销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime cancelTime;

    /**
     * 发布状态：0未发布，1已发布，2已撤销，默认0
     */
    private Integer sendStatus;

    /**
     * 页面url
     */
    private String pageUrl;

    /**
     * 备注
     */
    private String remark;
}
