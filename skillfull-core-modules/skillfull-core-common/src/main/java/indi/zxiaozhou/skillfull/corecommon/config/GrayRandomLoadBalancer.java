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
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.ENABLE_GRAY_KEY;

/**
 * 灰度策略(基于Random)
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
public class GrayRandomLoadBalancer extends RandomLoadBalancer {
    private final String serviceId;
    private final Environment environment;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayRandomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                  String serviceId, Environment environment) {
        super(serviceInstanceListSupplierProvider, serviceId);
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.environment = environment;
    }


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        // 如果不允许直接调用父类方法处理负载
        Boolean enableGray = environment.getProperty(ENABLE_GRAY_KEY, Boolean.class);
        if (Objects.isNull(enableGray) || !enableGray) {
            return super.choose(request);
        }
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances,
                                                              Request<?> request) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request<?> request) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }
        GrayUtil.GrayModel grayModel = GrayUtil.getInstance(instances, request);
        instances = grayModel.getInstances();
        int index = ThreadLocalRandom.current().nextInt(instances.size());
        ServiceInstance instance = instances.get(index);
        if (grayModel.isGraySuccess()) {
            log.debug("------------GrayRandomLoadBalancer------灰度调度成功(Random)------>:\nserviceId:{},instanceId:{},scheme:{},host:{},port:{},metadata:{}", instance.getServiceId(), instance.getInstanceId(), instance.getScheme(), instance.getHost(), instance.getPort(), instance.getMetadata());
        }
        return new DefaultResponse(instance);
    }
}
