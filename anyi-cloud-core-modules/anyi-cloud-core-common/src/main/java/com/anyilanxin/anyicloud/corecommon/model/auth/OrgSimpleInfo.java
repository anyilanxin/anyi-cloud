/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.anyicloud.corecommon.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构基本信息
 *
 * @author zxh
 * @date 2022-02-14 17:16
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
public class OrgSimpleInfo implements Serializable {
    private static final long serialVersionUID = 1654336717080L;

    @Schema(name = "orgId", title = "组织id")
    private String orgId;

    @Schema(name = "parentId", title = "父级组织id")
    private String parentId;

    @Schema(name = "orgName", title = "组织名称")
    private String orgName;

    @Schema(name = "orgSimpleName", title = "组织机构简称")
    private String orgSimpleName;

    @Schema(name = "orgNameEn", title = "英文名")
    private String orgNameEn;

    @Schema(name = "orgNameAbbr", title = "缩写")
    private String orgNameAbbr;

    @Schema(name = "orgOrder", title = "排序")
    private Integer orgOrder;

    @Schema(name = "orgType", title = "组织机构类型：1-公司,2-部门")
    private Integer orgType;

    @Schema(name = "orgCode", title = "组织编码")
    private String orgCode;

    @Schema(name = "orgSysCode", title = "组织编码(系统)")
    private String orgSysCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0")
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

    @Schema(name = "areaCode", title = "行政区域")
    private String areaCode;

    @Schema(name = "areaCodeName", title = "行政区域名称")
    private String areaCodeName;

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

    @Schema(name = "additionalInformation", title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
