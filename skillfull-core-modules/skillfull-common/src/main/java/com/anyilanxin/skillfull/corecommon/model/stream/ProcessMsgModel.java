// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.stream;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.constant.impl.BusinessType;
import com.anyilanxin.skillfull.corecommon.constant.impl.SocketMsgType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * web socket消息model
 *
 * @author zxiaozhou
 * @date 2021-01-08 15:57
 * @since JDK11
 */
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessMsgModel implements Serializable {
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
        if (CollectionUtil.isEmpty(this.userIds)) {
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
        if (CollectionUtil.isEmpty(this.loginCodes)) {
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
