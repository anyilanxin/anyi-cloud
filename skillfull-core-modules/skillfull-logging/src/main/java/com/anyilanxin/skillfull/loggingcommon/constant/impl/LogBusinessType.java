// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.loggingcommon.constant.impl;


import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.List;

/**
 * 业务操作类型
 *
 * @author zxiaozhou
 * @date 2022-04-04 18:15
 * @since JDK1.8
 */
@Getter
@ConstantType
public enum LogBusinessType implements ISuperType {
    /**
     * 其它
     */
    OTHER("OTHER", "其它"),

    /**
     * 新增
     */
    INSERT("INSERT", "新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE", "修改"),

    /**
     * 删除
     */
    DELETE("DELETE", "删除"),

    /**
     * 授权
     */
    GRANT("GRANT", "授权"),

    /**
     * 导出
     */
    EXPORT("EXPORT", "导出"),

    /**
     * 导入
     */
    IMPORT("IMPORT", "导入");

    /**
     * 类型
     */
    private final String type;

    /**
     * 类型描述
     */
    private final String typeDescribe;


    LogBusinessType(String type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }

    @Override
    public List<ConstantDictModel> getConstantDict() {
        return null;
    }
}
