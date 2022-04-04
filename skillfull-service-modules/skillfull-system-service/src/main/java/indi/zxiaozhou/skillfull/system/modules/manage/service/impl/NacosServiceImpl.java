// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.NacosAllInstancesQueryVo;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.NacosGroupNameVo;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.NacosSubscribeVo;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.NacosUpdateInstanceVo;
import indi.zxiaozhou.skillfull.system.modules.manage.service.ICustomNacosNamingService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.INacosService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ServiceInstanceDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ServiceInstanceDetailMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * nacos open api接口二次封装实现
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 11:13
 * @since JDK1.8
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
    private final CoreWebMvcProperty property;
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
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 15:32
     */
    private ServiceInstanceDto createServiceInstanceDto(List<Instance> instances) {
        ServiceInstanceDto serviceInstanceDto = new ServiceInstanceDto();
        if (CollectionUtil.isNotEmpty(instances)) {
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
