package com.anyilanxin.skillfull.corecommon.constant.impl;


import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ActionConstant;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮权限检测类型
 *
 * @author zxiaozhou
 * @date 2020-09-11 12:12
 * @since JDK1.8
 */
@Getter
@ConstantType
public enum ActionType implements ISuperType {


    /**
     * 拥有任意一个指定角色
     */
    hasAnyRole(ActionConstant.HAS_ANY_ROLE, "拥有任意一个指定角色"),

    /**
     * 拥有任意一个指定的权限
     */
    hasAnyAuthority(ActionConstant.HAS_ANY_AUTHORITY, "拥有任意一个指定的权限"),

    /**
     * 拥有任意一个指定Ip
     */
    hasAnyIpAddress(ActionConstant.HAS_ANY_IP_ADDRESS, "拥有任意一个指定Ip");


    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    ActionType(String type, String typeDescribe) {
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
        ActionType[] values = ActionType.values();
        for (ActionType value : values) {
            if (value.type.equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static ActionType getComplainByType(String type) {
        ActionType[] values = ActionType.values();
        for (ActionType value : values) {
            if (value.type.equals(type)) {
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
        ActionType[] values = ActionType.values();
        StringBuilder sb = new StringBuilder();
        for (ActionType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ActionType[] values = ActionType.values();
        for (ActionType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeName(value.getTypeDescribe());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
