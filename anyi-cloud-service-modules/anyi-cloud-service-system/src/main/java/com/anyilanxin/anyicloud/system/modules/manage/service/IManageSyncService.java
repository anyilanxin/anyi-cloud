

package com.anyilanxin.anyicloud.system.modules.manage.service;

/**
 * 同步接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:16
 * @since 1.0.0
 */
public interface IManageSyncService {
    /**
     * 同步路由
     *
     * @param force ${@link Boolean} true-强行,false-非强行
     * @author zxh
     * @date 2021-12-22 22:00
     */
    void reloadRoute(boolean force);


    /**
     * 同步指定服务路由
     *
     * @param serviceId 服务id
     * @author zxh
     * @date 2021-12-22 22:00
     */
    void updateServiceRoute(String serviceId);


    /**
     * 删除服务路由
     *
     * @param serviceId 服务id
     * @author zxh
     * @date 2021-12-22 22:00
     */
    void deleteServiceRoute(String serviceId);
}
