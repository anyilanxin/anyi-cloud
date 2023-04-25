package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 服务管理分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:19
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageServicePageVo extends BasePageVo {
    private static final long serialVersionUID = 799708842262970829L;

    @Schema(name = "serviceCode", title = "服务编码")
    private String serviceCode;

    @Schema(name = "serviceName", title = "服务名")
    private String serviceName;

    @Schema(name = "serviceState", title = "服务状态:0-禁用,1-启用。默认0")
    private Integer serviceState;
}
