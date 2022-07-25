package com.anyilanxin.skillfull.system.core.constant.impl;


import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 特殊url类型
 *
 * @author zxiaozhou
 * @date 2020-09-11 12:12
 * @since JDK1.8
 */
@Getter
@ConstantType
public enum SpecialUrlType implements ISuperType {

    /**
     * 白名单
     */
    WHITE_LIST(1, "白名单"),


    /**
     * 黑名单
     */
    BLACK_LIST(2, "黑名单");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    SpecialUrlType(int type, String typeDescribe) {
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
    public static boolean isHaveByType(Integer type) {
        if (Objects.isNull(type)) {
            return false;
        }
        SpecialUrlType[] values = SpecialUrlType.values();
        for (SpecialUrlType value : values) {
            if (type.equals(value.type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link Integer} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static SpecialUrlType getComplainByType(int type) {
        SpecialUrlType[] values = SpecialUrlType.values();
        for (SpecialUrlType value : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        SpecialUrlType[] values = SpecialUrlType.values();
        StringBuilder sb = new StringBuilder();
        for (SpecialUrlType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        SpecialUrlType[] values = SpecialUrlType.values();
        for (SpecialUrlType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType() + "");
            dictDto.setTypeName(value.getTypeDescribe());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
