

package com.anyilanxin.anyicloud.systemrpc.feign.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户组信息
 *
 * @author zxh
 * @date 2021-11-05 17:49
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class GroupTenantVo implements Serializable {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "groupId", title = "用户组id", required = true)
    @NotBlank(message = "用户组id不能为空")
    protected String groupId;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;
}
