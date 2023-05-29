

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Map;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由断言添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:42
 * @since 1.0.0
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
