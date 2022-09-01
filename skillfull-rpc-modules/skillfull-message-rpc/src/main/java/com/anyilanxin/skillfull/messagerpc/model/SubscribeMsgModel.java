// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.messagerpc.model;

import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 消息实体
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-07 15:19
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubscribeMsgModel extends SocketMsgModel {
    private static final long serialVersionUID = -3931714024892969626L;

    /**
     * 发送人自定义session id
     */
    private String sendCustomSessionId;

    /**
     * 发送人session id
     */
    private String sendSessionId;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送用户信息
     */
    private UserInfo sendUserInfo;

    /**
     * 发送用户信息id
     */
    private String userId;

    /**
     * 发送人token
     */
    private String sendToken;


    public SubscribeMsgModel(SocketMessageEventType eventType) {
        super(eventType);
    }
}
