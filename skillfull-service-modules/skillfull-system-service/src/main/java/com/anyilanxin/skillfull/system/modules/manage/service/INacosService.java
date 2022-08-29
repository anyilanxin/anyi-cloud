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

import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosAllInstancesQueryVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosGroupNameVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosSubscribeVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosUpdateInstanceVo;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ServiceInstanceDto;

import java.util.List;

/**
 * nacos open api接口二次封装
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 10:50
 * @since JDK1.8
 */
public interface INacosService {

    /**
     * 订阅服务变化监听
     *
     * @param vo ${@link NacosSubscribeVo} 查询条件
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 13:03
     */
    void subscribe(NacosSubscribeVo vo) throws RuntimeException;


    /**
     * 取消服务变化订阅
     *
     * @param vo ${@link NacosSubscribeVo} 查询条件
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 13:03
     */
    void unsubscribe(NacosSubscribeVo vo) throws RuntimeException;

    /**
     * 服务实例上下线
     *
     * @param vo ${@link NacosUpdateInstanceVo} 查询条件
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-11 13:43
     */
    void updateInstance(NacosUpdateInstanceVo vo) throws RuntimeException;


    /**
     * 查询某个服务所有实例
     *
     * @param vo ${@link NacosAllInstancesQueryVo} 查询条件
     * @return ServiceInstanceDto ${@link ServiceInstanceDto} 实例
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 13:03
     */
    ServiceInstanceDto getAllInstances(NacosAllInstancesQueryVo vo) throws RuntimeException;


    /**
     * 获取已经注册的服务
     *
     * @param vo ${@link NacosGroupNameVo} 查询条件
     * @return List<String> ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 13:21
     */
    List<NacosServiceInfoDto> getServices(NacosGroupNameVo vo) throws RuntimeException;
}
