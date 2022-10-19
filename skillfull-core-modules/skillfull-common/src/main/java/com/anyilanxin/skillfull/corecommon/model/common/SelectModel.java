// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 下拉组件model
 *
 * @author zxiaozhou
 * @date 2022-08-18 16:55
 * @since JDK11
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
