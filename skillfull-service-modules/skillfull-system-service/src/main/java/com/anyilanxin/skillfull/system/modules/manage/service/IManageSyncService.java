// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service;

/**
 * 同步接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:16
 * @since JDK1.8
 */
public interface IManageSyncService {
    /**
     * 同步路由
     *
     * @param force ${@link Boolean} true-强行,false-非强行
     * @author zxiaozhou
     * @date 2021-12-22 22:00
     */
    void reloadRoute(boolean force);


    /**
     * 同步指定服务路由
     *
     * @param serviceId 服务id
     * @author zxiaozhou
     * @date 2021-12-22 22:00
     */
    void updateServiceRoute(String serviceId);


    /**
     * 删除服务路由
     *
     * @param serviceId 服务id
     * @author zxiaozhou
     * @date 2021-12-22 22:00
     */
    void deleteServiceRoute(String serviceId);
}
