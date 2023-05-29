

package com.anyilanxin.anyicloud.corecommon.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * 鉴权类型
 *
 * @author zxh
 * @date 2020-09-11 12:12
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum AuthType implements ISuperType {

    /**
     * 全局
     */
    ALL(1, "全局"),

    /**
     * 网关
     */
    GATEWAY(2, "网关"),

    /**
     * 服务
     */
    SERVICE(3, "服务");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String typeDescribe;

    AuthType(int type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(Integer type) {
        if (Objects.isNull(type)) {
            return false;
        }
        AuthType[] values = AuthType.values();
        for (AuthType value : values) {
            if (value.type == type) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static AuthType getComplainByType(Integer type) {
        if (Objects.isNull(type)) {
            return null;
        }
        AuthType[] values = AuthType.values();
        for (AuthType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String}
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        AuthType[] values = AuthType.values();
        StringBuilder sb = new StringBuilder();
        for (AuthType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        AuthType[] values = AuthType.values();
        for (AuthType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType() + "");
            dictDto.setTypeName(value.getTypeDescribe());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
