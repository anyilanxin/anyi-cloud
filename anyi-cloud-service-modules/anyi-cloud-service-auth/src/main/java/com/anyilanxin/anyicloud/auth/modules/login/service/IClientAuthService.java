

package com.anyilanxin.anyicloud.auth.modules.login.service;

import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:17
 * @since 1.0.0
 */
public interface IClientAuthService {

    /**
     * 通过客户端id查询详情
     *
     * @param clientId 客户端id
     * @return SystemClientDetailsModel 查询结果
     * @author zxh
     * @date 2022-02-12 22:15
     */
    ClientAndResourceAuthModel getByClientId(String clientId);
}
