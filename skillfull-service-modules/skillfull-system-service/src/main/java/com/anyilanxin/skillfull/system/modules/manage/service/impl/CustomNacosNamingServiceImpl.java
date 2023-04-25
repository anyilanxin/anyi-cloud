/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.CommonParams;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.ServerListManager;
import com.alibaba.nacos.client.naming.remote.http.NamingHttpClientManager;
import com.alibaba.nacos.client.naming.remote.http.NamingHttpClientProxy;
import com.alibaba.nacos.client.naming.utils.InitUtils;
import com.alibaba.nacos.client.naming.utils.UtilAndComs;
import com.alibaba.nacos.client.security.SecurityProxy;
import com.alibaba.nacos.client.utils.ValidatorUtils;
import com.alibaba.nacos.common.utils.HttpMethod;
import com.alibaba.nacos.common.utils.StringUtils;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.InstancePageVo;
import com.anyilanxin.skillfull.system.modules.manage.service.ICustomNacosNamingService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosNamespacesDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ServiceInstancePageDto;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ServiceInstancePageMap;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 19:40
 * @since JDK1.8
 */
@Service
@Slf4j
public class CustomNacosNamingServiceImpl implements ICustomNacosNamingService {
    private String namespace;
    private String group;
    private String endpoint;
    private String serverList;
    private NamingHttpClientProxy serverProxy;
    private final ServiceInstancePageMap instancePageMap;
    private String clusterName;

    @Autowired
    public CustomNacosNamingServiceImpl(
            NacosDiscoveryProperties properties, ServiceInstancePageMap instancePageMap)
            throws NacosException {
        init(properties);
        this.instancePageMap = instancePageMap;
    }

    private void init(NacosDiscoveryProperties properties) throws NacosException {
        ValidatorUtils.checkInitParam(properties.getNacosProperties());
        this.namespace = InitUtils.initNamespaceForNaming(properties.getNacosProperties());
        this.group = properties.getGroup();
        this.clusterName = properties.getClusterName();
        initServerAddr(properties.getNacosProperties());
        InitUtils.initWebRootContext(properties.getNacosProperties());
        ServerListManager serverListManager =
                new ServerListManager(properties.getNacosProperties(), namespace);
        SecurityProxy securityProxy =
                new SecurityProxy(
                        properties.getNacosProperties(),
                        NamingHttpClientManager.getInstance().getNacosRestTemplate());
        this.serverProxy =
                new NamingHttpClientProxy(
                        this.namespace,
                        securityProxy,
                        serverListManager,
                        properties.getNacosProperties(),
                        null);
    }

    private void initServerAddr(Properties properties) {
        serverList = properties.getProperty(PropertyKeyConst.SERVER_ADDR);
        endpoint = InitUtils.initEndpoint(properties);
        if (StringUtils.isNotEmpty(endpoint)) {
            serverList = "";
        }
    }

