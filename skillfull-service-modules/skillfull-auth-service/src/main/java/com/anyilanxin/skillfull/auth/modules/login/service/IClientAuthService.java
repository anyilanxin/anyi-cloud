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
