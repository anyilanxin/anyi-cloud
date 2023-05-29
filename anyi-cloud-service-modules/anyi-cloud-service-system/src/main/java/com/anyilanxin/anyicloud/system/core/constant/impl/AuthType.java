

package com.anyilanxin.anyicloud.system.core.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 授权类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum AuthType implements ISuperType {
    /**
     * 图片验证码授权
     */
    PICTURE_CODE("PICTURE_CODE", "图片验证码授权"),

    /**
     * 短信验证码授权
     */
    SMS_CODE("SMS_CODE", "短信验证码授权"),

    /**
     * 客户端授权
     */
    CLIENT_AUTH("CLIENT_AUTH", "客户端授权"),

    /**
     * 授权码授权
     */
    AUTH_CODE("AUTH_CODE", "授权码授权"),

    /**
     * 开放id授权
     */
    OPEN_ID("OPEN_ID", "开放id授权"),

    /**
     * 退出授权
     */
    EXIT_AUTH("EXIT_AUTH", "退出授权");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;

    AuthType(String type, String describe) {
        this.type = type;
        this.describe = describe;
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
        AuthType[] values = AuthType.values();
        for (AuthType value : values) {
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
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
