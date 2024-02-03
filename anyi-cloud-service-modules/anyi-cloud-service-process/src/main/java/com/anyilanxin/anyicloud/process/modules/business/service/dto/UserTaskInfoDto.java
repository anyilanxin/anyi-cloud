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

package com.anyilanxin.anyicloud.process.modules.business.service.dto;

import com.anyilanxin.anyicloud.process.extend.model.AnYiUserTaskPropertyModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 任务列表
 *
 * @author zxh
 * @date 2020-10-19 19:40
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserTaskInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "taskName", title = "任务名称")
    private String taskName;

    @Schema(name = "description", title = "任务描述")
    private String description;

    @Schema(name = "taskDefinitionKey", title = "任务定义key")
    private String taskDefinitionKey;

    @Schema(name = "formKey", title = "表单key")
    private String formKey;

    @Schema(name = "formRef", title = "表单ref")
    private String formRef;

    @Schema(name = "propertyModel", title = "自定义扩展属性")
    private AnYiUserTaskPropertyModel propertyModel;

    @Schema(name = "owner", title = "委托办理人id(查询历史任务时存在)")
    private String owner;

    @Schema(name = "ownerName", title = "委托办理人名称(查询历史任务时存在)")
    private String ownerName;

    @Schema(name = "assignee", title = "所属机构id(查询历史任务时存在)")
    private String assignee;

    @Schema(name = "assigneeName", title = "审批用户名称(查询历史任务时存在)")
    private String assigneeName;
}
