// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.base.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

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