

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构角色表查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgRoleDto implements Serializable {
    private static final long serialVersionUID = -94277471588927143L;

    @Schema(name = "orgRoleId", title = "机构角色id")
    private String orgRoleId;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "dataAuthType", title = "数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,6-仅自己")
    private Integer dataAuthType;

    @Schema(name = "customDataAuthData", title = "自定义类角色数据权限,权限ids json array")
    private Set<String> customDataAuthData;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0，挂接机构时自动挂接")
    private Integer autoBind;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0")
    private Integer roleStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

    @Schema(name = "menuIds", title = "菜单列表")
    private List<String> menuIds;
}
