

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构-资源表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgResourceApiVo implements Serializable {
    private static final long serialVersionUID = -79026674985523671L;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlankOrNull(message = "机构id不能为空")
    private String orgId;

    @Schema(name = "apiId", title = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值", required = true)
    @NotBlankOrNull(message = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值不能为空")
    private String apiId;
}
