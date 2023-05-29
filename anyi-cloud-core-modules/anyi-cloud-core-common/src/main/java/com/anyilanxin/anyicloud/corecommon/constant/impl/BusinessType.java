

package com.anyilanxin.anyicloud.corecommon.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 业务类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum BusinessType implements ISuperType {
    /**
     * 登录过期
     */
    TOKEN_EXPIRED("LOGIN_DATE", "登录过期"),

    /**
     * 异常消息
     */
    EXCEPTION_MSG("EXCEPTION_MSG", "异常消息"),

    /**
     * 被踢下线
     */
    KICK_LOGIN("KICK_LOGIN", "被踢下线"),

    /**
     * 新临时通知消息
     */
    NEW_TEMPORARY_NOTICE_MSG("NEW_TEMPORARY_NOTICE_MSG", "新临时通知消息"),

    /**
     * 新通知消息
     */
    NEW_NOTICE_MSG("NEW_NOTICE_MSG", "新通知消息");

    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;

    BusinessType(String type, String typeDescribe) {
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
        BusinessType[] values = BusinessType.values();
        for (BusinessType value : values) {
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
        BusinessType[] values = BusinessType.values();
        StringBuilder sb = new StringBuilder();
        for (BusinessType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        BusinessType[] values = BusinessType.values();
        for (BusinessType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
