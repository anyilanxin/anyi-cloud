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

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息模板(ManageTemplate)Entity
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
