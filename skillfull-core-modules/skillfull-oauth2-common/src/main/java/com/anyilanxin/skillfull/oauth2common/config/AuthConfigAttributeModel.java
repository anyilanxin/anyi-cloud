package com.anyilanxin.skillfull.oauth2common.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * security按钮权限
 *
 * @author zxiaozhou
 * @date 2022-03-01 22:40
 * @since JDK1.8
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AuthConfigAttributeModel implements Serializable {
    private static final long serialVersionUID = -5725052195449698245L;

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> authConfigAttribute;
}
