// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 后端按钮权限
 *
 * @author zxiaozhou
 * @date 2021-06-04 00:17
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Schema
public class ActionModel implements Serializable {
    private static final long serialVersionUID = -3148648474688359998L;

    @Schema(name = "actionSet", title = "指令")
    private Set<String> actionSet;

    @Schema(name = "systemId", title = "系统id")
    private String systemId;

    @Schema(name = "actionMethodSet", title = "按钮请求方法")
    private Set<String> actionMethodSet;

    @Schema(name = "actionLimitMethod", title = "按钮限制请求方法:0-不限制,1-限制，实际boolean型")
    private boolean actionLimitMethod;

    @Schema(name = "serviceId", title = "所属服务id")
    private String serviceId;

    @Schema(name = "describe", title = "描述")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String describe;

    @Schema(name = "path", title = "后端地址")
    private String path;

    @Schema(name = "checkStrategy", title = "检验策略")
    private String checkStrategy;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionModel)) {
            return false;
        }
        ActionModel that = (ActionModel) o;
        return Objects.equals(getActionMethodSet(), that.getActionMethodSet()) && Objects.equals(getServiceId(), that.getServiceId()) && Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActionMethodSet(), getServiceId(), getPath());
    }
}

