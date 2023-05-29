

package com.anyilanxin.anyicloud.message.core.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 消息模板类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum WebSocketSessionType implements ISuperType {
    /**
     * 用户id key
     */
    USER_ID("USER_ID", "用户id key"),

    /**
     * 用户token key
     */
    TOKEN("TOKEN", "token key"),

    /**
     * 自定义session id key
     */
    CUSTOM_SESSION_ID("CUSTOM_SESSION_ID", "自定义session id key");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;

    WebSocketSessionType(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link Integer} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        WebSocketSessionType[] values = WebSocketSessionType.values();
        for (WebSocketSessionType value : values) {
            if (value.type.equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        WebSocketSessionType[] values = WebSocketSessionType.values();
        StringBuilder sb = new StringBuilder();
        for (WebSocketSessionType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        WebSocketSessionType[] values = WebSocketSessionType.values();
        for (WebSocketSessionType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.describe);
            dictDto.setTypeName(value.describe);
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
