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
 * @copyright zxiaozhou（https://skillfull.divisu.com）
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
public class ManageCustomFilterDetailDto extends ManageCustomFilterSimpleDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "specialUrls", title = "特殊url")
    private List<ManageSpecialUrlDto> specialUrls;
}
