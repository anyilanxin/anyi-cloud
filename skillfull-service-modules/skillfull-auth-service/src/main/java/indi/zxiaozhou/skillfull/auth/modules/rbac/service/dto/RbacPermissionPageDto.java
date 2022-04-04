// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.TIME_ZONE_GMT8;

/**
 * 权限表分页查询Response
 *
 * @author zxiaozhou
 * @date 2021-01-14 22:02:26
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class RbacPermissionPageDto extends BaseTree<RbacPermissionPageDto> implements Serializable {
    private static final long serialVersionUID = 162089458999815869L;

    @Schema(name = "permissionId", title = "权限id")
    private String permissionId;

    @Schema(name = "parentId", title = "父id")
    private String parentId;

    @Schema(name = "path", title = "路径")
    private String path;

    @Schema(name = "component", title = "前端组件")
    private String component;

    @Schema(name = "pathName", title = "路由名称")
    private String pathName;

    @Schema(name = "permissionType", title = "权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:PermissionType")
    private Integer permissionType;

    @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
    private Integer actionType;

    @Schema(name = "actions", title = "按钮权限action,多个英文逗号隔开")
    private String actions;

    @Schema(name = "iframe", title = "是否外连接")
    private boolean iframe;

    @Schema(name = "permissionStatus", title = "权限状态:0-禁用,1-启用")
    private Integer permissionStatus;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "icon", title = "图标")
    private String icon;

    @Schema(name = "iconType", title = "图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)")
    private Integer iconType;

    @Schema(name = "orderNo", title = "菜单排序")
    private Integer orderNo;

    @Schema(name = "systemId", title = "所属系统")
    private String systemId;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "permissionSysCode", title = "系统内置编码(系统自动生成)")
    private String permissionSysCode;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}