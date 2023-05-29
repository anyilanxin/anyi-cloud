

package com.anyilanxin.anyicloud.corecommon.config.properties;

import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * skillfull oauth2配置
 *
 * @author zxh
 * @date 2022-08-10 02:44
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "skillfull.oauth")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SkillfullOauthProperty {

    /**
     * 授权类型
     */
    private AuthorizedGrantTypes grantType = AuthorizedGrantTypes.CLIENT_CREDENTIALS;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;
}
