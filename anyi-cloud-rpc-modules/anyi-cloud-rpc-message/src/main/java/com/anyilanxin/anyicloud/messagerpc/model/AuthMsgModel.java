

package com.anyilanxin.anyicloud.messagerpc.model;

import com.anyilanxin.anyicloud.corecommon.constant.Status;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 发送消息
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-07 15:19
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AuthMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;

    /**
     * 类型：1-登录过期，2-被强制下线，3-已在其他设备登录下线(开启单设备登录时有效)
     */
    private Status type;

    /**
     * 消息
     */
    private String message;

    /**
     * 鉴权信息针对token
     */
    private String token;
}
