// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatapermmvc.constant.impl;

import indi.zxiaozhou.skillfull.corecommon.annotation.ConstantType;
import indi.zxiaozhou.skillfull.corecommon.constant.ISuperType;
import indi.zxiaozhou.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据权限规则类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum RoleDataRuleType implements ISuperType {
    /**
     * 全部
     */
    ALL("ALL", "全部"),

    /**
     * 自己
     */
    OWN("OWN", "自己"),

    /**
     * 多条件
     */
    MANY_CONDITIONS("MANY_CONDITIONS", "多条件"),

    /**
     * 本级
     */
    SAME_LEVEL("SAME_LEVEL", "本级"),

    /**
     * 本级以及子级
     */
    SAME_OR_CHILD_LEVEL("SAME_OR_CHILD_LEVEL", "本级以及子级"),

    /**
     * 自定义
     */
    CUSTOM("CUSTOM", "自定义");

    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    RoleDataRuleType(String type, String typeDescribe) {
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
        RoleDataRuleType[] values = RoleDataRuleType.values();
        for (RoleDataRuleType value : values) {
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
        RoleDataRuleType[] values = RoleDataRuleType.values();
        StringBuilder sb = new StringBuilder();
        for (RoleDataRuleType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        RoleDataRuleType[] values = RoleDataRuleType.values();
        for (RoleDataRuleType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
