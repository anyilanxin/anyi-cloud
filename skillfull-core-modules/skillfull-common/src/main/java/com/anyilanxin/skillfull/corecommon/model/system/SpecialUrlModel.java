package com.anyilanxin.skillfull.corecommon.model.system;

/**
 * @author zxiaozhou
 * @date 2021-07-09 13:01
 * @since JDK1.8
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * 特殊url信息
 *
 * @author zxiaozhou
 * @date 2021-05-06 12:56
 * @since JDK1.8
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SpecialUrlModel implements Serializable {
    private static final long serialVersionUID = -5373929396939925329L;

    @Schema(name = "urlType", title = "地址业务类型,与关联业务所属业务类型")
    private String urlType;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)")
    private Integer specialUrlType;

    @Schema(name = "url", title = "url")
    private String url;

    @Schema(name = "requestMethodSet", title = "请求方法")
    private Set<String> requestMethodSet;

    @Schema(name = "requestMethod", title = "1-限制请求方法,0-不限制")
    private Integer limitMethod;
}
