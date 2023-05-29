

package com.anyilanxin.anyicloud.messagerpc.model;

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
public class ErrorMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;
    /**
     * 异常消息
     */
    private String data;
}
