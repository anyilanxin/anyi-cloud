package com.anyilanxin.skillfull.system.modules.common.service;

import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;

import java.util.List;

/**
 * 获取常量字典
 *
 * @author zxiaozhou
 * @date 2021-07-27 11:36
 * @since JDK1.8
 */
public interface ICommonConstantDictService {

    /**
     * 获取常量字典
     *
     * @param constantTypes ${@link String} 字典类型，多个英文逗号隔开
     * @return List<ConstantDictModel> ${@link List<ConstantDictModel>}
     * @author zxiaozhou
     * @date 2021-07-27 11:37
     */
    List<ConstantDictModel> getListByConstantTypes(String constantTypes);
}
