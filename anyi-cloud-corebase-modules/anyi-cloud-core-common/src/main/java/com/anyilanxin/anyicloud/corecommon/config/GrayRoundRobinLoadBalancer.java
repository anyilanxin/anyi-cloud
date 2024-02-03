/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.config;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.utils.GrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 灰度策略(基于RoundRobin)
 * <div>
 *     允许灰度指标:
 *     grayIp:服务ip,
 *     grayActive:服务环境(spring.profiles.active)
 *     grayVersion:服务版本(spring.application.version)
 * </div>
 *
 * @author zxh
 * @date 2021-12-30 17:56
 * @since 1.0.0
 */
@Slf4j
public class GrayRoundRobinLoadBalancer extends RoundRobinLoadBalancer {
    private final AtomicInteger position;
    private final String serviceId;
    private final Environment environment;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, Environment environment) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000), environment);
    }


    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, int seedPosition, Environment environment) {
        super(serviceInstanceListSupplierProvider, serviceId, seedPosition);
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
        this.environment = environment;
    }


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        Boolean enableGray = environment.getProperty(CommonCoreConstant.ENABLE_GRAY_KEY, Boolean.class);
        // 如果不允许直接调用父类方法处理负载
        if (Objects.isNull(enableGray) || !enableGray) {
            return super.choose(request);
        }
        return supplier.get(request).next().map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
    }


    /**
     * 获取灰度实例
     *
     * @param supplier         ${@link ServiceInstanceListSupplier}
     * @param serviceInstances ${@link List<ServiceInstance>}
     * @param request          ${@link Request}
     * @return ServiceInstance> ${@link ServiceInstance>}
     * @author zxh
     * @date 2022-01-02 19:48
     */
    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances, Request<?> request) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }


    /**
     * 灰度实例计算
     *
     * @param instances ${@link List<ServiceInstance>}
     * @param request   ${@link Request}
     * @return Response<ServiceInstance> ${@link Response<ServiceInstance>}
     * @author zxh
     * @date 2022-01-02 19:48
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request<?> request) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }
        GrayUtil.GrayModel grayModel = GrayUtil.getInstance(instances, request);
        instances = grayModel.getInstances();
        int pos = Math.abs(this.position.incrementAndGet());
        ServiceInstance instance = instances.get(pos % instances.size());
        if (grayModel.isGraySuccess()) {
            log.debug("------------GrayRoundRobinLoadBalancer------灰度调度成功(RoundRobin)------>:\nserviceId:{},instanceId:{},scheme:{},host:{},port:{},metadata:{}", instance.getServiceId(), instance.getInstanceId(), instance.getScheme(), instance.getHost(), instance.getPort(), instance.getMetadata());
        }
        return new DefaultResponse(instance);
    }
}
