

package com.anyilanxin.anyicloud.messagerpc.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

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
public enum SocketMsgType implements ISuperType {
    /**
     * 授权事件
     */
    DESIGNATED_PERSONNEL(0, "指定人"),

    /**
     * 异常事件
     */
    DESIGNATED_ORG(1, "指定机构"),

    /**
     * 通知事件
     */
    BROADCAST(2, "广播"),

    /**
     * 应答数据
     */
    CALLBACK(3, "应答数据");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String typeDescribe;

    SocketMsgType(int type, String typeDescribe) {
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
    public static boolean isHaveByType(int type) {
        SocketMsgType[] values = SocketMsgType.values();
        for (SocketMsgType value : values) {
            if (value.type == type) {
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
    public static SocketMsgType getByType(int type) {
        SocketMsgType[] values = SocketMsgType.values();
        for (SocketMsgType value : values) {
            if (value.type == type) {
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
        SocketMsgType[] values = SocketMsgType.values();
        StringBuilder sb = new StringBuilder();
        for (SocketMsgType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        SocketMsgType[] values = SocketMsgType.values();
        for (SocketMsgType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(String.valueOf(value.getType()));
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
