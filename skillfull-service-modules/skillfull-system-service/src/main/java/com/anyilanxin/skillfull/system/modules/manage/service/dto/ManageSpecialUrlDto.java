package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由特殊地址查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 09:34:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageSpecialUrlDto implements Serializable {
    private static final long serialVersionUID = -11097092569384435L;

    @Schema(name = "specialUrlId", title = "特殊过滤器id")
    private String specialUrlId;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;

    @Schema(name = "urlName", title = "接口名称")
    private String urlName;

    @Schema(name = "urlDescribe", title = "接口描述")
    private String urlDescribe;

    @Schema(name = "urlType", title = "地址类型:0-系统,1-自定义,默认0")
    private Integer urlType;

    @Schema(name = "specialStatus", title = "特殊地址状态:0-禁用,1-启用，默认0")
    private Integer specialStatus;

    @Schema(name = "limitMethod", title = "限制请求方法：0-不限制,1-限制")
    private Integer limitMethod;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)")
    private Integer specialUrlType;

    @Schema(name = "requestMethod", title = "允许的请求方法,多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "url", title = "地址,服务地址或http地址")
    private String url;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
