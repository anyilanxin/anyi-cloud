// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 分类字典表(CommonCategory)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:59:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName("sys_common_category")
public class CommonCategoryEntity extends BaseEntity {
    private static final long serialVersionUID = -29101329593126397L;

    @TableId
    private String categoryId;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类统一编码
     */
    private String categoryCommonCode;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 是否父节:0-不是，1-时，默认0
     */
    private Integer isParent;

    /**
     * 备注
     */
    private String remark;
}
