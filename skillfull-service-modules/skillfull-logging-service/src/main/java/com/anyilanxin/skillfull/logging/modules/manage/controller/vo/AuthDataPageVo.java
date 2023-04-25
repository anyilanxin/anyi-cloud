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

package com.anyilanxin.skillfull.logging.modules.manage.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 登录日志分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-01-26 21:53:03
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class AuthDataPageVo extends BasePageVo {
  private static final long serialVersionUID = 107385952712755796L;

  @Schema(name = "logCode", title = "日志编号")
  private String logCode;

  @Schema(name = "requestIp", title = "请求ip")
  private String requestIp;

  @Schema(name = "authType", title = "授权类型，具体参考常量字典AuthorizedGrantTypes")
  private String authType;

  @Schema(name = "authUserName", title = "授权用户名称")
  private String authUserName;

  @Schema(name = "authClientName", title = "授权客户端名称")
  private String authClientName;

  @Schema(name = "authStatus", title = "授权状态：0-失败,1-成功")
  private Integer authStatus;
}
