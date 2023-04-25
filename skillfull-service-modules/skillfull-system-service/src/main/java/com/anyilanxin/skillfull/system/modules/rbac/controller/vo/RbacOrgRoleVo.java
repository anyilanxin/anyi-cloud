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
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构角色表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgRoleVo implements Serializable {
    private static final long serialVersionUID = -36211334953329117L;

    @Schema(name = "roleName", title = "角色名称", required = true)
    @NotBlankOrNull(message = "角色名称不能为空")
    private String roleName;

    @Schema(name = "roleCode", title = "角色编码", required = true)
    @NotBlankOrNull(message = "角色编码不能为空")
    private String roleCode;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0，挂接机构时自动挂接", required = true)
    @NotBlankOrNull(message = "绑定方式:0-手动,1-自动。默认0，挂接机构时自动挂接不能为空")
    private Integer autoBind;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0", required = true)
    @NotBlankOrNull(message = "角色状态:0-禁用,1-启用,默认0不能为空")
    private Integer roleStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlankOrNull(message = "机构id不能为空")
    private String orgId;

}
