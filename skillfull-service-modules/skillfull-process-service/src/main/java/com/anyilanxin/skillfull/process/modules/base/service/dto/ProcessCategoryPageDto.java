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

package com.anyilanxin.skillfull.process.modules.base.service.dto;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程类别分页查询Response
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ProcessCategoryPageDto implements Serializable {
  private static final long serialVersionUID = 694284762200872007L;

  @Schema(name = "categoryId", title = "类别id")
  private String categoryId;

  @Schema(name = "categoryCode", title = "类别编码(唯一)")
  private String categoryCode;

  @Schema(name = "categoryName", title = "类别名称")
  private String categoryName;

  @Schema(name = "categoryState", title = "类别状态:0-禁用,1-启用。默认0")
  private Integer categoryState;

  @Schema(name = "pictures", title = "类别logo")
  private String pictures;

  @Schema(name = "categoryDescribe", title = "类别描述")
  private String categoryDescribe;

  @Schema(name = "remark", title = "备注")
  private String remark;

  @Schema(name = "createUserId", title = "创建用户id")
  private String createUserId;

  @Schema(name = "createUserName", title = "创建用户姓名")
  private String createUserName;

  @Schema(name = "createTime", title = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
  private LocalDateTime createTime;
}
