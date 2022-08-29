// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 资源api表分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-03 00:29:07
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacResourceApiPageVo extends BasePageVo {
    private static final long serialVersionUID = -94994231532318825L;

    @Schema(name = "resourceId", title = "资源id")
    private String resourceId;

    @Schema(name = "requestMethod", title = "请求类型")
    private String requestMethod;

    @Schema(name = "apiUriAll", title = "接口全地址,request_prefix+api_uri")
    private String apiUriAll;

    @Schema(name = "apiName", title = "接口名称")
    private String apiName;

    @Schema(name = "apiTagName", title = "api所属分组名称")
    private String apiTagName;

    @Schema(name = "apiVersions", title = "api版本,多个英文逗号隔开")
    private String apiVersions;

    @Schema(name = "requireAuth", title = "是否鉴权,0-不需要,1-需要。默认1")
    private Integer requireAuth;

    @Schema(name = "authType", title = "鉴权类型,0-不限制,1-非角色鉴权。默认1")
    private Integer authType;

}
