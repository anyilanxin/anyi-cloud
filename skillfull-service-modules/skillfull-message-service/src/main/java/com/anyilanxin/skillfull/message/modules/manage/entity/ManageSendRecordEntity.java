// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.entity;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 消息发送记录表(ManageSendRecord)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:49:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "msg_manage_send_record", autoResultMap = true)
public class ManageSendRecordEntity extends BaseEntity {
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
    @TableField(typeHandler = FastjsonTypeHandler.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
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
