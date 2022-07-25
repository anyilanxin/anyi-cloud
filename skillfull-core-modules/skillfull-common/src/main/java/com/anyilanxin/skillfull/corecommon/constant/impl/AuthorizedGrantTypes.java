// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum AuthorizedGrantTypes implements ISuperType {

    /**
     * 图片验证码授权
     */
    PICTURE_CODE("picture_code", "图片验证码模式"),

    /**
     * 短信验证码授权
     */
    SMS_CODE("sms_code", "短信验证码模式"),

    /**
     * 客户端授权
     */
    CLIENT_CREDENTIALS("client_credentials", "客户端模式"),

    /**
     * 授权码授权
     */
    AUTHORIZATION_CODE("authorization_code", "授权码模式"),

    /**
     * 开放id授权
     */
    OPEN_ID("open_id", "开放id模式"),

    /**
     * 刷新token
     */
    REFRESH_TOKEN("refresh_token", "刷新token");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;


    AuthorizedGrantTypes(String type, String describe) {
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
        AuthorizedGrantTypes[] values = AuthorizedGrantTypes.values();
        for (AuthorizedGrantTypes value : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        AuthorizedGrantTypes[] values = AuthorizedGrantTypes.values();
        StringBuilder sb = new StringBuilder();
        for (AuthorizedGrantTypes value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        AuthorizedGrantTypes[] values = AuthorizedGrantTypes.values();
        for (AuthorizedGrantTypes value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
