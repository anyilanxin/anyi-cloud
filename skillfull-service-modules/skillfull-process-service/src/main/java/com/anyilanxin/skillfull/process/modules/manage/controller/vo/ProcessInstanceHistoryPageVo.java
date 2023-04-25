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

package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程实例删除
 *
 * @author zxiaozhou
 * @date 2020-10-20 10:01
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessInstanceHistoryPageVo extends BasePageVo implements Serializable {
  private static final long serialVersionUID = -2625200294039929340L;

  @Schema(name = "processDefinitionId", title = "流程定义id")
  private String processDefinitionId;

  @Schema(name = "instanceState", title = "实例状态,来源于常量字典:ProcessInstanceState")
  private Integer instanceState;

  @Schema(name = "title", title = "流程实例标题")
  private String title;

  @Schema(name = "finished", title = "是否结束")
  private Boolean finished;

  @Schema(name = "realName", title = "真实姓名")
  private String realName;

  @Schema(name = "processDefinitionName", title = "流程定义名称")
  private String processDefinitionName;
}
