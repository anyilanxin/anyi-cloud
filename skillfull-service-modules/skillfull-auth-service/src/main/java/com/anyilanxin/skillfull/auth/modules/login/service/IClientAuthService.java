// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.service;

import com.anyilanxin.skillfull.corecommon.model.system.ClientAndResourceAuthModel;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:17
 * @since JDK1.8
 */
public interface IClientAuthService {


    /**
     * 通过客户端id查询详情
     *
     * @param clientId 客户端id
     * @return SystemClientDetailsModel 查询结果
     * @author zxiaozhou
     * @date 2022-02-12 22:15
     */
    ClientAndResourceAuthModel getByClientId(String clientId);
}
