

package com.anyilanxin.anyicloud.corecommon.base;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.constant.impl.BusinessType;
import com.anyilanxin.anyicloud.corecommon.constant.impl.SocketMsgType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;

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
public class SocketResult implements Serializable {
    private static final long serialVersionUID = -7991672291592110700L;

    @Schema(name = "msgType", title = "消息类型:0-指定接收人,1-指定接收系统,2-广播,3-请求业务数据,默认4(与SocketMsgType一致)", required = true)
    private Integer msgType;

    @Schema(name = "data", title = "业务数据")
    private Object data;

    @Schema(name = "message", title = "提示信息")
    private String message;

    @Schema(name = "requestMarker", title = "请求标记")
    private String requestMarker;

    @Schema(title = "响应时间")
    private final long timestamp;

    @Schema(title = "消息业务类型(与BusinessType对应)", required = true)
    @NotNull(message = "消息业务类型不能为空")
    private final String businessType;

    @Schema(name = "userIds", title = "接收用户ids")
    private List<String> userIds;

    @Schema(name = "loginCodes", title = "接收登录编码")
    private List<String> loginCodes;

    public SocketResult(BusinessType businessType) {
        this.businessType = businessType.getType();
        this.msgType = 3;
        this.timestamp = System.currentTimeMillis();
    }


    public SocketResult(BusinessType businessType, Object data) {
        this(businessType);
        this.data = data;
    }


    public SocketResult setMsgType(SocketMsgType msgType) {
        this.msgType = msgType.getType();
        return this;
    }


    public SocketResult setData(Object data) {
        this.data = data;
        return this;
    }


    public SocketResult setMessage(String message) {
        this.message = message;
        return this;
    }


    public SocketResult setRequestMarker(String requestMarker) {
        this.requestMarker = requestMarker;
        return this;
    }


    public SocketResult setUserId(String userId) {
        if (CollectionUtil.isEmpty(this.userIds)) {
            this.userIds = new ArrayList<>();
        }
        this.userIds.add(userId);
        return this;
    }


    public SocketResult setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }


    public SocketResult setSystemId(String loginCode) {
        if (CollectionUtil.isEmpty(this.loginCodes)) {
            this.loginCodes = new ArrayList<>();
        }
        this.loginCodes.add(loginCode);
        return this;
    }


    public SocketResult setSystemIds(List<String> loginCodes) {
        this.loginCodes = loginCodes;
        return this;
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
