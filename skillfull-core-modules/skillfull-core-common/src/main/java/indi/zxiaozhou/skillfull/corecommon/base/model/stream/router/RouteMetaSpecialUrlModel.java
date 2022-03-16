package indi.zxiaozhou.skillfull.corecommon.base.model.stream.router;

import indi.zxiaozhou.skillfull.corecommon.base.model.common.SpecialUrlModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

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
@AllArgsConstructor
@NoArgsConstructor
public class RouteMetaSpecialUrlModel implements Serializable {
    private static final long serialVersionUID = -2028750138891938444L;

    @Schema(name = "specialUrl", title = "特殊url")
    private Set<SpecialUrlModel> specialUrl;

    @Schema(name = "specialUrlType", title = "特殊url类型:1-白名单(放行url),2-黑名单(只处理url)")
    private Integer specialUrlType;
}
