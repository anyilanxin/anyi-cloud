// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 系统表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:32
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonSystemVo implements Serializable {
    private static final long serialVersionUID = -92865521492838874L;

    @Schema(name = "serviceName", title = "服务名称", required = true)
    @NotBlankOrNull(message = "服务名称不能为空")
    private String serviceName;

    @Schema(name = "systemCode", title = "系统编码", required = true)
    @NotBlankOrNull(message = "系统编码不能为空")
    private String serviceCode;

    @Schema(name = "remark", title = "备注")
    private String remark;
}