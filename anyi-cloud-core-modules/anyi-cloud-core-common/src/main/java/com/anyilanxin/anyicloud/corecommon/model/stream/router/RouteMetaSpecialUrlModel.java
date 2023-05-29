

package com.anyilanxin.anyicloud.corecommon.model.stream.router;

import com.anyilanxin.anyicloud.corecommon.model.system.SpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * 后端路由特殊url
 *
 * @author zxh
 * @date 2021-07-11 20:30
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RouteMetaSpecialUrlModel implements Serializable {
    private static final long serialVersionUID = -2028750138891938444L;

    @Schema(name = "whiteSpecialUrls", title = "白名单特殊url")
    private List<SpecialUrlModel> whiteSpecialUrls;

    @Schema(name = "blackSpecialUrls", title = "黑名单特殊url")
    private List<SpecialUrlModel> blackSpecialUrls;
}
