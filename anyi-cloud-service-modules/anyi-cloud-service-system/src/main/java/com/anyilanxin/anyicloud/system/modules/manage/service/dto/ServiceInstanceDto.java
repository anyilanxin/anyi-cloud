/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 服务实例信息
 *
 * @author zxh zxh
 * @date 2020-10-11 13:24
 * @since 1.0.0
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ServiceInstanceDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2013577984078828501L;

    @Schema(name = "instanceNum", title = "实例总数")
    private int instanceNum;

    @Schema(name = "healthyNum", title = "健康实例数量")
    private int healthyNum;

    @Schema(name = "unhealthyNum", title = "不健康数量")
    private int unhealthyNum;

    @Schema(name = "enabledNum", title = "能否接收请求实例数量")
    private int enabledNum;

    @Schema(name = "ephemeralNum", title = "临时实例数量")
    private int ephemeralNum;

    @Schema(name = "serviceName", title = "服务名称")
    private String serviceName;

    @Schema(name = "instanceMap", title = "服务实例id,服务实例信息")
    @JsonIgnore
    private Map<String, Instance> instanceMap;

    @Schema(name = "serviceInstanceDetails", title = "服务实例详细")
    private List<ServiceInstanceDetail> serviceInstanceDetails;

    @Getter
    @Setter
    @ToString

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class ServiceInstanceDetail implements Serializable {
        @Serial
        private static final long serialVersionUID = 4086735209634588642L;

        @Schema(name = "instanceId", title = "实例唯一id")
        private String instanceId;

        @Schema(name = "ip", title = "实例ip")
        private String ip;

        @Schema(name = "port", title = "实例端口")
        private int port;

        @Schema(name = "weight", title = "实例权重")
        @Builder.Default
        private double weight = 1.0D;

        @Schema(name = "healthy", title = "实例健康状态")
        @Builder.Default
        private boolean healthy = false;

        @Schema(name = "enabled", title = "实例是否接受请求")
        @Builder.Default
        private boolean enabled = true;

        @Schema(name = "ephemeral", title = "是否临时实例")
        @Builder.Default
        private boolean ephemeral = true;

        @Schema(name = "clusterName", title = "实例集群信息")
        private String clusterName;

        @Schema(name = "serviceName", title = "实例服务名")
        private String serviceName;

        @Schema(name = "metadata", title = "元数据")
        private Map<String, String> metadata;
    }
}
