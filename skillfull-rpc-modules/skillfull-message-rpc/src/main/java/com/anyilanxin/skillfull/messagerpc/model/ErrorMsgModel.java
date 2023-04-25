package com.anyilanxin.skillfull.messagerpc.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 发送消息
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-07 15:19
 * @since JDK1.8
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
