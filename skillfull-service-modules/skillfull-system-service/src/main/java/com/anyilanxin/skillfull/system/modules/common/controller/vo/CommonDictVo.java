package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 数据字典表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:17
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonDictVo implements Serializable {
    private static final long serialVersionUID = -18634770323379386L;

    @Schema(name = "dictName", title = "字典名称", required = true)
    @NotBlankOrNull(message = "字典名称不能为空")
    private String dictName;

    @Schema(name = "dictCode", title = "字典编码", required = true)
    @NotBlankOrNull(message = "字典编码不能为空")
    private String dictCode;

    @Schema(name = "dictType", title = "字典类型：0-字符串,1-数字,2-布尔。默认0", required = true)
    @NotBlankOrNull(message = "字典类型：0-字符串,1-数字,2-布尔。默认0不能为空")
    private Integer dictType;

    @Schema(name = "dictStatus", title = "字典状态：1启用，0禁用，默认0")
    private int dictStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
