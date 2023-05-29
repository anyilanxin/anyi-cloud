

package com.anyilanxin.anyicloud.process.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.process.core.base.controller.vo.CamundaNoDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 租户信息
 *
 * @author zxh
 * @date 2021-11-05 17:49
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class TenantQueryPageVoCamunda extends CamundaNoDateBasePageVo {
    private static final long serialVersionUID = -4949394561962888487L;

    @Schema(name = "name", title = "租户名称")
    protected String name;
}