    @Override
    public List<Instance> getAllInstances(String serviceCode, String groupName, List<String> clusters)
            throws RuntimeException {
        final Map<String, String> params = new HashMap<>(8);
        params.put(CommonParams.NAMESPACE_ID, namespace);
        params.put(CommonParams.SERVICE_NAME, serviceCode);
        if (StringUtils.isBlank(groupName)) {
            groupName = group;
            if (StringUtils.isBlank(groupName)) {
                groupName = Constants.DEFAULT_GROUP;
            }
        }
        String clusterName = Constants.DEFAULT_CLUSTER_NAME;
        if (CollUtil.isNotEmpty(clusters)) {
            clusterName = StringUtils.join(clusters, ",");
        }
        params.put("groupName", groupName);
        params.put("pageSize", String.valueOf(Integer.MAX_VALUE));
        params.put("clusterName", clusterName);
        params.put("pageNo", "1");
        try {
            List<Instance> instances = Collections.emptyList();
            String result =
                    serverProxy.reqApi(
                            UtilAndComs.nacosUrlBase + "/catalog/instances", params, HttpMethod.GET);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("count") >= 1) {
                    instances = JSON.parseArray(jsonObject.getString("list"), Instance.class);
                }
            }
            return instances;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(
                    "------------INacosServiceImpl------------>getAllInstances:serviceName-{},groupName-{},errMsg-{}",
                    serviceCode,
                    groupName,
                    e.getErrMsg());
            return Collections.emptyList();
        }
    }

    @Override
    public PageDto<ServiceInstancePageDto> selectInstancePage(InstancePageVo vo) {
        final Map<String, String> params = new HashMap<>(8);
        params.put(CommonParams.NAMESPACE_ID, namespace);
        params.put(CommonParams.SERVICE_NAME, vo.getServiceCode());
        String groupName = vo.getGroupName();
        if (StringUtils.isBlank(groupName)) {
            groupName = group;
            if (StringUtils.isBlank(groupName)) {
                groupName = Constants.DEFAULT_GROUP;
            }
        }
        String clusterName = Constants.DEFAULT_CLUSTER_NAME;
        if (StringUtils.isNotBlank(clusterName)) {
            clusterName = this.clusterName;
        }
        params.put("groupName", groupName);
        params.put("pageSize", String.valueOf(vo.getSize()));
        params.put("clusterName", clusterName);
        params.put("pageNo", String.valueOf(vo.getCurrent()));
        List<ServiceInstancePageDto> pageDtos = Collections.emptyList();
        int count = 0;
        try {
            String result =
                    serverProxy.reqApi(
                            UtilAndComs.nacosUrlBase + "/catalog/instances", params, HttpMethod.GET);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                count = jsonObject.getIntValue("count");
                if (count >= 1) {
                    List<Instance> instances = JSON.parseArray(jsonObject.getString("list"), Instance.class);
                    pageDtos = new ArrayList<>(count);
                    for (Instance instance : instances) {
                        pageDtos.add(instancePageMap.bToA(instance));
                    }
                }
            }
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(
                    "------------INacosServiceImpl------------>getAllInstances:serviceName-{},groupName-{},errMsg-{}",
                    vo.getServiceCode(),
                    groupName,
                    e.getErrMsg());
        }
        return new PageDto<>(count, pageDtos);
    }

    @Override
    public List<NacosNamespacesDto> getAllNamespaces() throws RuntimeException {
        final Map<String, String> params = new HashMap<>(0);
        try {
            String result =
                    serverProxy.reqApi(
                            UtilAndComs.webContext + "/v1/console/namespaces", params, HttpMethod.GET);
            List<NacosNamespacesDto> nacosNamespacesDtos = Collections.emptyList();
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("code") == HttpStatus.HTTP_OK) {
                    nacosNamespacesDtos =
                            JSON.parseArray(jsonObject.getString("data"), NacosNamespacesDto.class);
                }
            }
            return nacosNamespacesDtos;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(
                    "------------INacosServiceImpl------查询命名空间异常------>getAllInstances:{}", e.getErrMsg());
            throw new ResponseException(Status.ERROR, "查询命名空间异常:" + e.getErrMsg());
        }
    }

    @Override
    public List<NacosServiceInfoDto> getServicesOfServer(
            Integer pageNo, Integer pageSize, String groupName) throws RuntimeException {
        final Map<String, String> params = new HashMap<>(8);
        if (StringUtils.isBlank(groupName)) {
            groupName = group;
            if (StringUtils.isBlank(groupName)) {
                groupName = Constants.DEFAULT_GROUP;
            }
        }
        params.put("groupName", groupName);
        params.put("pageSize", String.valueOf(Objects.isNull(pageSize) ? Integer.MAX_VALUE : pageSize));
        params.put("pageNo", String.valueOf(Objects.isNull(pageNo) ? Integer.MAX_VALUE : pageNo));
        params.put("hasIpCount", String.valueOf(true));
        params.put("withInstances", String.valueOf(false));
        params.put(CommonParams.NAMESPACE_ID, namespace);
        try {
            String result =
                    serverProxy.reqApi(
                            UtilAndComs.nacosUrlBase + "/catalog/services", params, HttpMethod.GET);
            List<NacosServiceInfoDto> nacosServiceInfoDtos = Collections.emptyList();
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("count") >= 1) {
                    nacosServiceInfoDtos =
                            JSON.parseArray(jsonObject.getString("serviceList"), NacosServiceInfoDto.class);
                }
            }
            return nacosServiceInfoDtos;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(
                    "------------INacosServiceImpl------查询组下所有服务异常------>getServicesOfServer:groupName-{},errMsg-{}",
                    groupName,
                    e.getErrMsg());
            throw new ResponseException(Status.ERROR, "查询" + groupName + "组服务异常:" + e.getErrMsg());
        }
    }
}
