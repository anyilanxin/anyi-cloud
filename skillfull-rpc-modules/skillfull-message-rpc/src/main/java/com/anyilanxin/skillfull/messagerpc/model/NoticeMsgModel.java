/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.messagerpc.model;

import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMsgType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

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
