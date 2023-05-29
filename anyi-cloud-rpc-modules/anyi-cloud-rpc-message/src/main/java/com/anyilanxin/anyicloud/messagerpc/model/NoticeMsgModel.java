

package com.anyilanxin.anyicloud.messagerpc.model;

import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMsgType;

import java.io.Serializable;
import java.util.List;

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
public class NoticeMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;

    /**
     * 数据类型：0-字符串，1-html
     */
    private int dataType;

    /**
     * 类型：1-通知，2-消息，3-代办
     */
    private int type;

    /**
     * showType类型：0-不显示通知(默认)，1-banner通知，2-通知提醒
     */
    private int showType;

    /**
     * 通知提醒类型：0-成功(默认)，1-信息，2-警告，3-错误
     */
    private int noticeShowType;

    /**
     * 消息数据
     */
    private String data;

    /**
     * 接收类型：0-指定人，1-指定机构，2-广播，3-应答数据
     */
    private SocketMsgType receiveType;

    /**
     * 人员列表(通知事件才存在)
     */
    private List<String> userIds;

    /**
     * 机构列表(通知事件才存在)
     */
    private List<String> orgIds;
}
