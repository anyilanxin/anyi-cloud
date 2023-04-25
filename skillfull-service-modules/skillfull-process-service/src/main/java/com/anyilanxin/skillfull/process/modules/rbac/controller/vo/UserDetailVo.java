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

package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class UserDetailVo implements Serializable {
  private static final long serialVersionUID = 6364921052776119371L;

  @Schema(name = "nickName", title = "用户昵称")
  private String nickName;

  @Schema(name = "shortProfile", title = "个人简介")
  private String shortProfile;

  @Schema(name = "avatar", title = "头像")
  private String avatar;

  @Schema(name = "birthday", title = "生日", type = "string", example = "2020-12-21")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
  private LocalDate birthday;

  @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
  private Integer sex;

  @Schema(name = "phone", title = "电话号码")
  private String phone;

  @Schema(name = "workNo", title = "工号，唯一键")
  private String workNo;

  @Schema(name = "telephone", title = "座机号")
  private String telephone;

  @Schema(name = "password", title = "密码(新增时有效)")
  private String password;
}
