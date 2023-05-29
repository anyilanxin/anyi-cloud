

package com.anyilanxin.anyicloud.messagerpc.model;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.MsgTemplateEmailChannelType;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 邮箱模板消息
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 14:07
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TemplateEmailMsgModel implements Serializable {
    private static final long serialVersionUID = 5735357577900147933L;

    /**
     * 模板code required=true
     */
    private String templateCode;

    /**
     * 接收人信息，电话号码或其他平台需要的信息 required=true
     */
    private List<String> receiveInfo;

    /**
     * 发送数据
     */
    private JSONObject jsonObject;

    /**
     * 邮键渠道
     */
    private MsgTemplateEmailChannelType channel;

    /**
     * 所属业务id
     */
    private String businessId;
}
