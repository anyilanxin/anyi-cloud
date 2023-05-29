

package com.anyilanxin.skillfull.storage.core.config.properties;

import com.anyilanxin.skillfull.storage.core.constant.impl.StorageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 存储引擎配置
 *
 * @author zhou
 * @date 2022-07-17 22:13
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "storage")
public class EngineProperty {
    /**
     * 使用存储类型
     */
    private StorageType type = StorageType.LOCAL;
}
