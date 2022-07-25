// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程类别(ProcessCategory)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 11:52:04
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("act_custom_process_category")
public class ProcessCategoryEntity extends BaseEntity {
    private static final long serialVersionUID = -30458247825549992L;

    @TableId
    private String categoryId;

    /**
     * 类别编码(唯一)
     */
    private String categoryCode;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别状态:0-禁用,1-启用。默认0
     */
    private Integer categoryState;

    /**
     * 类别描述
     */
    private String categoryDescribe;

    /**
     * 类别图片
     */
    private String pictures;

    /**
     * 备注
     */
    private String remark;
}
