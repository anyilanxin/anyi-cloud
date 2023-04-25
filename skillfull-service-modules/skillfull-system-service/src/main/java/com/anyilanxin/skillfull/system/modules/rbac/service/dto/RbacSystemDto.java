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

package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 系统查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacSystemDto implements Serializable {
  private static final long serialVersionUID = 189324587707962855L;

  @Schema(name = "systemId", title = "系统id")
  private String systemId;

  @Schema(name = "systemName", title = "系统名称")
  private String systemName;

  @Schema(name = "systemCode", title = "系统编码")
  private String systemCode;

  @Schema(name = "systemDescribe", title = "系统描述")
  private String systemDescribe;

  @Schema(name = "icon", title = "系统图标")
  private String icon;

  @Schema(name = "remark", title = "备注")
  private String remark;

  @Schema(name = "createUserName", title = "创建用户姓名")
  private String createUserName;

  @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
  private LocalDateTime createTime;

  @Schema(name = "updateUserName", title = "更新用户姓名")
  private String updateUserName;

  @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
  private LocalDateTime updateTime;
}
