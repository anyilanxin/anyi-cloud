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

package com.anyilanxin.cloud.coreprocess.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 流程定义统计
 *
 * @author zxh
 * @date 2022-01-03 12:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AnYiProcessDefinitionAnalystInfoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(title = "流程xml转base64后数据")
    private String bpmnBase64;

    @Schema(title = "流程定义key")
    private String bpmnProcessDefinitionKey;

    @Schema(title = "流程名称")
    private String bpmnProcessName;

    @Schema(title = "流程描述文本")
    private String bpmnProcessDescription;

    @Schema(title = "流程版本")
    private Integer processVersion;

    @Schema(title = "bpmn流程id")
    private String bpmnProcessId;

    @Schema(title = "定义统计信息")
    private Map<String, AnYiDefinitionStatisticInfo> statisticInfo;

    @Getter
    @Setter
    @ToString
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class AnYiDefinitionStatisticInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 1666533656454L;

        @Schema(title = "值(以完成实例+活动实例)")
        private double value;

        @Schema(title = "元素bpmn id")
        private String bpmnElementId;

        @Schema(title = "当前元素进入连线")
        private Set<String> incomingBpmnIds;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof AnYiDefinitionStatisticInfo))
                return false;
            AnYiDefinitionStatisticInfo that = (AnYiDefinitionStatisticInfo) o;
            return Objects.equals(getBpmnElementId(), that.getBpmnElementId());
        }


        @Override
        public int hashCode() {
            return Objects.hash(getBpmnElementId());
        }
    }
}
