// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coreprocess.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotInEnum;
import indi.zxiaozhou.skillfull.coreprocess.constant.impl.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 审批信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 12:58
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
public class SubmitApprovalModel implements Serializable {
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(name = "approvalStatus", title = "审批状态:30-拒绝，40-同意, 50-驳回，具体与TaskStatus一致", required = true)
    @NotNull(message = "审批状态不能为空")
    @NotInEnum(enumClass = TaskStatus.class, message = "审批状态只能为30、40、50")
    private Integer approvalStatus;

    @Schema(name = "status", title = "审批已经", hidden = true)
    @JsonIgnore
    @JSONField(serialize = false)
    private TaskStatus status;

    @Schema(name = "approvalOpinion", title = "审批意见")
    private String approvalOpinion;

    @Schema(name = "attachmentInfos", title = "审批附件信息")
    private List<AttachmentInfoModel> attachmentInfos;

    public TaskStatus getStatus() {
        return TaskStatus.getByValue(this.approvalStatus);
    }
}
