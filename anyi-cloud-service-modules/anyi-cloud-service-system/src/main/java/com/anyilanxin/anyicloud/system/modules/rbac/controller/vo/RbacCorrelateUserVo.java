

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户-关联关系表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacCorrelateUserVo implements Serializable {
    private static final long serialVersionUID = -49011627519158475L;

    @Schema(name = "correlateId", title = "关联id", required = true)
    @NotBlankOrNull(message = "关联id不能为空")
    private String correlateId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构,2-职位,3-用户组", required = true)
    @NotBlankOrNull(message = "关联类型：1-组织机构,2-职位,3-用户组不能为空")
    private Integer correlateType;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlankOrNull(message = "用户id不能为空")
    private String userId;
}
