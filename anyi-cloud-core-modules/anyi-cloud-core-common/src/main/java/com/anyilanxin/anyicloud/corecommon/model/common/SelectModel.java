

package com.anyilanxin.anyicloud.corecommon.model.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 下拉组件model
 *
 * @author zxh
 * @date 2022-08-18 16:55
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SelectModel implements Serializable {
    private static final long serialVersionUID = 1660812955774L;

    @Schema(name = "value", title = "value值")
    private String value;

    @Schema(name = "label", title = "label数据")
    private String label;

    @Schema(name = "extendInfo", title = "扩展信息")
    private Object extendInfo;

    @Schema(name = "disabled", title = "是否禁用")
    private boolean disabled;
}
