// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collections;
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
public class ApprovalInfoModel implements Serializable {
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(name = "approvalStatus", title = "审批状态,具体与TaskStatus一致")
    private Integer approvalStatus;

    @Schema(name = "commentsStr", title = "审批已经")
    private String commentsStr;

    @Schema(name = "approvalOpinions", title = "审批意见")
    @Builder.Default
    private List<TaskOptionInfoModel> approvalOpinions = Collections.emptyList();

    @Schema(name = "attachmentInfos", title = "审批附件信息")
    @Builder.Default
    private List<AttachmentModel> attachmentInfos = Collections.emptyList();


}
