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

import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

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
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SocketMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;
    /**
     * 消息事件,具体与SocketMessageEventType常量一致
     */
    private String messageEvent;

    /**
     * 消息数据
     */
    private Object data;

    /**
     * 序列id
     */
    private String serialId;


    public SocketMsgModel(SocketMessageEventType eventType) {
        this.messageEvent = eventType.getType();
    }
}
