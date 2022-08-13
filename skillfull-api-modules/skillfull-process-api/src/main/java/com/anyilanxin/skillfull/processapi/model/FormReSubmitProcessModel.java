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

import com.anyilanxin.skillfull.corecommon.model.stream.SubmitFormModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * 创建流程实例
 *
 * @author zxiaozhou
 * @date 2020-10-19 16:58
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FormReSubmitProcessModel implements Serializable {
    private static final long serialVersionUID = 3926458899033457164L;

    @Schema(name = "processInstanceId", title = "流程实例id", required = true)
    @NotBlank(message = "流程实例id不能为空")
    private String processInstanceId;


    @Schema(name = "attachmentInfo", title = "附件信息")
    @Valid
    private AttachmentInfoModel attachmentInfo;

    @Schema(name = "startFormData", title = "流程开始表单数据")
    private Map<String, SubmitFormModel> startFormData;
}
