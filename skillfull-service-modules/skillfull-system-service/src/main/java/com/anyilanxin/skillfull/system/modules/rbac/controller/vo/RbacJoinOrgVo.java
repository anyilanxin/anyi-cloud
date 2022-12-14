package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * 加入机构信息
 *
 * @author zxiaozhou
 * @date 2022-08-02 16:53
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacJoinOrgVo implements Serializable {
    private static final long serialVersionUID = 1659430499110L;

    @Schema(name = "userIds", title = "用户ids", required = true)
    @NotNull(message = "用户ids不能为空")
    @Size(min = 1, message = "用户ids不能为空")
    private Set<String> userIds;

    @Schema(name = "orgId", title = "组织id", required = true)
    @NotBlank(message = "组织id不能为空")
    private String orgId;
}
