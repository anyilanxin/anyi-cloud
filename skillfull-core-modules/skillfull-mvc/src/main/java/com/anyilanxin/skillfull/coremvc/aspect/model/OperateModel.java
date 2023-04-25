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

package com.anyilanxin.skillfull.coremvc.aspect.model;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 操作日志添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-09-14 23:39:55
 * @since JDK11
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class OperateModel implements Serializable {
  private static final long serialVersionUID = -72227624406240356L;

  @Schema(name = "logNote", title = "日志注解")
  private String logNote;

  @Schema(name = "operateType", title = " 操作类型（1查询，2添加，3修改，4删除，5其他）", required = true)
  @NotBlankOrNull(message = " 操作类型不能为空")
  private Integer operateType;

  @Schema(name = "fileName", title = "调用文件名")
  private String fileName;

  @Schema(name = "methodName", title = "调用方法名")
  private String methodName;

  @Schema(name = "methodParams", title = "调用方法参数")
  private String methodParams;

  @Schema(name = "exceptionName", title = "异常名")
  private String exceptionName;

  @Schema(name = "stackTrace", title = "堆栈信息")
  private String stackTrace;

  @Schema(name = "exceptionMessage", title = "异常消息")
  private String exceptionMessage;

  @Schema(name = "requestResult", title = "请求结果")
  private String requestResult;

  @Schema(name = "requestParam", title = "请求参数")
  private String requestParam;

  @Schema(name = "lineNumber", title = "代码行数")
  private Integer lineNumber;

  @Schema(
      name = "requestStartTime",
      title = "请求开始时间(创建实例时自动处理)",
      hidden = true,
      type = "string",
      example = "2020-12-21 12:22:21")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
  private LocalDateTime requestStartTime;

  @Schema(name = "remark", title = "备注")
  private String remark;
}
