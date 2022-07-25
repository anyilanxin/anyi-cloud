// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.constant;

import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;

import java.util.List;

/**
 * 字典常量类型父级(方便反射获取子类)
 *
 * @author zxiaozhou
 * @date 2020-09-28 09:42
 * @since JDK11
 */
public interface ISuperType {
    /**
     * 获取常量字典
     *
     * @return List<ConstantDictModel> ${@link  List<  ConstantDictModel  >}
     * @author zxiaozhou
     * @date 2020-09-28 09:44
     */
    List<ConstantDictModel> getConstantDict();

}
