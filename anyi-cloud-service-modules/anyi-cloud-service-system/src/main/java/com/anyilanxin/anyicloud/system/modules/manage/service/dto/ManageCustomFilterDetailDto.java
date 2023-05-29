

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 自定义过滤器查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:14
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageCustomFilterDetailDto extends ManageCustomFilterSimpleDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "specialUrls", title = "特殊url")
    private List<ManageSpecialUrlDto> specialUrls;
}
