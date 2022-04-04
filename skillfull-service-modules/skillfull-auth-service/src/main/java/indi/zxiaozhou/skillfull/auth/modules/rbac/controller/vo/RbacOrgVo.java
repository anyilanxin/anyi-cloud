// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 组织表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:41
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacOrgVo implements Serializable {
    private static final long serialVersionUID = -55129698456654917L;

    @Schema(name = "parentId", title = "父级组织id")
    private String parentId;

    @Schema(name = "orgName", title = "组织名称", required = true)
    @NotBlankOrNull(message = "组织名称不能为空")
    private String orgName;

    @Schema(name = "orgNameEn", title = "英文名")
    private String orgNameEn;

    @Schema(name = "orgNameAbbr", title = "缩写")
    private String orgNameAbbr;

    @Schema(name = "orgOrder", title = "排序,默认0")
    private int orgOrder;

    @Schema(name = "orgType", title = "组织机构类型：1-公司,2-部门", required = true)
    @NotBlankOrNull(message = "组织机构类型：1-公司,2-部门不能为空")
    private Integer orgType;

    @Schema(name = "orgCode", title = "组织编码", required = true)
    @NotBlankOrNull(message = "组织编码不能为空")
    private String orgCode;

    @Schema(name = "orgStatus", title = "组织状态：0-禁用，1-启用，默认0", required = true)
    @NotBlankOrNull(message = "组织状态：0-禁用，1-启用，默认0不能为空")
    private Integer orgStatus;

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
}