// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 分类字典表分页查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:40:12
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
public class CommonCategoryPageDto implements Serializable {
    private static final long serialVersionUID = 999040784753501720L;

    @Schema(name = "categoryId", title = "分类id")
    private String categoryId;

    @Schema(name = "parentId", title = "父级id")
    private String parentId;

    @Schema(name = "categoryName", title = "分类名称")
    private String categoryName;

    @Schema(name = "categoryCommonCode", title = "分类统一编码")
    private String categoryCommonCode;

    @Schema(name = "categoryCode", title = "分类编码")
    private String categoryCode;

    @Schema(name = "isParent", title = "是否父节:0-不是，1-是，默认0")
    private Integer isParent;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "hasChildren", title = "是否有子节点,默认false")
    private boolean hasChildren;

    @Schema(name = "remark", title = "备注")
    private String remark;
}