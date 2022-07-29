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

import com.anyilanxin.skillfull.corecommon.base.model.stream.SubmitFormModel;
import com.anyilanxin.skillfull.processapi.constant.impl.CallbackType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
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
public class FormSubmitProcessModel implements Serializable {
    private static final long serialVersionUID = 3926458899033457164L;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "processDefinitionId", title = "流程定义id(与流程定义key必传一个,两个都传认流程定义id),即指定版本")
    private String processDefinitionId;

    @Schema(name = "processDefinitionKey", title = "流程定义key(与流程定义id必传一个,两个都传认流程定义id),运行最新版本")
    private String processDefinitionKey;

    @Schema(name = "requireCallback", title = "是否需要回调，true-需要,false-不需要")
    private boolean requireCallback;

    @Schema(name = "callbackType", title = "回调方式")
    private CallbackType callbackType;

    @Schema(name = "callbackInfo", title = "回调信息,具体依据回调方式提供对应的回调参数")
    private Object callbackInfo;

    @Schema(name = "processVariables", title = "流程实例变量数据")
    private Map<String, Object> processVariables;

    @Schema(name = "attachmentInfo", title = "附件信息")
    @Valid
    private AttachmentInfoModel attachmentInfo;

    @Schema(name = "startFormData", title = "流程开始表单数据")
    private Map<String, SubmitFormModel> startFormData;
}
