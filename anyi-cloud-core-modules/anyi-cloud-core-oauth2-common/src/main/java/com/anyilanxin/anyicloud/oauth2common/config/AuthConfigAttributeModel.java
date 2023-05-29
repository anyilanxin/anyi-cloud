

package com.anyilanxin.anyicloud.oauth2common.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * security按钮权限
 *
 * @author zxh
 * @date 2022-03-01 22:40
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AuthConfigAttributeModel implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> authConfigAttribute;
}
