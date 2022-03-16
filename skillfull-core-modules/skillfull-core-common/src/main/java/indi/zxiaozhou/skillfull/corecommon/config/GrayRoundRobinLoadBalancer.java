// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.config;

import indi.zxiaozhou.skillfull.corecommon.utils.GrayUtil;
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

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.ENABLE_GRAY_KEY;

/**
 * 灰度策略(基于RoundRobin)
 * <div>
 *     允许灰度指标:
 *     grayIp:服务ip,
 *     grayActive:服务环境(spring.profiles.active)
 *     grayVersion:服务版本(spring.application.version)
 * </div>
 *
 * @author zxiaozhou
 * @date 2021-12-30 17:56
 * @since JDK1.8
 */
@Slf4j
public class GrayRoundRobinLoadBalancer extends RoundRobinLoadBalancer {
    private final AtomicInteger position;
    private final String serviceId;
    private final Environment environment;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                      String serviceId, Environment environment) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000), environment);
    }


    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                      String serviceId, int seedPosition, Environment environment) {
        super(serviceInstanceListSupplierProvider, serviceId, seedPosition);
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
        this.environment = environment;
    }


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        Boolean enableGray = environment.getProperty(ENABLE_GRAY_KEY, Boolean.class);
        // 如果不允许直接调用父类方法处理负载
        if (Objects.isNull(enableGray) || !enableGray) {
            return super.choose(request);
        }
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
    }

    /**
     * 获取灰度实例
     *
     * @param supplier         ${@link ServiceInstanceListSupplier}
     * @param serviceInstances ${@link List<ServiceInstance>}
     * @param request          ${@link Request}
     * @return ServiceInstance> ${@link ServiceInstance>}
     * @author zxiaozhou
     * @date 2022-01-02 19:48
     */
    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances,
                                                              Request<?> request) {
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
     * @author zxiaozhou
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
