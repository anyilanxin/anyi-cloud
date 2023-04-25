package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.List;

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
public class RbacClientResourceApiPageVo extends BasePageVo {
    private static final long serialVersionUID = -94994231532318825L;

    @Schema(name = "resourceId", title = "资源id")
    private String resourceId;

    @Schema(name = "apiUriAll", title = "接口全地址,request_prefix+api_uri")
    private String apiUriAll;

    @Schema(name = "clientDetailId", title = "客户端信息id", required = true)
    @NotBlank(message = "客户端信息id不能为空")
    private String clientDetailId;

    @Schema(name = "resourceIds", title = "现在的资源列表", hidden = true)
    private List<String> resourceIds;

    @Schema(name = "apiTagName", title = "api所属分组名称")
    private String apiTagName;
}
