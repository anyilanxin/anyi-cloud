

package com.anyilanxin.anyicloud.process.core.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * camunda分页基类
 *
 * @author zxh
 * @date 2021-11-07 14:48
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CamundaNoDateBasePageVo implements Serializable {
    private static final long serialVersionUID = 889985578802106850L;

    @Schema(name = "current", title = "当前页", example = "1")
    @Builder.Default
    private int current = 0;

    @Schema(name = "size", title = "每页条数", example = "10")
    @Builder.Default
    private int size = 10;

    @Schema(name = "descs", title = "降序字段列表")
    private Set<String> descs;

    @Schema(name = "ascs", title = "升序字段列表")
    private Set<String> ascs;

    public int getCurrent() {
        return current == 0 ? current : current - 1;
    }
}
