package com.anyilanxin.skillfull.process.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.process.core.base.controller.vo.CamundaNoDateBasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户组信息
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class GroupQueryPageVoCamunda extends CamundaNoDateBasePageVo {
    private static final long serialVersionUID = 9119670587651253109L;

    @Schema(name = "name", title = "用户组名称")
    protected String name;

    @Schema(name = "tenantId", title = "租户id")
    private String tenantId;
}
