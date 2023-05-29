/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.NacosAllInstancesQueryVo;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.NacosGroupNameVo;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.NacosSubscribeVo;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.NacosUpdateInstanceVo;
import com.anyilanxin.anyicloud.system.modules.manage.service.ICustomNacosNamingService;
import com.anyilanxin.anyicloud.system.modules.manage.service.INacosService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.NacosServiceInfoDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ServiceInstanceDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.ServiceInstanceDetailMap;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * nacos open api接口二次封装实现
 *
 * @author zxh zxiaozhou
 * @date 2020-10-11 11:13
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NacosServiceImpl implements INacosService {
    private final NamingService namingService;
    private final ServiceInstanceDetailMap detailMap;
    private final NamingMaintainService namingMaintainService;
    private final NacosDiscoveryProperties properties;
    private final NacosEventListener nacosEventListener;
    private final ICustomNacosNamingService customNacosNamingService;

    @Override
    public void subscribe(NacosSubscribeVo vo) throws RuntimeException {
        String groupName = vo.getGroupName();
        String serviceName = vo.getServiceName();
        if (StringUtils.isBlank(groupName)) {
            groupName = properties.getGroup();
            if (StringUtils.isBlank(groupName)) {
                groupName = Constants.DEFAULT_GROUP;
            }
        }
        try {
            namingService.unsubscribe(serviceName, groupName, nacosEventListener);
            namingService.subscribe(serviceName, groupName, nacosEventListener);
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------订阅服务变更失败------>subscribe:groupName-{},serviceName-{},errMsg-{}", groupName, serviceName, e.getErrMsg());
            throw new ResponseException(Status.ERROR, "订阅" + serviceName + "变更通知异常:" + e.getErrMsg());
        }
    }


    @Override
    public void unsubscribe(NacosSubscribeVo vo) throws RuntimeException {
        String groupName = vo.getGroupName();
        String serviceName = vo.getServiceName();
        if (StringUtils.isBlank(groupName)) {
            groupName = properties.getGroup();
        }
        try {
            namingService.unsubscribe(serviceName, groupName, nacosEventListener);
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------取消服务变化订阅------>unsubscribe:groupName-{},serviceName-{},errMsg-{}", groupName, serviceName, e.getErrMsg());
            throw new ResponseException(Status.ERROR, "取消" + serviceName + "服务变化订阅异常:" + e.getErrMsg());
        }
    }


    @Override
    public ServiceInstanceDto getAllInstances(NacosAllInstancesQueryVo vo) throws RuntimeException {
        String groupName = vo.getGroupName();
        String serviceCode = vo.getServiceCode();
        List<Instance> allInstances = customNacosNamingService.getAllInstances(serviceCode, groupName, null);
        return createServiceInstanceDto(allInstances);
    }


    /**
     * 服务实例信息转为自定义格式
     *
     * @param instances ${@link List<Instance>} 待转换实例
     * @return ServiceInstanceDto ${@link ServiceInstanceDto} 转换后结果
     * @author zxh zxiaozhou
     * @date 2020-10-11 15:32
     */
    private ServiceInstanceDto createServiceInstanceDto(List<Instance> instances) {
        ServiceInstanceDto serviceInstanceDto = new ServiceInstanceDto();
        if (CollUtil.isNotEmpty(instances)) {
            String[] serviceNameInfos = instances.get(0).getServiceName().split("@@");
            String serviceName = serviceNameInfos[serviceNameInfos.length - 1];
            serviceInstanceDto.setServiceName(serviceName);
            serviceInstanceDto.setInstanceNum(instances.size());
            int healthyNum = 0;
            int enabledNum = 0;
            int ephemeralNum = 0;
            List<ServiceInstanceDto.ServiceInstanceDetail> serviceInstanceDetails = new ArrayList<>();
            Map<String, Instance> instanceMap = new HashMap<>(4);
            for (Instance instance : instances) {
                ServiceInstanceDto.ServiceInstanceDetail instanceDetail = detailMap.bToA(instance);
                instanceDetail.setServiceName(serviceName);
                instance.setServiceName(serviceName);
                serviceInstanceDetails.add(instanceDetail);
                instanceMap.put(instance.getInstanceId(), instance);
                if (instance.isEnabled()) {
                    enabledNum++;
                }
                if (instance.isEphemeral()) {
                    ephemeralNum++;
                }
                if (instance.isHealthy()) {
                    healthyNum++;
                }
            }
            serviceInstanceDto.setHealthyNum(healthyNum);
            serviceInstanceDto.setInstanceMap(instanceMap);
            serviceInstanceDto.setUnhealthyNum(serviceInstanceDto.getInstanceNum() - healthyNum);
            serviceInstanceDto.setEnabledNum(enabledNum);
            serviceInstanceDto.setEphemeralNum(ephemeralNum);
            serviceInstanceDto.setServiceInstanceDetails(serviceInstanceDetails);
        }
        return serviceInstanceDto;
    }


    @Override
    public void updateInstance(NacosUpdateInstanceVo vo) throws RuntimeException {
        String groupName = properties.getGroup();
        if (StringUtils.isBlank(groupName)) {
            groupName = Constants.DEFAULT_GROUP;
        }
        NacosAllInstancesQueryVo queryVo = new NacosAllInstancesQueryVo();
        queryVo.setGroupName(groupName);
        queryVo.setServiceCode(vo.getServiceCode());
        ServiceInstanceDto allInstances = this.getAllInstances(queryVo);
        Map<String, Instance> instanceMap = allInstances.getInstanceMap();
        List<ServiceInstanceDto.ServiceInstanceDetail> serviceInstanceDetails = allInstances.getServiceInstanceDetails();
        if (CollectionUtil.isEmpty(serviceInstanceDetails)) {
            throw new ResponseException(Status.ERROR, "查询" + groupName + "组中" + groupName + "服务不存在");
        }
        Instance instance = instanceMap.get(vo.getInstanceId());
        if (Objects.isNull(instance)) {
            throw new ResponseException(Status.ERROR, vo.getServiceCode() + "服务实例不存在");
        }
        instance.setEnabled(vo.getType() == 1);
        try {
            namingMaintainService.updateInstance(instance.getServiceName(), groupName, instance);
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------------>updateInstance:vo-{},errMsg-{}", vo, e.getErrMsg());
            throw new ResponseException(Status.ERROR, "更新" + groupName + "组中" + vo.getServiceCode() + "服务" + vo.getInstanceId() + "实例状态异常:" + e.getErrMsg());
        }
    }


    @Override
    public List<NacosServiceInfoDto> getServices(NacosGroupNameVo vo) throws RuntimeException {
        return customNacosNamingService.getServicesOfServer(null, null, vo.getGroupName());
    }
}
