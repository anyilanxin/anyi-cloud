// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
