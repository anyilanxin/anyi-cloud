package com.anyilanxin.skillfull.gateway.core.constant.typeimpl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 协议类型前缀
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum ProtocolType implements ISuperType {
    /**
     * http协议前缀
     */
    HTTP("HTTP://", "http协议前缀"),

    /**
     * https协议前缀
     */
    HTTPS("HTTPS://", "https协议前缀"),

    /**
     * ws协议前缀
     */
    WS("WS://", "ws协议前缀"),

    /**
     * wss协议前缀
     */
    WSS("WSS://", "wss协议前缀");


    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;


    ProtocolType(String type, String describe) {
        this.type = type;
        this.describe = describe;
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
        ProtocolType[] values = ProtocolType.values();
        for (ProtocolType value : values) {
            if (value.type.startsWith(type.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        ProtocolType[] values = ProtocolType.values();
        StringBuilder sb = new StringBuilder();
        for (ProtocolType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ProtocolType[] values = ProtocolType.values();
        for (ProtocolType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
