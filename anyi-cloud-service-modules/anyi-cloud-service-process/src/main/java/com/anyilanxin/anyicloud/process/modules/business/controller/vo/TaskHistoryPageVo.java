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

package com.anyilanxin.anyicloud.process.modules.business.controller.vo;

import com.anyilanxin.anyicloud.process.core.base.controller.vo.CamundaDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 获取我的代办
 *
 * @author zxh
 * @date 2020-10-19 19:37
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TaskHistoryPageVo extends CamundaDateBasePageVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2741579373373551412L;

    @Schema(name = "processTitle", title = "流程标题(模糊查询)")
    private String processTitle;

    @Schema(name = "applyUserName", title = "流程申请人(模糊查询)")
    private String applyUserName;

    @Schema(name = "businessKey", title = "业务id(模糊查询)")
    private String businessKey;

    @Schema(name = "applyUserOrgId", title = "流程申请人所属机构(精确查询)")
    private String applyUserOrgId;

    @Schema(name = "category", title = "流程类型(精确查询)")
    private String category;

    @Schema(name = "processFormEquals", title = "流程表单精确匹配查询字段")
    private Map<String, String> processFormEquals;

    @Schema(name = "processFormLike", title = "流程表单模糊匹配查询字段")
    private Map<String, String> processFormLike;

    @Schema(name = "processFormTime", title = "流程表单时间匹配")
    private Map<String, QueryTimeVo> processFormTime;

    @Schema(name = "taskName", title = "任务名称(模糊查询)")
    private String taskName;

    @Schema(name = "taskTitle", title = "任务标题(模糊查询)")
    private String taskTitle;

    @Schema(name = "description", title = "任务描述(模糊查询)")
    private String description;

    @Schema(name = "taskFormEquals", title = "任务表单精确匹配查询字段")
    private Map<String, String> taskFormEquals;

    @Schema(name = "dueDateStart", title = "到期开始时间")
    private Date dueDateStart;

    @Schema(name = "dueDateEnd", title = "到期结束时间")
    private Date dueDateEnd;

    @Schema(name = "followUpDateStart", title = "跟进日期开始时间")
    private Date followUpDateStart;

    @Schema(name = "followUpDateEnd", title = "跟进日期结束时间")
    private Date followUpDateEnd;

    @Schema(name = "finishedStart", title = "任务完成开始时间")
    private Date finishedStart;

    @Schema(name = "finishedEnd", title = "任务完成结束时间")
    private Date finishedEnd;

    @Schema(name = "taskState", title = "任务状态:具体与UserTaskState常量字典一致")
    private Integer taskState;
}
