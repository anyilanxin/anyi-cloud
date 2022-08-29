// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由断言查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageRoutePredicateDto implements Serializable {
    private static final long serialVersionUID = 929216992206459132L;

    @Schema(name = "predicateId", title = "断言id")
    private String predicateId;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "routeId", title = "路由id")
    private String routeId;

    @Schema(name = "predicateType", title = "断言类型")
    private String predicateType;

    @Schema(name = "predicateTypeName", title = "断言类型名称")
    private String predicateTypeName;

    @Schema(name = "rules", title = "断言规则:Map")
    private Map<String, String> rules;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "predicateName", title = "断言名称")
    private String predicateName;

}
