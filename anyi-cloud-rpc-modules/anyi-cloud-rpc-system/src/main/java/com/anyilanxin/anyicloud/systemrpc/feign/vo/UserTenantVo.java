

package com.anyilanxin.anyicloud.systemrpc.feign.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息
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
public class UserTenantVo implements Serializable {
    private static final long serialVersionUID = 6364921052776119371L;

    @Schema(name = "tenantIds", title = "租户ids")
    protected Set<String> tenantIds;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    private String userId;
}
