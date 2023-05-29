

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 资源api表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-03 00:29:07
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgResourceApiPageVo extends BasePageVo {
    private static final long serialVersionUID = -94994231532318825L;

    @Schema(name = "resourceId", title = "资源id")
    private String resourceId;

    @Schema(name = "apiUriAll", title = "接口全地址,request_prefix+api_uri")
    private String apiUriAll;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlank(message = "机构id不能为空")
    private String orgId;

    @Schema(name = "apiTagName", title = "api所属分组名称")
    private String apiTagName;
}
