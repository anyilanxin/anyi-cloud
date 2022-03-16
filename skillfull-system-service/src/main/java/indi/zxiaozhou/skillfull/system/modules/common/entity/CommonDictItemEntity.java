// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典配置项表(CommonDictItem)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:26:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_common_dict_item")
public class CommonDictItemEntity extends BaseEntity {
    private static final long serialVersionUID = 127826571553333406L;

    @TableId
    private String itemId;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;

    @Schema(name = "itemText", title = "字典项名称")
    private String itemText;

    @Schema(name = "itemValue", title = "字典项值")
    private String itemValue;

    @Schema(name = "dictCode", title = "字典编码")
    private String dictCode;

    @Schema(name = "sortOrder", title = "排序,越小越靠前,默认0")
    private Integer sortOrder;

    @Schema(name = "itemStatus", title = "字典项状态：1启用，0禁用，默认0")
    private Integer itemStatus;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除改值未主键")
    private String uniqueHelp;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "remark", title = "备注")
    private String remark;
}