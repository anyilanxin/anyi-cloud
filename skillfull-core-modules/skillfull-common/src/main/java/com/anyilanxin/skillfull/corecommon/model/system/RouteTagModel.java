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

