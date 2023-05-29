

package com.anyilanxin.anyicloud.system.modules.common.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典表(CommonDict)Entity
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
@TableName("sys_common_dict")
public class CommonDictEntity extends BaseEntity {
    private static final long serialVersionUID = 379821196050855712L;

    @TableId
    private String dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典状态：1启用，0禁用，默认0
     */
    private Integer dictStatus;

    /**
     * 字典类型：0-字符串,1-数字,2-布尔。默认0
     */
    private Integer dictType;

    /**
     * 唯一索引帮助字段,默认1，如果删除改值未主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
