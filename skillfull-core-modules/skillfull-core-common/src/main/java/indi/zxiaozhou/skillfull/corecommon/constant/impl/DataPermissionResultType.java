// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.constant.impl;

import indi.zxiaozhou.skillfull.corecommon.annotation.ConstantType;
import indi.zxiaozhou.skillfull.corecommon.constant.ISuperType;
import indi.zxiaozhou.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行数据权限处理的返回值类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum DataPermissionResultType implements ISuperType {
    /**
     * mybatis分页
     */
    I_PAGE("com.baomidou.mybatisplus.core.metadata.IPage", "mybatis分页"),
    /**
     * list集合
     */
    LIST("java.util.List", "list集合"),

    /**
     * set集合
     */
    SET("java.util.Set", "set集合"),

    /**
     * json数组
     */
    JSON_ARRAY("com.alibaba.fastjson.JSONArray", "json数组");

    /**
     * 返回值类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String typeDescribe;


    DataPermissionResultType(String type, String typeDescribe) {
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
        DataPermissionResultType[] values = DataPermissionResultType.values();
        for (DataPermissionResultType value : values) {
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
    public static boolean isHaveFirstContent(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }
        DataPermissionResultType[] values = DataPermissionResultType.values();
        for (DataPermissionResultType value : values) {
            if (type.contains(value.type)) {
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
        DataPermissionResultType[] values = DataPermissionResultType.values();
        StringBuilder sb = new StringBuilder();
        for (DataPermissionResultType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        DataPermissionResultType[] values = DataPermissionResultType.values();
        for (DataPermissionResultType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            dictDto.setTypeName(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
