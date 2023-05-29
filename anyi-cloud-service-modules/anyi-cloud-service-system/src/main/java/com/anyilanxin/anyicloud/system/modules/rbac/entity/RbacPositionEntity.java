

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 职位表(RbacPosition)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_rbac_position")
public class RbacPositionEntity extends BaseEntity {
    private static final long serialVersionUID = -42001886261952020L;

    @TableId
    private String positionId;

    /**
     * 职位编码
     */
    private String positionCode;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 绑定方式:0-手动,1-自动。默认0
     */
    private Integer autoBind;

    /**
     * 职级
     */
    private Integer positionRank;

    /**
     * 职位状态：0-无效，1-有效，默认0
     */
    private Integer positionStatus;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
