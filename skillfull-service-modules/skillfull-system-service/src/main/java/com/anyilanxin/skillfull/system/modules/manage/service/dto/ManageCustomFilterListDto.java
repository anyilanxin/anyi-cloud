// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义过滤器查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:14
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class ManageCustomFilterListDto extends ManageCustomFilterSimpleDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "whiteSpecialUrls", title = "白名单特殊url")
    private List<ManageSpecialUrlDto> whiteSpecialUrls;

    @Schema(name = "blackSpecialUrls", title = "黑名单特殊url")
    private List<ManageSpecialUrlDto> blackSpecialUrls;

}
