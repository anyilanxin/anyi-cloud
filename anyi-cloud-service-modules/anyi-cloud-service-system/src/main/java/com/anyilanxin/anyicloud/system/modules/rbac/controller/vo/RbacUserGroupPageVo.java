

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户组分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacUserGroupPageVo extends BasePageVo {
    private static final long serialVersionUID = -18259803229216430L;

    @Schema(name = "keyword", title = "用户组名称或编码")
    private String keyword;

    @Schema(name = "groupStatus", title = "用户组状态:0-禁用,1-启用。默认0")
    private Integer groupStatus;
}
