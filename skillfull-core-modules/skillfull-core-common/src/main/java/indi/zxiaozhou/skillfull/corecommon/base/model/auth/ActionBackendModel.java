package indi.zxiaozhou.skillfull.corecommon.base.model.auth;

import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 用户登录路由信息
 *
 * @author zxiaozhou
 * @date 2021-07-11 20:23
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ActionBackendModel implements Serializable {
    private static final long serialVersionUID = 6772054685375558007L;

    @Schema(name = "userActions", title = "后端按钮权限指令,serviceId:path:ActionModel")
    Map<String, Map<String, Set<ActionModel>>> userActions;
}
