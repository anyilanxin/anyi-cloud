

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 职位表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacPositionVo implements Serializable {
    private static final long serialVersionUID = 202984047701766420L;

    @Schema(name = "positionName", title = "职位名称", required = true)
    @NotBlankOrNull(message = "职位名称不能为空")
    private String positionName;

    @Schema(name = "positionCode", title = "职位编码", required = true)
    @NotBlankOrNull(message = "职位编码不能为空")
    private String positionCode;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;

    @Schema(name = "positionRank", title = "职级", required = true)
    @NotBlankOrNull(message = "职级不能为空")
    private Integer positionRank;

    @Schema(name = "positionStatus", title = "职位状态：0-无效，1-有效，默认0", required = true)
    @NotBlankOrNull(message = "职位状态：0-无效，1-有效，默认0不能为空")
    private Integer positionStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
