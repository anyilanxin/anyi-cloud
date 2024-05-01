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

package com.anyilanxin.cloud.process.modules.manage.service.dto;

import com.anyilanxin.cloud.process.extend.model.AnYiUserTaskPropertyModel;
import com.anyilanxin.cloud.processadapter.model.ProcessFormModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程图用户任务信息
 *
 * @author zxh
 * @date 2022-01-03 10:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessTaskInfoDto implements Serializable, Comparable<ProcessTaskInfoDto> {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "taskDefinitionKey", title = "任务定义id")
    protected String taskDefinitionKey;

    @Schema(name = "name", title = "任务名称")
    protected String name;

    @Schema(name = "description", title = "任务描述")
    protected String description;

    @Schema(name = "assignee", title = "审批人")
    protected String assignee;

    @Schema(name = "propertyModel", title = "节点信息扩展属性信息")
    private AnYiUserTaskPropertyModel propertyModel;

    @Schema(name = "formModel", title = "任务表单")
    protected ProcessFormModel formModel;

    @Schema(name = "formKey", title = "任务表单key")
    private String formKey;

    @Schema(name = "formRef", title = "任务表单ref")
    private String formRef;

    @Schema(name = "dueDateStr", title = "到期日期")
    protected String dueDateStr;

    @Schema(name = "dueFormatDate", title = "到期日期格式化值")
    protected String dueFormatDate;

    @Schema(name = "followUpDateStr", title = "跟进日期")
    protected String followUpDateStr;

    @Schema(name = "followUpFormatDate", title = "跟进时间格式化值")
    protected String followUpFormatDate;

    @Schema(name = "taskSort", title = "任务排序值")
    private String taskSort;

    @Schema(name = "priority", title = "优先级")
    protected int priority;

    @Schema(name = "multiInstance", title = "是否多实例")
    private boolean multiInstance;

    @Schema(name = "sequential", title = "是否顺序")
    private boolean sequential;

    @Override
    public int compareTo(ProcessTaskInfoDto o) {
        String nowTaskSort = this.taskSort;
        if (StringUtils.isBlank(nowTaskSort)) {
            nowTaskSort = StringUtils.isNotBlank(this.taskDefinitionKey) ? this.taskDefinitionKey : "";
        }
        String currentTaskSort = o.taskSort;
        if (StringUtils.isBlank(currentTaskSort)) {
            currentTaskSort = StringUtils.isNotBlank(o.taskDefinitionKey) ? o.taskDefinitionKey : "";
        }
        return nowTaskSort.compareTo(currentTaskSort);
    }
}
