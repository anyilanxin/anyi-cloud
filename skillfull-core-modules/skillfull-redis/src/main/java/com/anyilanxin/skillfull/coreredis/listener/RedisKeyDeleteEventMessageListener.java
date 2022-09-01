package com.anyilanxin.skillfull.coreredis.listener;


import com.anyilanxin.skillfull.coreredis.utils.CoreRedisCommonUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.lang.Nullable;

/**
 * redis数据被删除监听
 *
 * @author zxiaozhou
 * @date 2021-07-09 21:45
 * @since JDK1.8
 */
public class RedisKeyDeleteEventMessageListener extends KeyspaceEventMessageListener implements ApplicationEventPublisherAware {
    private static final Topic KEY_EVENT_DELETE_TOPIC = new PatternTopic("__keyevent@*__:del");


    @Nullable
    private ApplicationEventPublisher publisher;


    public RedisKeyDeleteEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEY_EVENT_DELETE_TOPIC);
    }

    @Override
    protected void doHandleMessage(Message message) {
        this.publishEvent(new RedisKeyExpiredEvent(message.getBody()));
    }

    protected void publishEvent(RedisKeyExpiredEvent event) {
        if (this.publisher != null) {
            this.publisher.publishEvent(event);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    /**
     * 获取服务锁(如果锁不存在则自动添加),过期时间10s
     *
     * @param key ${@link String}
     * @author zxiaozhou
     * @date 2021-08-19 16:38
     */
    public boolean serviceLock(String key) {
        return CoreRedisCommonUtils.createRedisServiceLock(key, 10L);
    }
}
