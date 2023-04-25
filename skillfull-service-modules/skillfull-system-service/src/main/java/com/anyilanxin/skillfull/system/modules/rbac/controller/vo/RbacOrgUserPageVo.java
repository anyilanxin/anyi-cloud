package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构-用户分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgUserPageVo extends BasePageVo {
    private static final long serialVersionUID = -47653047735389614L;

    @Schema(name = "orgUserId", title = "机构用户id")
    private String orgUserId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

}
