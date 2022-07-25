// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processapi.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotInEnum;
import com.anyilanxin.skillfull.processapi.constant.impl.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubmitApprovalModel implements Serializable {
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(name = "approvalStatus", title = "审批状态:12-打回,20-不同意,21-同意,30-拒绝，具体与TaskStatus一致", required = true, example = "21")
    @NotNull(message = "审批状态不能为空")
    @NotInEnum(enumClass = TaskStatus.class, message = "审批状态只能为:12-打回,20-不同意,21-同意,30-拒绝")
    private Integer approvalStatus;

    @Schema(name = "status", title = "审批状态", hidden = true)
    @JsonIgnore
    @JSONField(serialize = false)
    private TaskStatus status;

    @Schema(name = "approvalOpinion", title = "审批意见")
    private String approvalOpinion;

    @Schema(name = "attachmentInfos", title = "审批附件信息")
    private List<AttachmentModel> attachmentInfos;

    public TaskStatus getStatus() {
        return TaskStatus.getByValue(this.approvalStatus);
    }
}
