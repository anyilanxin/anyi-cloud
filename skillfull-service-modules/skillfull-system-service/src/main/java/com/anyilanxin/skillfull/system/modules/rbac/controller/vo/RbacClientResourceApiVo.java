package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 客户端-资源表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacClientResourceApiVo implements Serializable {
    private static final long serialVersionUID = 190015326129393260L;

    @Schema(name = "clientDetailId", title = "客户端信息id", required = true)
    @NotBlankOrNull(message = "客户端信息id不能为空")
    private String clientDetailId;

    @Schema(name = "apiId", title = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值", required = true)
    @NotBlankOrNull(message = "资源接口id,资源id+请求地址+允许请求方法(排序后的)，md5值不能为空")
    private String apiId;

}
