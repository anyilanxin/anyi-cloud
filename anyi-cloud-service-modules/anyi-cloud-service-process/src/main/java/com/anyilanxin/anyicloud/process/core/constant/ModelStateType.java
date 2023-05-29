

package com.anyilanxin.anyicloud.process.core.constant;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 流程人员身份类型
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum ModelStateType implements ISuperType {
    /**
     * 未部署
     */
    NO_DEPLOYMENT(0, "未部署"),

    /**
     * 已经部署
     */
    DEPLOYMENT(1, "已经部署"),

    /**
     * 新版本待部署
     */
    NEW_VERSION(2, "新版本待部署");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String describe;

    ModelStateType(int type, String describe) {
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
    public static boolean isHaveByType(int type) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.type == type) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据类型获取LB
     *
     * @param type ${@link String} 类型
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static ModelStateType getByType(int type) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取某个字符串前面匹配的Lb类型
     *
     * @param describe ${@link String} 待匹配的字符串
     * @return LbType ${@link ModelStateType} 匹配的类型
     * @author zxh
     * @date 2020-09-28 09:32
     */
    public static ModelStateType getByTypeName(String describe) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.describe.equalsIgnoreCase(describe)) {
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
        ModelStateType[] values = ModelStateType.values();
        StringBuilder sb = new StringBuilder();
        for (ModelStateType value : values) {
            sb.append("、").append(value.type).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
