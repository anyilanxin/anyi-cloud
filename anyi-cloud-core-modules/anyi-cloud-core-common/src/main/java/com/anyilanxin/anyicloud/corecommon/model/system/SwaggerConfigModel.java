

package com.anyilanxin.anyicloud.corecommon.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由查询Response
 *
 * @author zxh
 * @date 2020-09-12 16:04:47
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class SwaggerConfigModel implements Serializable {
    private static final long serialVersionUID = 550568918804547444L;

    @Schema(name = "configUrl", title = "url配置信息")
    private String configUrl;

    @Schema(name = "urls", title = "url信息")
    private List<UrlInfo> urls;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @Schema
    public static class UrlInfo {
        @Schema(name = "url", title = "doc文件地址")
        private String url;

        @Schema(name = "name", title = "分组名称")
        private String name;
    }
}
