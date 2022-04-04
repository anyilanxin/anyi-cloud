// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 21:08
 * @since JDK1.8
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
public class NacosNamespacesDto implements Serializable {

    private static final long serialVersionUID = -625547796053966768L;

    @Schema(name = "namespace", title = "命名空间ID")
    private String namespace;

    @Schema(name = "namespaceShowName", title = "命名空间名称")
    private String namespaceShowName;

    @Schema(name = "quota", title = "quota")
    private Integer quota;

    @Schema(name = "configCount", title = "配置数")
    private Integer configCount;

    @Schema(name = "type", title = "type")
    private Integer type;
}
