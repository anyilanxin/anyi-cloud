// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 消息群(ChatGroup)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:49:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName("msg_chat_group")
public class ChatGroupEntity extends BaseEntity {
    private static final long serialVersionUID = 249358828938985345L;

    @TableId
    private String chatGroupId;

    /**
     * 新成员查看历史消息：0-不能,1-能。默认0
     */
    private Integer enableHistoryMsg;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群号
     */
    private String groupNo;

    /**
     * 群描述
     */
    private String describe;

    /**
     * 群图标
     */
    private String groupImg;

    /**
     * 群成员数量
     */
    private Integer groupUserNum;

    /**
     * 群主id
     */
    private String managerUserId;

    /**
     * 备注
     */
    private String remark;
}
