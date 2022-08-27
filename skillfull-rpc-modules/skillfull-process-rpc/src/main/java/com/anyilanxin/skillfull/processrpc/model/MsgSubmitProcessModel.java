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

import com.anyilanxin.skillfull.corecommon.model.stream.SubmitFormModel;
import com.anyilanxin.skillfull.processrpc.constant.impl.CallbackType;
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
public class MsgSubmitProcessModel implements Serializable {
    private static final long serialVersionUID = 3926458899033457164L;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "messageName", title = "流程消息name", required = true)
    @NotBlank(message = "流程消息name不能为空")
    private String messageName;

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
