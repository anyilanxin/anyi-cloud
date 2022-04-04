// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 数据映射表(CommonDataMap)Entity
 *
 * @author zxiaozhou
 * @date 2021-07-28 11:53:28
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
@TableName("auth_common_data_map")
public class CommonDataMapEntity extends BaseEntity {
    private static final long serialVersionUID = -80892851790826730L;

    @TableId
    private String dataMapId;

    @Schema(name = "mapOriginalType", title = "映射原类型:1-系统数据，2-区域数据，3-组织数据，4-用户组数据，5-个人数据")
    private Integer mapOriginalType;

    @Schema(name = "originalId", title = "映射原类型id")
    private String originalId;

    @Schema(name = "targetMapType", title = "映射目标类型:1-系统数据，2-区域数据，3-组织数据，4-用户组数据，5-个人数据")
    private Integer targetMapType;

    @Schema(name = "targetId", title = "映射目标类型id")
    private String targetId;

    @Schema(name = "operationType", title = "数据操作类型:1.公有话，2.私有化，3.目标映射")
    private Integer operationType;

    @Schema(name = "permissionDataRuleMutex", title = "权限添置规则互斥：0.取消填制规则,1. 按原按钮填值规则，默认1")
    private Integer permissionDataRuleMutex;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;
}
