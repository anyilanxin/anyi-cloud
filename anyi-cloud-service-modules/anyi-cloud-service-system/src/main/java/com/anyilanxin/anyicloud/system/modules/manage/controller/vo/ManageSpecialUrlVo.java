

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由特殊地址添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 09:34:50
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class ManageSpecialUrlVo implements Serializable {
    private static final long serialVersionUID = -43635735021401802L;

    @Schema(name = "urlName", title = "接口名称", required = true)
    @NotBlankOrNull(message = "接口名称不能为空")
    private String urlName;

    @Schema(name = "urlDescribe", title = "接口描述")
    private String urlDescribe;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)", required = true)
    @NotBlankOrNull(message = "特殊url类型不能为空")
    private Integer specialUrlType;

    @Schema(name = "urlType", title = "地址类型:0-系统,1-自定义,默认0")
    private Integer urlType;

    @Schema(name = "specialStatus", title = "特殊地址状态:0-禁用,1-启用，默认0", required = true)
    @NotBlankOrNull(message = "特殊地址状态不能为空")
    private Integer specialStatus;

    @Schema(name = "limitMethod", title = "限制请求方法：0-不限制,1-限制", required = true)
    @NotBlankOrNull(message = "限制请求方法不能为空")
    private Integer limitMethod;

    @Schema(name = "requestMethod", title = "允许的请求方法,多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "url", title = "地址,服务地址或http地址", required = true)
    @NotBlankOrNull(message = "地址,服务地址或http地址不能为空")
    private String url;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
