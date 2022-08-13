package com.anyilanxin.skillfull.loggingcommon.constant.impl;


import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
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
public enum OperateType implements ISuperType {


    /**
     * 查询
     */
    QUERY(1, "查询"),

    /**
     * 添加
     */
    ADD(2, "添加"),

    /**
     * 修改
     */
    UPDATE(3, "修改"),

    /**
     * 删除
     */
    DELETE(4, "删除"),


    /**
     * 其他
     */
    OTHER(5, "其他");


    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    OperateType(int type, String typeDescribe) {
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
    public static boolean isHaveByType(int type) {
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static OperateType getComplainByType(int type) {
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
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
        OperateType[] values = OperateType.values();
        StringBuilder sb = new StringBuilder();
        for (OperateType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(String.valueOf(value.getType()));
            dictDto.setTypeName(value.getTypeDescribe());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
