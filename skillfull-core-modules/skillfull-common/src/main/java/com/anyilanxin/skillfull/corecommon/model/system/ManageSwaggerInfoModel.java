// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由查询Response
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:04:47
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageSwaggerInfoModel implements Serializable {
    private static final long serialVersionUID = 550568918804547444L;

    @Schema(name = "swaggerConfigUrl", title = "swagger doc uri,即凭借上网关ip信息就能访问的uri")
    private String swaggerConfigUrl;

    @Schema(name = "serviceCode", title = "服务编码")
    private String serviceCode;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应")
    private Integer isLoadBalancer;
}
