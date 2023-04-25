package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由断言添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class ManageRoutePredicateVo implements Serializable {
    private static final long serialVersionUID = 927432054061925798L;

    @Schema(name = "predicateType", title = "断言类型", required = true)
    @NotBlankOrNull(message = "断言类型不能为空")
    private String predicateType;

    @Schema(name = "predicateName", title = "断言名称", required = true)
    @NotBlankOrNull(message = "断言名称不能为空")
    private String predicateName;

    @Schema(name = "predicateTypeName", title = "断言类型名称", required = true)
    @NotBlankOrNull(message = "断言类型名称不能为空")
    private String predicateTypeName;

    @Schema(name = "rules", title = "断言规则:Map")
    private Map<String, String> rules;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
