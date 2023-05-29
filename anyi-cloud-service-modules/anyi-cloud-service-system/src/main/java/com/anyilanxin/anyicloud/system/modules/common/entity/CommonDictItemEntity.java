

package com.anyilanxin.anyicloud.system.modules.common.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典配置项表(CommonDictItem)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 11:59:50
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_common_dict_item")
public class CommonDictItemEntity extends BaseEntity {
    private static final long serialVersionUID = -75577601007558139L;

    @TableId
    private String itemId;

    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典项名称
     */
    private String itemText;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 排序,越小越靠前,默认0
     */
    private Integer sortOrder;

    /**
     * 字典项状态：1启用，0禁用，默认0
     */
    private Integer itemStatus;

    /**
     * 唯一索引帮助字段,默认1，如果删除改值未主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
