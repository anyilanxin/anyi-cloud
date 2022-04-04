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
import cn.hutool.http.HttpStatus;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.CommonParams;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.net.NamingProxy;
import com.alibaba.nacos.client.naming.utils.InitUtils;
import com.alibaba.nacos.client.naming.utils.UtilAndComs;
import com.alibaba.nacos.client.utils.ValidatorUtils;
import com.alibaba.nacos.common.utils.HttpMethod;
import com.alibaba.nacos.common.utils.StringUtils;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.InstancePageVo;
import indi.zxiaozhou.skillfull.system.modules.manage.service.ICustomNacosNamingService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.NacosNamespacesDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ServiceInstancePageDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ServiceInstancePageMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private NamingProxy serverProxy;
    private final ServiceInstancePageMap instancePageMap;
    private String clusterName;

    @Autowired
    public CustomNacosNamingServiceImpl(NacosDiscoveryProperties properties, ServiceInstancePageMap instancePageMap) throws NacosException {
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
        this.serverProxy = new NamingProxy(this.namespace, this.endpoint, this.serverList, properties.getNacosProperties());
    }


    private void initServerAddr(Properties properties) {
        serverList = properties.getProperty(PropertyKeyConst.SERVER_ADDR);
        endpoint = InitUtils.initEndpoint(properties);
        if (StringUtils.isNotEmpty(endpoint)) {
            serverList = "";
        }
    }


    @Override
    public List<Instance> getAllInstances(String serviceCode, String groupName, List<String> clusters) throws RuntimeException {
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
        if (CollectionUtil.isNotEmpty(clusters)) {
            clusterName = StringUtils.join(clusters, ",");
        }
        params.put("groupName", groupName);
        params.put("pageSize", String.valueOf(Integer.MAX_VALUE));
        params.put("clusterName", clusterName);
        params.put("pageNo", "1");
        try {
            List<Instance> instances = Collections.emptyList();
            String result = serverProxy.reqApi(UtilAndComs.nacosUrlBase + "/catalog/instances", params, HttpMethod.GET);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("count") >= 1) {
                    instances = JSONObject.parseArray(jsonObject.getString("list"), Instance.class);
                }
            }
            return instances;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------------>getAllInstances:serviceName-{},groupName-{},errMsg-{}", serviceCode, groupName, e.getErrMsg());
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
            String result = serverProxy.reqApi(UtilAndComs.nacosUrlBase + "/catalog/instances", params, HttpMethod.GET);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                count = jsonObject.getIntValue("count");
                if (count >= 1) {
                    List<Instance> instances = JSONObject.parseArray(jsonObject.getString("list"), Instance.class);
                    pageDtos = new ArrayList<>(count);
                    for (Instance instance : instances) {
                        pageDtos.add(instancePageMap.bToA(instance));
                    }
                }
            }
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------------>getAllInstances:serviceName-{},groupName-{},errMsg-{}", vo.getServiceCode(), groupName, e.getErrMsg());
        }
        return new PageDto<>(count, pageDtos);
    }


    @Override
    public List<NacosNamespacesDto> getAllNamespaces() throws RuntimeException {
        final Map<String, String> params = new HashMap<>(0);
        try {
            String result = serverProxy.reqApi(UtilAndComs.webContext + "/v1/console/namespaces", params, HttpMethod.GET);
            List<NacosNamespacesDto> nacosNamespacesDtos = Collections.emptyList();
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("code") == HttpStatus.HTTP_OK) {
                    nacosNamespacesDtos = JSONObject.parseArray(jsonObject.getString("data"), NacosNamespacesDto.class);
                }
            }
            return nacosNamespacesDtos;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------查询命名空间异常------>getAllInstances:{}", e.getErrMsg());
            throw new ResponseException(Status.ERROR, "查询命名空间异常:" + e.getErrMsg());
        }
    }


    @Override
    public List<NacosServiceInfoDto> getServicesOfServer(Integer pageNo, Integer pageSize, String groupName) throws RuntimeException {
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
            String result = serverProxy.reqApi(UtilAndComs.nacosUrlBase + "/catalog/services", params, HttpMethod.GET);
            List<NacosServiceInfoDto> nacosServiceInfoDtos = Collections.emptyList();
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getIntValue("count") >= 1) {
                    nacosServiceInfoDtos = JSONObject.parseArray(jsonObject.getString("serviceList"), NacosServiceInfoDto.class);
                }
            }
            return nacosServiceInfoDtos;
        } catch (NacosException e) {
            e.printStackTrace();
            log.error("------------INacosServiceImpl------查询组下所有服务异常------>getServicesOfServer:groupName-{},errMsg-{}", groupName, e.getErrMsg());
            throw new ResponseException(Status.ERROR, "查询" + groupName + "组服务异常:" + e.getErrMsg());
        }
    }


}
