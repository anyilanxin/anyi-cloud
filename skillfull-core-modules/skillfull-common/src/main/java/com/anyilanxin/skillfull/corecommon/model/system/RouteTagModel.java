// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 路由meta
 *
 * @author zxiaozhou
 * @date 2021-06-04 00:17
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Schema
public class RouteTagModel implements Serializable {
    private static final long serialVersionUID = -3148648474688359998L;

    @Schema(name = "type", title = "类型：primary、error、warn、success")
    private String type;

    @Schema(name = "content", title = "内容")
    private String content;

    @Schema(name = "dot", title = "是否圆点")
    private Boolean dot;
}

