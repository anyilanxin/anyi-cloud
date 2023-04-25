package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 授权客户端信息分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacClientDetailsPageVo extends BasePageVo {
    private static final long serialVersionUID = -89155631261196641L;

    @Schema(name = "clientId", title = "客户端id")
    private String clientId;

    @Schema(name = "clientName", title = "客户端名称")
    private String clientName;

    @Schema(name = "clientStatus", title = "状态：0-未启用,1-启用，2-锁定，默认0")
    private Integer clientStatus;

}
