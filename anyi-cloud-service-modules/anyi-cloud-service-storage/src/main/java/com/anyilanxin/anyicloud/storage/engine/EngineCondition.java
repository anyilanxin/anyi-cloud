

package com.anyilanxin.skillfull.storage.engine;

import com.anyilanxin.skillfull.storage.core.constant.impl.StorageType;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * 存储引擎动态加载条件
 *
 * @author zhou
 * @date 2022-07-17 21:58
 * @since 1.0.0
 */
@Slf4j
public class EngineCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MergedAnnotation<Component> componentMergedAnnotation = metadata.getAnnotations().get(Component.class);
        Component component = componentMergedAnnotation.synthesize();
        Environment environment = context.getEnvironment();
        StorageType storageType = environment.getProperty("storage.type", StorageType.class, StorageType.LOCAL);
        return Objects.equals(component.value(), storageType.getStrategy());
    }
}
