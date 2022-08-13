package com.anyilanxin.skillfull.corecommon.model.stream.router;

import com.anyilanxin.skillfull.corecommon.model.system.SpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 后端路由特殊url
 *
 * @author zxiaozhou
 * @date 2021-07-11 20:30
 * @since JDK1.8
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
