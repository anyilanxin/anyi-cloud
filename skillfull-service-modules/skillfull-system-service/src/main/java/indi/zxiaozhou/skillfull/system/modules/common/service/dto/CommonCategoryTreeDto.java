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

import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分类字典表查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:39:55
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema
public class CommonCategoryTreeDto extends BaseTree<CommonCategoryTreeDto> implements Serializable {
    private static final long serialVersionUID = 235699223663179437L;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "remark", title = "备注")
    private String remark;
}