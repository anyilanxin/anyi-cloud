

package com.anyilanxin.anyicloud.message.modules.websocket.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 发送socket消息
 *
 * @author zxh
 * @date 2021-01-08 15:57
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SendSocket implements Serializable {
    private static final long serialVersionUID = -7991672291592110700L;
}
