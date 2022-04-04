// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 自定义过滤器(ManageCustomFilter)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:14
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_manage_custom_filter")
@Schema
public class ManageCustomFilterEntity extends BaseEntity {
    private static final long serialVersionUID = -17039860292153687L;

    @TableId
    private String customFilterId;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "filterName", title = "过滤器名称")
    private String filterName;

    @Schema(name = "filterTypeName", title = "过滤器类型名称")
    private String filterTypeName;

    @Schema(name = "filterType", title = "过滤器类型")
    private String filterType;

    @Schema(name = "filterStatus", title = "过滤器状态:0-禁用,1-启用，默认0")
    private Integer filterStatus;

    @Schema(name = "rules", title = "过滤器规则:{key:value}")
    private String rules;

    @Schema(name = "haveSpecial", title = "是否有特殊url:0-没有,1-有。默认0")
    private Integer haveSpecial;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)")
    private Integer specialUrlType;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

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
