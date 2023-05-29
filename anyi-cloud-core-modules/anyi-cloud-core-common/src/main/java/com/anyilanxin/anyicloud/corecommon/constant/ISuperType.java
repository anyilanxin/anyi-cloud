

package com.anyilanxin.anyicloud.corecommon.constant;

import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.List;

/**
 * 字典常量类型父级(方便反射获取子类)
 *
 * @author zxh
 * @date 2020-09-28 09:42
 * @since 1.0.0
 */
public interface ISuperType {
    /**
     * 获取常量字典
     *
     * @return List<ConstantDictModel> ${@link List<  ConstantDictModel  >}
     * @author zxh
     * @date 2020-09-28 09:44
     */
    List<ConstantDictModel> getConstantDict();
}
