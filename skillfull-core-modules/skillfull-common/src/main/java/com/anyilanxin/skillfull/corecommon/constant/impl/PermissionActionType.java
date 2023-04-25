package com.anyilanxin.skillfull.corecommon.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum PermissionActionType implements ISuperType {
    /**
     * 具备某个指令
     */
    HAS_AUTHORITY("hasAuthority", "具备某个指令"),

    /**
     * 具备多个指令中的任意一个
     */
    HAS_ANY_AUTHORITY("hasAnyAuthority", "具备多个指令中的任意一个"),

    /**
     * 具备某个角色
     */
    HAS_ROLE("hasRole", "具备某个角色"),

    /**
     * 具备多个角色中的任意一个
     */
    HAS_ANY_ROLE("hasAnyRole", "具备多个角色中的任意一个"),

    /**
     * 统统允许访问
     */
    PERMIT_ALL("permitAll", "统统允许访问"),

    /**
     * 统统拒绝访问
     */
    DENY_ALL("denyAll", "统统拒绝访问"),

    /**
     * 匿名用户可访问
     */
    IS_ANONYMOUS("isAnonymous", "匿名用户可访问"),
    /**
     * 授权后访问
     */
    IS_AUTHENTICATED("isAuthenticated", "授权后访问"),
    /**
     * 具备某个权限可访问
     */
    HAS_PERMISSION("hasPermission", "具备某个权限可访问"),

    /**
     * 方法执行前进行权限,基于EL实现
     */
    PRE_AUTHORIZE("PreAuthorize", "方法执行前进行权限,基于EL实现"),

    /**
     * 方法执行后进行权限检查,基于EL实现
     */
    POST_AUTHORIZE("PostAuthorize", "方法执行后进行权限检查,基于EL实现");
    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    PermissionActionType(String type, String typeDescribe) {
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
        PermissionActionType[] values = PermissionActionType.values();
        for (PermissionActionType value : values) {
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
        PermissionActionType[] values = PermissionActionType.values();
        StringBuilder sb = new StringBuilder();
        for (PermissionActionType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        PermissionActionType[] values = PermissionActionType.values();
        for (PermissionActionType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
