/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.processrpc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
* 用户组信息
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
@Schema
public class SyncGroupRequestModel implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;

    @Schema(name = "groupId", title = "用户组id", required = true)
    @NotBlank(message = "用户组id不能为空")
    private String groupId;

    @Schema(name = "name", title = "用户组名称", required = true)
    @NotBlank(message = "用户组名称不能为空")
    private String name;

    @Schema(name = "code", title = "用户组编码", required = true)
    @NotBlank(message = "用户组编码不能为空")
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SyncGroupRequestModel)) {
            return false;
        }
        SyncGroupRequestModel that = (SyncGroupRequestModel) o;
        return Objects.equals(getGroupId(), that.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId());
    }
}
