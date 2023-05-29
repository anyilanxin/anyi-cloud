

package com.anyilanxin.anyicloud.system.core.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * 角色关联关系类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum RoleCorrelateType implements ISuperType {
    /**
     * 组织机构
     */
    ORG(1, "组织机构"),
    /**
     * 职位
     */
    POSITION(2, "职位"),

    /**
     * 个人
     */
    PERSON(3, "个人"),

    /**
     * 用户组
     */
    ROLE_USER_GROUP(4, "用户组");

    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String describe;

    RoleCorrelateType(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link Integer} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(Integer type) {
        RoleCorrelateType[] values = RoleCorrelateType.values();
        for (RoleCorrelateType value : values) {
            if (Objects.nonNull(type) && type.equals(value.type)) {
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
        RoleCorrelateType[] values = RoleCorrelateType.values();
        StringBuilder sb = new StringBuilder();
        for (RoleCorrelateType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        RoleCorrelateType[] values = RoleCorrelateType.values();
        for (RoleCorrelateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
