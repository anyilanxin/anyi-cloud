

package com.anyilanxin.anyicloud.system.modules.common.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域表分页查询Request
 *
 * @author zxh
 * @date 2020-11-02 09:25:04
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class CommonAreaPageVo extends BasePageVo {
    private static final long serialVersionUID = -74388906185871378L;

    @Schema(name = "areaName", title = "区域名称")
    private String areaName;

    @Schema(name = "zipCode", title = "邮编")
    private String zipCode;

    @Schema(name = "areaId", title = "区域编码")
    private String areaId;
}
