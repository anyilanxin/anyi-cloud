package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 数据字典表分页查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:18
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class CommonDictPageVo extends BasePageVo {
    private static final long serialVersionUID = 155865653502537066L;

    @Schema(name = "keyword", title = "字典名称或编码")
    private String keyword;

    @Schema(name = "dictStatus", title = "字典状态：1启用，0禁用，默认0")
    private Integer dictStatus;

    @Schema(name = "dictType", title = "字典类型：0-字符串,1-数字,2-布尔。默认0")
    private Integer dictType;
}
