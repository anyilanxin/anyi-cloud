

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.utils.tree.model.BaseTree;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 组织表分页查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class RbacOrgTreePageDto extends BaseTree<RbacOrgTreePageDto> implements Serializable {
    private static final long serialVersionUID = 559650666267330827L;

    @Schema(name = "orgId", title = "组织id")
    private String orgId;

    @Schema(name = "leaderUserId", title = "负责人id")
    private String leaderUserId;

    @Schema(name = "parentId", title = "父级组织id")
    private String parentId;

    @Schema(name = "orgSysCode", title = "组织编码(系统)")
    private String orgSysCode;

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

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;
}
