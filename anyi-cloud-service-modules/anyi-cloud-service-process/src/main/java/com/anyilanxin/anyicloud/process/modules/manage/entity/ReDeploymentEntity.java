

package com.anyilanxin.anyicloud.process.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * (ReDeployment)Entity
 *
 * @author zxh
 * @date 2021-11-16 21:10:44
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("ACT_RE_DEPLOYMENT")
public class ReDeploymentEntity implements Serializable {
    private static final long serialVersionUID = 511282777388026196L;
    /**
     * 部署id
     */
    @TableId
    private String id_;

    /**
     * 部署名称
     */
    private String name_;

    /**
     * 部署时间
     */
    private LocalDateTime deployTime_;

    /**
     * 资源名称
     */
    private String source_;

    /**
     * 租户id
     */
    private String tenantId_;

    public String getId() {
        return id_;
    }


    public void setId(String id) {
        this.id_ = id;
    }


    public String getName() {
        return name_;
    }


    public void setName(String name) {
        this.name_ = name;
    }


    public LocalDateTime getDeployTime() {
        return deployTime_;
    }


    public void setDeployTime(LocalDateTime deployTime) {
        this.deployTime_ = deployTime;
    }


    public String getSource() {
        return source_;
    }


    public void setSource(String source) {
        this.source_ = source;
    }


    public String getTenantId() {
        return tenantId_;
    }


    public void setTenantId(String tenantId) {
        this.tenantId_ = tenantId;
    }
}
