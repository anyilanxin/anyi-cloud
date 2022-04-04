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
 * 数据字典表(CommonDict)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:26:32
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
@TableName("sys_common_dict")
public class CommonDictEntity extends BaseEntity {
    private static final long serialVersionUID = -11234642979323454L;

    @TableId
    private String dictId;

    @Schema(name = "dictName", title = "字典名称")
    private String dictName;

    @Schema(name = "dictCode", title = "字典编码")
    private String dictCode;

    @Schema(name = "dictStatus", title = "字典状态：1启用，0禁用，默认0")
    private Integer dictStatus;

    @Schema(name = "dictType", title = "字典类型：0-字符串,1-数字,2-布尔。默认0")
    private Integer dictType;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除改值未主键")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注")
    private String remark;

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
}