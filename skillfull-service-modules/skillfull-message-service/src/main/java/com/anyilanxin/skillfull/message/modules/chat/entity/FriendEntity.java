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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 好友列表(Friend)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:24
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName("msg_friend")
public class FriendEntity extends BaseEntity {
    private static final long serialVersionUID = -90880464018351841L;

    @TableId
    private String friendId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 好友用户id
     */
    private String friendUserId;

    /**
     * 创建区域编码
     */
    private String createAreaCode;

    /**
     * 创建职位编码
     */
    private String createPositionCode;

    /**
     * 创建机构系统编码
     */
    private String createOrgSysCode;

    /**
     * 创建系统编码
     */
    private String createSystemCode;

    /**
     * 创建租户id
     */
    private String createTenantId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 聊天会话id,好友列表公用会话id
     */
    private String chatSessionId;

    /**
     * 申请状态：0-申请待通过，1-通过
     */
    private Integer friendStatus;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime applyTime;

    /**
     * 申请通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime successTime;
}
