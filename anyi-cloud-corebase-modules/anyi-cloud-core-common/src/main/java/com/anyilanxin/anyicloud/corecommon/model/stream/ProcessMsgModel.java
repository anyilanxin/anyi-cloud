/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.model.stream;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.corecommon.constant.impl.BusinessType;
import com.anyilanxin.anyicloud.corecommon.constant.impl.SocketMsgType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.dromara.hutool.core.collection.CollUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * web socket消息model
 *
 * @author zxh
 * @date 2021-01-08 15:57
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessMsgModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -7991672291592110700L;

    @Schema(name = "msgType", title = "消息类型:0-指定接收人,1-指定接收系统,2-广播,3-请求业务数据,默认4(与SocketMsgType一致)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer msgType;

    @Schema(name = "data", title = "业务数据")
    private Object data;

    @Schema(name = "message", title = "提示信息")
    private String message;

    @Schema(name = "requestMarker", title = "请求标记")
    private String requestMarker;

    @Schema(title = "响应时间")
    private final long timestamp;

    @Schema(title = "消息业务类型(与BusinessType对应)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "消息业务类型不能为空")
    private final String businessType;

    @Schema(name = "userIds", title = "接收用户ids")
    private List<String> userIds;

    @Schema(name = "loginCodes", title = "接收登录编码")
    private List<String> loginCodes;

    public ProcessMsgModel(BusinessType businessType) {
        this.businessType = businessType.getType();
        this.msgType = 3;
        this.timestamp = System.currentTimeMillis();
    }


    public ProcessMsgModel(BusinessType businessType, Object data) {
        this(businessType);
        this.data = data;
    }


    public ProcessMsgModel setMsgType(SocketMsgType msgType) {
        this.msgType = msgType.getType();
        return this;
    }


    public ProcessMsgModel setData(Object data) {
        this.data = data;
        return this;
    }


    public ProcessMsgModel setMessage(String message) {
        this.message = message;
        return this;
    }


    public ProcessMsgModel setRequestMarker(String requestMarker) {
        this.requestMarker = requestMarker;
        return this;
    }


    public ProcessMsgModel setUserId(String userId) {
        if (CollUtil.isEmpty(this.userIds)) {
            this.userIds = new ArrayList<>();
        }
        this.userIds.add(userId);
        return this;
    }


    public ProcessMsgModel setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }


    public ProcessMsgModel setSystemId(String loginCode) {
        if (CollUtil.isEmpty(this.loginCodes)) {
            this.loginCodes = new ArrayList<>();
        }
        this.loginCodes.add(loginCode);
        return this;
    }


    public ProcessMsgModel setSystemIds(List<String> loginCodes) {
        this.loginCodes = loginCodes;
        return this;
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
