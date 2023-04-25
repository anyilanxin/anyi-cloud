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
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息模板(ManageTemplate)Entity
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
@TableName("msg_manage_template")
public class ManageTemplateEntity extends BaseEntity {
    private static final long serialVersionUID = 125199450464820958L;

    @TableId
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板状态:0-禁用,1-启用
     */
    private Integer templateStatus;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 三方系统模板编码
     */
    private String templateThirdCode;

    /**
     * 最大重试次数,默认0-不重试
     */
    private Integer sendMaxNum;

    /**
     * 模板类型:1-微信模板,2-短信,3-邮件,来源于常量字典：MsgTemplateType
     */
    private Integer templateType;

    /**
     * 是否限制发送次数：0-不限制,1-限制。默认0
     */
    private Integer limitSend;

    /**
     * 每天允许最大发送次数,当启用限制次数时有效，默认10
     */
    private Integer maxSendNum;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板字段说明信息
     */
    private String templateContentDescribe;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
