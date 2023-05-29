

package com.anyilanxin.anyicloud.process.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 历史变量信息
 *
 * @author zxh
 * @date 2021-07-30 21:51
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class HistoricVariableDto implements Serializable {
    private static final long serialVersionUID = -796467855703929445L;

    @Schema(name = "variableState", title = "变量状态:0-删除,1-创建")
    protected Integer variableState;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    @Schema(name = "removalTime", title = "删除时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime removalTime;

    @Schema(name = "name", title = "变量名称")
    private String name;

    @Schema(name = "value", title = "变量值")
    private String value;

    @Schema(name = "id", title = "变量id")
    private String id;
}
