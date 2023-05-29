

package com.anyilanxin.anyicloud.corecommon.model.stream.router;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 动态路由信息
 *
 * @author zxh
 * @date 2020-09-12 16:04:47
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SystemRouterListModel implements Serializable {
    private static final long serialVersionUID = 550568918804547444L;

    @Schema(name = "routerInfoModels", title = "所有有效的路由信息")
    private List<SystemRouterModel> routerInfoModels;
}
