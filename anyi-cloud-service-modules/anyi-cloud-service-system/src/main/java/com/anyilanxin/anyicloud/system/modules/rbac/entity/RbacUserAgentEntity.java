

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户-代理人表(RbacUserAgent)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_user_agent")
public class RbacUserAgentEntity extends BaseEntity {
    private static final long serialVersionUID = 478044700733569420L;

    @TableId
    private String agentId;

    /**
     * 用户名id
     */
    private String userId;

    /**
     * 代理人用户id
     */
    private String agentUserId;

    /**
     * 是否限制时间:0-不限制,1-限制，默认0
     */
    private Integer isLimit;

    /**
     * 代理开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime agentStartTime;

    /**
     * 代理结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime agentEndTime;

    /**
     * 状态：0-无效，1-有效,默认0
     */
    private Integer agentStatus;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
