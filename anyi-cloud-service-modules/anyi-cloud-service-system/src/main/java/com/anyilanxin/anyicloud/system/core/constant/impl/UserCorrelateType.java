

package com.anyilanxin.anyicloud.system.core.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * 用户关联关系类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum UserCorrelateType implements ISuperType {
    /**
     * 组织机构
     */
    ORG(1, "组织机构"),
    /**
     * 职位
     */
    POSITION(2, "职位"),
    /**
     * 用户组
     */
    USER_GROUP(3, "职位");
    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String describe;

    UserCorrelateType(int type, String describe) {
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
        UserCorrelateType[] values = UserCorrelateType.values();
        for (UserCorrelateType value : values) {
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
        UserCorrelateType[] values = UserCorrelateType.values();
        StringBuilder sb = new StringBuilder();
        for (UserCorrelateType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        UserCorrelateType[] values = UserCorrelateType.values();
        for (UserCorrelateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
