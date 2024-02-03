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

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 流程定义迁移
 *
 * @author zxh
 * @date 2021-07-26 11:22
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class MigrationVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -8623417618389717556L;

    @Schema(name = "sourceProcessDefinitionId", title = "原流程定义id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "原流程定义id不能为空")
    private String sourceProcessDefinitionId;

    @Schema(name = "targetProcessDefinitionId", title = "目标流程定义id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "目标流程定义id不能为空")
    private String targetProcessDefinitionId;

    @Schema(name = "processInstanceIds", title = "流程实例id,不传则当前流程定义下的所有实例都会迁移")
    private Set<String> processInstanceIds;

    @Schema(name = "skipCustomListener", title = "是否跳过自定义监听器,默认否")
    private boolean skipCustomListener;

    @Schema(name = "autoActivities", title = "是否默认自动映射活动节点(需要迁移前活动节点在迁移后流程中存在),默认否")
    private boolean autoActivities;

    @Schema(name = "activitiesMap", title = "活动节点迁移映射关系，当autoActivities存在时可不填")
    private Map<String, String> activitiesMap;

    @Schema(name = "skipIoMapping", title = "是否跳过io映射器,默认否")
    private boolean skipIoMapping;

    @Schema(name = "async", title = "是否异步,默认否")
    private boolean async;
}
