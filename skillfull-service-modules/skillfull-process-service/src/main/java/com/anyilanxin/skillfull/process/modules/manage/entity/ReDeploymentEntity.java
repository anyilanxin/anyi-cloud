/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.process.modules.manage.entity;

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
* @author zxiaozhou
* @date 2021-11-16 21:10:44
* @since JDK1.8
*/
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("ACT_RE_DEPLOYMENT")
public class ReDeploymentEntity implements Serializable {
    private static final long serialVersionUID = 511282777388026196L;
    /** 部署id */
    @TableId private String id_;

    /** 部署名称 */
    private String name_;

    /** 部署时间 */
    private LocalDateTime deployTime_;

    /** 资源名称 */
    private String source_;

    /** 租户id */
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
