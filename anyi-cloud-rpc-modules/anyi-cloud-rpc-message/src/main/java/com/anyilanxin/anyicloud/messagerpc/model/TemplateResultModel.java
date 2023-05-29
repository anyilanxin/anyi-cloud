

package com.anyilanxin.anyicloud.messagerpc.model;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 公共模板消息(没有子类)
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
public class TemplateResultModel implements Serializable {
    private static final long serialVersionUID = 5735357577900147933L;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 发送成功信息
     */
    private List<String> successInfo;

    /**
     * 发送失败信息
     */
    private List<Fail> failInfo;

    /**
     * 发送状态:0-全部失败，1-部分失败，2-全部成功
     */
    private int status;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class Fail implements Serializable {
        private static final long serialVersionUID = 1661827675724L;

        /**
         * 接收人
         */
        private String receiveInfo;

        /**
         * 失败原因
         */
        private String errorMsg;
    }
}
