// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2common.authinfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限信息
 *
 * @author zxiaozhou
 * @date 2022-05-13 15:44
 * @since JDK1.8
 */
public class SkillFullGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    /**
     * 权限
     */
    private final String authority;
    /**
     * 所属资源
     */
    private final String resourceId;
    /**
     * 属性
     */
    private final Map<String, Object> attributes;

    public SkillFullGrantedAuthority(Map<String, Object> attributes, String resourceId) {
        this("ROLE_USER", attributes, resourceId);
    }

    public SkillFullGrantedAuthority(String authority, Map<String, Object> attributes, String resourceId) {
        this.authority = authority;
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        this.resourceId = resourceId;
    }

    public SkillFullGrantedAuthority(String authority, String resourceId) {
        this.authority = authority;
        this.attributes = Collections.emptyMap();
        this.resourceId = resourceId;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }


    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public String getResourceId() {
        return resourceId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SkillFullGrantedAuthority that = (SkillFullGrantedAuthority) obj;
        if (!this.getAuthority().equals(that.getAuthority())) {
            return false;
        }
        return this.getAttributes().equals(that.getAttributes());
    }

    @Override
    public int hashCode() {
        int result = this.getAuthority().hashCode();
        result = 31 * result + this.getAttributes().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getAuthority();
    }

}
