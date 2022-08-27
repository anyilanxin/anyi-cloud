package com.anyilanxin.skillfull.messagerpc.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.messagerpc.constant.StreamSocketBusinessConstant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * stream与socket消息类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum StreamSocketBusinessType implements ISuperType {

    /**
     * 流程消息
     */
    PROCESS_MSG(StreamSocketBusinessConstant.PROCESS_MSG, "流程消息"),

    /**
     * 授权消息
     */
    AUTH_MSG(StreamSocketBusinessConstant.AUTH_MSG, "授权消息"),

    /**
     * 异常消息
     */
    ERROR_MSG(StreamSocketBusinessConstant.ERROR_MSG, "异常消息"),

    /**
     * 系统消息
     */
    SYSTEM_NOTICE(StreamSocketBusinessConstant.SYSTEM_NOTICE, "系统消息"),

    /**
     * 业务消息
     */
    BUSINESS_MSG(StreamSocketBusinessConstant.BUSINESS_MSG, "业务消息"),

    /**
     * 聊天消息
     */
    CHART_MSG(StreamSocketBusinessConstant.CHART_MSG, "聊天消息");

    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    StreamSocketBusinessType(String type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        StreamSocketBusinessType[] values = StreamSocketBusinessType.values();
        for (StreamSocketBusinessType value : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static StreamSocketBusinessType getByType(String type) {
        StreamSocketBusinessType[] values = StreamSocketBusinessType.values();
        for (StreamSocketBusinessType value : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        StreamSocketBusinessType[] values = StreamSocketBusinessType.values();
        StringBuilder sb = new StringBuilder();
        for (StreamSocketBusinessType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        StreamSocketBusinessType[] values = StreamSocketBusinessType.values();
        for (StreamSocketBusinessType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
