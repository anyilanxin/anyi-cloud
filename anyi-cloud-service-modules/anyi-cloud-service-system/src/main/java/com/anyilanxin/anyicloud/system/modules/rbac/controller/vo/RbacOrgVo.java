/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 组织表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 963678539662691043L;

    @Schema(name = "parentId", title = "父级组织id(更新是无效)")
    private String parentId;

    @Schema(name = "orgName", title = "组织名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "组织名称不能为空")
    private String orgName;

    @Schema(name = "orgSimpleName", title = "组织机构简称")
    private String orgSimpleName;

    @Schema(name = "orgNameEn", title = "英文名")
    private String orgNameEn;

    @Schema(name = "orgNameAbbr", title = "缩写")
    private String orgNameAbbr;

    @Schema(name = "orgOrder", title = "排序")
    private Integer orgOrder;

    @Schema(name = "orgType", title = "组织机构类型：1-公司,2-部门", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "组织机构类不能为空")
    // @NotInEnum(autoMessage = true, enumClass = OrgType.class)
    private Integer orgType;

    @Schema(name = "orgCode", title = "组织编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "组织编码不能为空")
    private String orgCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlankOrNull(message = "组织状态不能为空")
    // @NotInEnum(autoMessage = true, enumClass = CommonNotEnableType.class)
    private Integer orgStatus;

    @Schema(name = "email", title = "邮箱")
    private String email;

    @Schema(name = "phone", title = "手机号")
    private String phone;

    @Schema(name = "fax", title = "传真")
    private String fax;

    @Schema(name = "address", title = "地址")
    private String address;

    @Schema(name = "socialCode", title = "统一社会信用代码")
    private String socialCode;

    @Schema(name = "areaCodeName", title = "行政区域名称")
    private String areaCodeName;

    @Schema(name = "areaCode", title = "行政区域")
    private String areaCode;

    @Schema(name = "detailAddress", title = "详细地址")
    private String detailAddress;

    @Schema(name = "scopeBusiness", title = "经验范围")
    private String scopeBusiness;

    @Schema(name = "legalPerson", title = "法人")
    private String legalPerson;

    @Schema(name = "accountsName", title = "开户姓名")
    private String accountsName;

    @Schema(name = "accountsBank", title = "开户银行")
    private String accountsBank;

    @Schema(name = "backCard", title = "银行账号")
    private String backCard;

    @Schema(name = "businessLicensePicture", title = "营业执照")
    private String businessLicensePicture;

    @Schema(name = "sealPicture", title = "印章")
    private String sealPicture;

    @Schema(name = "orgMenuIds", title = "机构菜单权限")
    private Set<String> orgMenuIds;

    @Schema(name = "orgResourceIds", title = "机构资源权限")
    private Set<String> orgResourceIds;
}
