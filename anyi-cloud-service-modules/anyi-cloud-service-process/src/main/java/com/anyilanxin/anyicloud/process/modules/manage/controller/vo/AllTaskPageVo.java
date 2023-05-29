

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 获取我的代办
 *
 * @author zxh
 * @date 2020-10-19 19:37
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AllTaskPageVo extends BasePageVo implements Serializable {
    private static final long serialVersionUID = 2741579373373551412L;

    @Schema(name = "taskName", title = "任务名称")
    private String taskName;

    @Schema(name = "assignee", title = "处理人")
    private String assignee;

    @Schema(name = "roleCode", title = "处理角色")
    private String roleCode;

    @Schema(name = "candidateUser", title = "处理候选人")
    private String candidateUser;

    @Schema(name = "type", title = "任务类型:")
    private Integer type;
}
