// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流程act以及模型信息
 *
 * @author zxiaozhou
 * @date 2022-01-07 07:25
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ActInstanceInfoModel implements Serializable {
    private static final long serialVersionUID = -3317408065181369306L;

    @Schema(name = "diagramData", title = "bpmn blob文件(base64编码)")
    private String diagramData;

    @Schema(name = "diagramName", title = "当前实例模型名称")
    private String diagramName;

    @Schema(name = "processDefinitionKey", title = "当前实例流程定义key")
    private String processDefinitionKey;

    @Schema(name = "processDefinitionId", title = "当前实例流动定义id")
    private String processDefinitionId;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "deleteReason", title = "删除原因,当外部终止时使用，completed-完成，invalid-作废,refused-拒绝")
    protected String deleteReason;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "deploymentName", title = "当前实例流程部署名称")
    private String deploymentName;

    @Schema(name = "version", title = "当前实例模型版本")
    private Integer version;

    @Schema(name = "startTitle", title = "申请标题")
    private String startTitle;

    @Schema(name = "category", title = "当前模型类型")
    private String category;

    @Schema(name = "categoryName", title = "当前模型类型名称")
    private String categoryName;

    @Schema(name = "durationInMillis", title = "消耗时间")
    private Long durationInMillis;

    @Schema(name = "durationInMillisFormat", title = "消耗时间")
    private String durationInMillisFormat;

    @Schema(name = "processStartTime", title = "流程发起时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processStartTime;

    @Schema(name = "processEndTime", title = "流程结束时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processEndTime;

    @Schema(name = "finishState", title = "完成状态:0-未完成,1-完成")
    private Integer finishState;

    @Schema(name = "processState", title = "1-活动,2-挂起,3-完成,4-拒绝(特权，直接取消流程),5-作废,6-外部终止,7-内部终止,具体参考ProcessInstanceState")
    private Integer processState;

    @Schema(name = "needResubmit", title = "是否需要重新提交")
    private boolean needResubmit;

    @Schema(name = "suspended", title = "是否挂起")
    private boolean suspended;

    @Schema(name = "resubmitTaskId", title = "重新提交任务id")
    private String resubmitTaskId;

    @Schema(name = "attachmentInfo", title = "附件信息")
    private AttachmentInfoModel attachmentInfo;

    @Schema(name = "startFormData", title = "表单信息")
    private Map<String, Object> startFormData;

    @Schema(name = "actInstanceList", title = "流程实例信息")
    private List<ActInstanceModel> actInstanceList;
}
