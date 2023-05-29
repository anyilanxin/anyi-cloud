

package com.anyilanxin.anyicloud.system.modules.common.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典配置项表分页查询Request
 *
 * @author zxh
 * @date 2020-11-02 09:25:25
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class CommonDictItemPageVo extends BasePageVo {
    private static final long serialVersionUID = 853710730536860187L;

    @Schema(name = "keyword", title = "字典项名称、值，字典编码、名称")
    private String keyword;

    @Schema(name = "itemStatus", title = "字典项状态：1启用，0不启用，默认0")
    private Integer itemStatus;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;
}
