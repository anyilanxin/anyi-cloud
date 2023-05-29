

package com.anyilanxin.anyicloud.gateway.modules.manage.service;

import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;

/**
 * 工具了服务
 *
 * @author zxh
 * @date 2020-09-28 10:03
 * @since 1.0.0
 */
public interface IToolService {

    /**
     * 获取请求安全基础信息
     *
     * @return WebSecurityModel ${@link WebSecurityModel}
     * @author zxh
     * @date 2021-07-13 09:50
     */
    WebSecurityModel getBaseSecurity();


    /**
     * 获取请求安全基础信息
     *
     * @param serialNumber ${@link String} 待刷新的请求序列号
     * @return WebSecurityModel ${@link WebSecurityModel}
     * @author zxh
     * @date 2021-07-13 09:50
     */
    WebSecurityModel getRefreshBaseSecurity(String serialNumber);
}
