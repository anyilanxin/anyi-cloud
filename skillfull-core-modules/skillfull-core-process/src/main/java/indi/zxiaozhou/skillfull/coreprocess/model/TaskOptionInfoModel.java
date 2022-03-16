package indi.zxiaozhou.skillfull.coreprocess.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zxiaozhou
 * @date 2021-12-08 18:59
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskOptionInfoModel implements Serializable {
    private static final long serialVersionUID = 3859688714134530556L;

    @Schema(name = "comment", title = "审批意见")
    protected String comment;

    @Schema(name = "submitTime", title = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date submitTime;
}
