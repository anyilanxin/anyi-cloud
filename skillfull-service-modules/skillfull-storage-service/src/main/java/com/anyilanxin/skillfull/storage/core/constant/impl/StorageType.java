// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage.core.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.storage.core.constant.StorageTypeConstant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储单位
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum StorageType implements ISuperType {
    /**
     * 本地存储
     */
    LOCAL(1, StorageTypeConstant.LOCAL_STORAGE, "本地存储"),

    /**
     * ali oss存储
     */
    ALI_OSS(2, StorageTypeConstant.ALI_OSS_STORAGE, "oss存储"),

    /**
     * minio存储
     */
    MINIO(3, StorageTypeConstant.MINIO_STORAGE, "minio存储");

    /**
     * 类型
     */
    private final int type;

    /**
     * 存储策略
     */
    private final String strategy;

    /**
     * 描述
     */
    private final String describe;


    StorageType(int type, String strategy, String describe) {
        this.type = type;
        this.strategy = strategy;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link Integer} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(int type) {
        StorageType[] values = StorageType.values();
        for (StorageType value : values) {
            if (value.type == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过类型查找存储类型
     *
     * @param type ${@link Integer} 类型
     * @return StorageType
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static StorageType getByType(int type) {
        StorageType[] values = StorageType.values();
        for (StorageType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        StorageType[] values = StorageType.values();
        StringBuilder sb = new StringBuilder();
        for (StorageType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        StorageType[] values = StorageType.values();
        for (StorageType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type + "");
            dictDto.setTypeDescribe(value.describe);
            dictDto.setTypeName(value.describe);
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
