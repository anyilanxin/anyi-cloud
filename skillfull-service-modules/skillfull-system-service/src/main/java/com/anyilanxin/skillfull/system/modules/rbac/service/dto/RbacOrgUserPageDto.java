package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构-用户分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class RbacOrgUserPageDto implements Serializable {
    private static final long serialVersionUID = 645843230345528796L;

    @Schema(name = "orgUserId", title = "机构用户id")
    private String orgUserId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

}
