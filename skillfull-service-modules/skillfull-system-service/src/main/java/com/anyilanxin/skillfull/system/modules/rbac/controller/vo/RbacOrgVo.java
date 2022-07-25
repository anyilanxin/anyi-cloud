// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * 组织表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:39:45
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgVo implements Serializable {
    private static final long serialVersionUID = 963678539662691043L;

    @Schema(name = "parentId", title = "父级组织id(更新是无效)")
    private String parentId;

    @Schema(name = "orgName", title = "组织名称", required = true)
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

    @Schema(name = "orgType", title = "组织机构类型：1-公司,2-部门", required = true)
    @NotNull(message = "组织机构类不能为空")
//    @NotInEnum(autoMessage = true, enumClass = OrgType.class)
    private Integer orgType;

    @Schema(name = "orgCode", title = "组织编码", required = true)
    @NotBlankOrNull(message = "组织编码不能为空")
    private String orgCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0", required = true)
    @NotBlankOrNull(message = "组织状态不能为空")
//    @NotInEnum(autoMessage = true, enumClass = CommonNotEnableType.class)
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
