

package com.anyilanxin.anyicloud.process.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程实例删除
 *
 * @author zxh
 * @date 2020-10-20 10:01
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessInstanceHistoryPageVo extends BasePageVo implements Serializable {
    private static final long serialVersionUID = -2625200294039929340L;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "instanceState", title = "实例状态,来源于常量字典:ProcessInstanceState")
    private Integer instanceState;

    @Schema(name = "title", title = "流程实例标题")
    private String title;

    @Schema(name = "finished", title = "是否结束")
    private Boolean finished;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "processDefinitionName", title = "流程定义名称")
    private String processDefinitionName;
}
