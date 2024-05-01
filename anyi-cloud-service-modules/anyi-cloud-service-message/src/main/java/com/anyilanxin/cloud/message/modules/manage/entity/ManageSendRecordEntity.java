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

package com.anyilanxin.cloud.message.modules.manage.entity;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.cloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 消息发送记录表(ManageSendRecord)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 11:49:57
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "msg_manage_send_record", autoResultMap = true)
public class ManageSendRecordEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 921903043857933745L;

    @TableId
    private String sendRecordId;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 三方系统模板编码
     */
    private String templateThirdCode;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 发送批次号
     */
    private String sendBatchNo;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 模板原始数据,json
     */
    @TableField(typeHandler = Fastjson2TypeHandler.class)
    private JSONObject templateOriginalData;

    /**
     * 发送方式:1-微信模板,2-短信,3-邮件
     */
    private Integer sendType;

    /**
     * 接收人
     */
    private String sendReceiver;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime sendTime;

    /**
     * 发送状态:0-失败，1-成功，2-多次发送后失败。默认0
     */
    private String sendStatus;

    /**
     * 发送失败原因，json数组
     */
    private String sendResults;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已经发送次数，默认1
     */
    private Integer sendNum;

    /**
     * 最大发送次数，默认1
     */
    private Integer sendMaxNum;
}
