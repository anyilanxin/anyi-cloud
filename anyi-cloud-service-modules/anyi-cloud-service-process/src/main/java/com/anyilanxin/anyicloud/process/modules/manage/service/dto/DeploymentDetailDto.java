/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 部署信息
 *
 * @author zxh
 * @date 2020-10-15 08:45
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DeploymentDetailDto implements Serializable {
    private static final long serialVersionUID = 8084599820581857851L;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "processDefinitionKey", title = "流程定义key")
    private String processDefinitionKey;

    @Schema(name = "processDefinitionId", title = "最新流程定义Id")
    private String processDefinitionId;

    @Schema(name = "suspensionState", title = "定义暂停状态")
    private int suspensionState;

    @Schema(name = "isNew", title = "是否最新版本")
    private boolean isNew;

    @Schema(name = "version", title = "当前版本")
    private int version;

    @Schema(name = "versionTag", title = "流程版本tag")
    private String versionTag;

    @Schema(name = "resourceNames", title = "流程资源名称")
    private String resourceNames;

    @Schema(name = "diagramData", title = "bpmn blob文件")
    private String diagramData;

    @Schema(name = "hasStartFormKey", title = "是否存在开始表单key")
    private boolean hasStartFormKey;

    @Schema(name = "startFormKey", title = "开始表单key")
    private String startFormKey;

    @Schema(name = "category", title = "类别")
    private String category;

    @Schema(name = "historyTimeToLive", title = "历史存活时间(单位:天，引擎自定义不可变更单位)")
    private Integer historyTimeToLive;
}
