

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色权限添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgRoleAuthVo implements Serializable {
    private static final long serialVersionUID = 963678539662691043L;

    @Schema(name = "menuIds", title = "菜单列表")
    private List<String> menuIds;

    @Schema(name = "apiIds", title = "资源api列表(只有非角色权限指令才有必要授权)")
    private List<String> apiIds;

    @Schema(name = "dataAuthType", title = "数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,8-仅自己")
    private Integer dataAuthType;

    @Schema(name = "customDataAuthData", title = "自定义类数据权限数据")
    private Set<String> customDataAuthData;
}
