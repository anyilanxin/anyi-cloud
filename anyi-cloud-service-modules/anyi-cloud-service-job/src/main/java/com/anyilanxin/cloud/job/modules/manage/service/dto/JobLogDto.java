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

package com.anyilanxin.cloud.job.modules.manage.service.dto;

import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * job日志查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-06-25 00:41:52
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class JobLogDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 531906548239417279L;

    @Schema(name = "id", title = "job日志id")
    private Long id;

    @Schema(name = "jobGroup", title = "执行器主键ID")
    private Integer jobGroup;

    @Schema(name = "jobId", title = "任务，主键ID")
    private Integer jobId;

    @Schema(name = "executorAddress", title = "执行器地址，本次执行的地址")
    private String executorAddress;

    @Schema(name = "executorHandler", title = "执行器任务handler")
    private String executorHandler;

    @Schema(name = "executorParam", title = "执行器任务参数")
    private String executorParam;

    @Schema(name = "executorShardingParam", title = "执行器任务分片参数，格式如 1/2")
    private String executorShardingParam;

    @Schema(name = "executorFailRetryCount", title = "失败重试次数")
    private Integer executorFailRetryCount;

    @Schema(name = "triggerTime", title = "调度-时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime triggerTime;

    @Schema(name = "triggerCode", title = "调度-结果")
    private Integer triggerCode;

    @Schema(name = "triggerMsg", title = "调度-日志")
    private String triggerMsg;

    @Schema(name = "handleTime", title = "执行-时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime handleTime;

    @Schema(name = "handleCode", title = "执行-状态")
    private Integer handleCode;

    @Schema(name = "handleMsg", title = "执行-日志")
    private String handleMsg;

    @Schema(name = "alarmStatus", title = "告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败")
    private Integer alarmStatus;

}
