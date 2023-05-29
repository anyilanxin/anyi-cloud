

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 资源表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacResourcePageVo extends BasePageVo {
    private static final long serialVersionUID = -57633920667714595L;

    @Schema(name = "resourceCode", title = "资源编码,即后端服务名")
    private String resourceCode;

    @Schema(name = "resourceName", title = "资源名称")
    private String resourceName;

    @Schema(name = "resourceStatus", title = "状态：0-未启用,1-启用，默认0")
    private Integer resourceStatus;

    @Schema(name = "resourceType", title = "资源类型：1-内部服务,2-外部资源")
    private Integer resourceType;
}
