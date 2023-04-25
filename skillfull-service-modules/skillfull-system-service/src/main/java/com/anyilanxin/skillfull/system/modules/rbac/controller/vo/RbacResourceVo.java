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

package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 资源表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacResourceVo implements Serializable {
  private static final long serialVersionUID = 584443379737783939L;

  @Schema(name = "resourceCode", title = "资源编码,即后端服务名", required = true)
  @NotBlankOrNull(message = "资源编码,即后端服务名不能为空")
  private String resourceCode;

  @Schema(name = "resourceName", title = "资源名称", required = true)
  @NotBlankOrNull(message = "资源名称不能为空")
  private String resourceName;

  @Schema(name = "resourceIcon", title = "资源图标")
  private String resourceIcon;

  @Schema(name = "resourceStatus", title = "状态：0-未启用,1-启用，默认0", required = true)
  @NotBlankOrNull(message = "状态不能为空")
  private Integer resourceStatus;

  @Schema(name = "remark", title = "备注")
  private String remark;

  @Schema(
      name = "requestPrefix",
      title = "资源请求前缀，即server.servlet.context-path值或spring.webflux.base-path值，前缀mvc,后缀webflux")
  private String requestPrefix;

  @Schema(name = "resourceType", title = "资源类型：1-内部服务,2-外部资源", required = true)
  @NotBlankOrNull(message = "资源类型不能为空")
  private Integer resourceType;

  @Schema(name = "resourceDocUrl", title = "资源doc配置地址,当为内部资源时可以不填ip等信息")
  private String resourceDocUrl;

  @Schema(name = "docType", title = "资源doc类型:1-springdoc,2-swagger")
  private Integer docType;
}
