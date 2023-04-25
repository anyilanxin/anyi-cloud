package com.anyilanxin.skillfull.process.core.base.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * camunda分页基类
 *
 * @author zxiaozhou
 * @date 2021-11-07 14:48
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CamundaDateBasePageVo implements Serializable {
    private static final long serialVersionUID = 889985578802106850L;

    @Schema(name = "startTime", title = "开始时间", type = "string", example = "2020-11-12 11:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(name = "endTime", title = "结束时间", type = "string", example = "2020-11-12 11:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

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
