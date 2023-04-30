/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程表达式添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-27 00:20:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class DesignExpressionVo implements Serializable {
    private static final long serialVersionUID = -96603288767354418L;

    @Schema(name = "handleType", title = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他", required = true)
    @NotBlankOrNull(message = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他不能为空")
    private Integer handleType;

    @Schema(name = "implementClass", title = "实现class路径")
    private String implementClass;

    @Schema(name = "example", title = "示例")
    private String example;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "expressionName", title = "表达式名称", required = true)
    @NotBlankOrNull(message = "表达式名称不能为空")
    private String expressionName;

    @Schema(name = "expressionType", title = "表达式类型:1-表达式,2-委托表达式", required = true)
    @NotBlankOrNull(message = "表达式类型:1-表达式,2-委托表达式不能为空")
    private Integer expressionType;

    @Schema(name = "elExpressionValue", title = "El编码值,‘${’开头’}‘结尾，唯一", required = true)
    @NotBlankOrNull(message = "El编码值,‘${’开头’}‘结尾，唯一不能为空")
    private String elExpressionValue;

    @Schema(name = "expressionState", title = "表达式状态:0-禁用,1-启用", required = true)
    @NotBlankOrNull(message = "表达式状态:0-禁用,1-启用不能为空")
    private Integer expressionState;
}
