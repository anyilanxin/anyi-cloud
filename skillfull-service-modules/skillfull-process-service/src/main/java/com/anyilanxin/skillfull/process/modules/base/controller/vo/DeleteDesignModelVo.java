/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 删除流程模型
 *
 * @author zxiaozhou
 * @date 2020-10-20 11:25
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DeleteDesignModelVo implements Serializable {
    private static final long serialVersionUID = -3016812291917256003L;

    @Schema(name = "modelId", title = "模型id不能为空", required = true)
    @NotBlank(message = "模型id不能为空")
    private String modelId;

    @Schema(name = "cascade", title = "是否级联，默认false")
    @Builder.Default
    private Boolean cascade = false;

    @Schema(name = "skipCustomListeners", title = "是否跳过自定义监听器，默认false")
    @Builder.Default
    private Boolean skipCustomListeners = false;

    @Schema(name = "skipIoMappings", title = "是否跳过io映射，默认false")
    @Builder.Default
    private Boolean skipIoMappings = false;
}
