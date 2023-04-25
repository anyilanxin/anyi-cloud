package com.anyilanxin.skillfull.systemrpc.feign.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
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
