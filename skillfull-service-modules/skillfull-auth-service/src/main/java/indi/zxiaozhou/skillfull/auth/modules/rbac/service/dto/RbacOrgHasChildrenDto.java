// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 组织表查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:36
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacOrgHasChildrenDto implements Serializable {
    private static final long serialVersionUID = -23698600939298122L;

    @Schema(name = "orgId", title = "组织id")
    private String orgId;

    @Schema(name = "parentId", title = "父级组织id")
    private String parentId;

    @Schema(name = "orgName", title = "组织名称")
    private String orgName;

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

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;

    @Schema(name = "phone", title = "手机号")
    private String phone;

    @Schema(name = "fax", title = "传真")
    private String fax;

    @Schema(name = "address", title = "地址")
    private String address;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "hasChildren", title = "是否有子节点:true-有,false-无 ,(boolean类型),与前端共用children列数据")
    private boolean hasChildren;
}