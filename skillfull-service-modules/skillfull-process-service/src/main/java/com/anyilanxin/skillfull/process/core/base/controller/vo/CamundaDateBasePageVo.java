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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.process.core.base.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * camunda分页基类
 *
 * @author zxiaozhou
 * @date 2021-11-07 14:48
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CamundaDateBasePageVo implements Serializable {
  private static final long serialVersionUID = 889985578802106850L;

  @Schema(name = "startTime", title = "开始时间", type = "string", example = "2020-11-12 11:23")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime startTime;

  @Schema(name = "endTime", title = "结束时间", type = "string", example = "2020-11-12 11:23")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime endTime;

  @Schema(name = "current", title = "当前页", example = "1")
  @Builder.Default
  private int current = 0;

  @Schema(name = "size", title = "每页条数", example = "10")
  @Builder.Default
  private int size = 10;

  @Schema(name = "descs", title = "降序字段列表")
  private Set<String> descs;

  @Schema(name = "ascs", title = "升序字段列表")
  private Set<String> ascs;

  public int getCurrent() {
    return current == 0 ? current : current - 1;
  }
}
