

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
