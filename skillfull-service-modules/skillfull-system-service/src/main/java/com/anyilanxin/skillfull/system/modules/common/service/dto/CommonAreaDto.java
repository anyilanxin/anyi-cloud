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

package com.anyilanxin.skillfull.system.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域表查询Response
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:03
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class CommonAreaDto implements Serializable {
  private static final long serialVersionUID = -97880700851357588L;

  @Schema(name = "areaId", title = "区域id")
  private String areaId;

  @Schema(name = "parentId", title = "上级区域id")
  private String parentId;

  @Schema(name = "prePinYin", title = "区域名称拼音的第一个字母")
  private String prePinYin;

  @Schema(name = "simplePy", title = "首字母简拼")
  private String simplePy;

  @Schema(name = "pinYin", title = "区域名称拼音")
  private String pinYin;

  @Schema(name = "wholeName", title = "完整名称")
  private String wholeName;

  @Schema(name = "provinceId", title = "所属省级id")
  private String provinceId;

  @Schema(name = "simpleName", title = "中文简称")
  private String simpleName;

  @Schema(name = "areaLevel", title = "级别：1为省级，2为市级，3为县级, 4为乡, 5为村")
  private Integer areaLevel;

  @Schema(name = "areaName", title = "区域名称")
  private String areaName;

  @Schema(name = "areaCode", title = "区号")
  private String areaCode;

  @Schema(name = "cityId", title = "所属城市id")
  private String cityId;

  @Schema(name = "lon", title = "本区域经度")
  private String lon;

  @Schema(name = "lat", title = "本区域纬度")
  private String lat;

  @Schema(name = "zipCode", title = "邮编")
  private String zipCode;

  @Schema(name = "countyId", title = "区县id")
  private String countyId;

  @Schema(name = "remark", title = "备注/说明")
  private String remark;

  @Schema(name = "createUserName", title = "创建用户姓名")
  private String createUserName;
}
