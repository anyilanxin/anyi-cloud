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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 附件信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 12:49
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AttachmentInfoModel implements Serializable {
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(name = "remark", title = "附件说明")
    private String remark;


    @Schema(name = "attachments", title = "附件")
    @NotNull(message = "附件不能为空")
    @Size(min = 1, message = "附件不能为空")
    @Valid
    private List<AttachmentModel> attachments;
}
