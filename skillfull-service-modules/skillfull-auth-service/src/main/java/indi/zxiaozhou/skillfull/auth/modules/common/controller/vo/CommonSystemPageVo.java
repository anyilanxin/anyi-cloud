package indi.zxiaozhou.skillfull.auth.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 系统分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-07-28 10:13:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CommonSystemPageVo extends BasePageVo {
    private static final long serialVersionUID = 537687024391232603L;

    @Schema(name = "systemName", title = "系统名称")
    private String systemName;

    @Schema(name = "systemCode", title = "系统编码")
    private String systemCode;
}
