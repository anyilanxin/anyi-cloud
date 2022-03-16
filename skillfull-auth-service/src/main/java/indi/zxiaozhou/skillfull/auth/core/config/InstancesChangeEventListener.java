// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zxiaozhou
 * @date 2022-01-01 18:50
 * @since JDK1.8
 */
@Component
public class InstancesChangeEventListener extends Subscriber<InstancesChangeEvent> implements ApplicationEventPublisherAware {
    private static final Logger logger = LoggerFactory.getLogger(InstancesChangeEventListener.class);

    private ApplicationEventPublisher applicationEventPublisher;


    @PostConstruct
    private void post() {
        NotifyCenter.registerSubscriber(this);
    }


    /**
     * Event callback.
     *
     * @param event {@link Event}
     */
    @Override
    public void onEvent(InstancesChangeEvent event) {
        logger.info("接收到 InstancesChangeEvent 订阅事件：{}", JSON.toJSONString(event));
    }

    /**
     * Type of this subscriber's subscription.
     *
     * @return Class which extends {@link Event}
     */
    @Override
    public Class<? extends com.alibaba.nacos.common.notify.Event> subscribeType() {
        return InstancesChangeEvent.class;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}


