

package com.anyilanxin.anyicloud.messagerpc.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.messagerpc.constant.SocketMessageEventContent;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * socket消息事件
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum SocketMessageEventType implements ISuperType {

    /**
     * 授权事件
     */
    AUTH_EVENT(SocketMessageEventContent.AUTH_EVENT, "授权事件"),

    /**
     * 异常事件
     */
    ERROR_EVENT(SocketMessageEventContent.ERROR_EVENT, "异常事件"),

    /**
     * 通知事件
     */
    NOTICE_EVENT(SocketMessageEventContent.NOTICE_EVENT, "通知事件"),

    /**
     * 业务事件
     */
    BUSINESS_EVENT(SocketMessageEventContent.BUSINESS_EVENT, "业务事件"),

    /**
     * 聊天事件
     */
    CHAT_EVENT(SocketMessageEventContent.CHAT_EVENT, "聊天事件"),
    /**
     * 上下线通知
     */
    UP_DOWN(SocketMessageEventContent.UP_DOWN, "上下线通知");

    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;

    SocketMessageEventType(String type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        SocketMessageEventType[] values = SocketMessageEventType.values();
        for (SocketMessageEventType value : values) {
            if (value.type.equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断某个类型
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static SocketMessageEventType getByType(String type) {
        SocketMessageEventType[] values = SocketMessageEventType.values();
        for (SocketMessageEventType value : values) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        SocketMessageEventType[] values = SocketMessageEventType.values();
        StringBuilder sb = new StringBuilder();
        for (SocketMessageEventType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        SocketMessageEventType[] values = SocketMessageEventType.values();
        for (SocketMessageEventType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
