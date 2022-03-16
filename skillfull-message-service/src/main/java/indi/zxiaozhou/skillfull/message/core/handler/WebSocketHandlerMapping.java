// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.core.handler;

import indi.zxiaozhou.skillfull.message.core.annotation.WebSocketController;
import indi.zxiaozhou.skillfull.message.core.annotation.WebSocketMapping;
import indi.zxiaozhou.skillfull.message.modules.websocket.handle.IWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * web socket handle自动注册
 *
 * @author zxiaozhou
 * @date 2021-01-08 12:12
 * @since JDK11
 */
@Slf4j
public class WebSocketHandlerMapping extends SimpleUrlHandlerMapping {
    private final Map<String, WebSocketHandler> handlerMap = new LinkedHashMap<>();

    public WebSocketHandlerMapping() {
        super();
    }

    @Override
    public void initApplicationContext() throws BeansException {
        Map<String, Object> beanMap = obtainApplicationContext().getBeansWithAnnotation(WebSocketController.class);
        beanMap.values().forEach(bean -> {
            if (bean instanceof IWebSocketHandler) {
                WebSocketMapping annotation = AnnotationUtils.getAnnotation(bean.getClass(), WebSocketMapping.class);
                if (Objects.nonNull(annotation)) {
                    String mapping = annotation.value();
                    handlerMap.put(mapping, (WebSocketHandler) bean);
                }
            }
        });
        super.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.setUrlMap(handlerMap);
        super.initApplicationContext();
    }
}
