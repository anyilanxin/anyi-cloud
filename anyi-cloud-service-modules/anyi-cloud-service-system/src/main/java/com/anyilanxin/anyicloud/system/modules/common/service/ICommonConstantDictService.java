

package com.anyilanxin.anyicloud.system.modules.common.service;

import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.List;

/**
 * 获取常量字典
 *
 * @author zxh
 * @date 2021-07-27 11:36
 * @since 1.0.0
 */
public interface ICommonConstantDictService {

    /**
     * 获取常量字典
     *
     * @param constantTypes ${@link String} 字典类型，多个英文逗号隔开
     * @return List<ConstantDictModel> ${@link List<ConstantDictModel>}
     * @author zxh
     * @date 2021-07-27 11:37
     */
    List<ConstantDictModel> getListByConstantTypes(String constantTypes);
}
