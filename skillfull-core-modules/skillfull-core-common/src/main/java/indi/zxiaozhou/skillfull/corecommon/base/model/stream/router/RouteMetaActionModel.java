package indi.zxiaozhou.skillfull.corecommon.base.model.stream.router;

import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 后端路由按钮指令
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
public class RouteMetaActionModel implements Serializable {
    private static final long serialVersionUID = -2028750138891938444L;

    @Schema(name = "routeActions", title = "路由后端按钮权限指令,path:ActionModel")
    private Map<String, Set<ActionModel>> routeActions;


}
