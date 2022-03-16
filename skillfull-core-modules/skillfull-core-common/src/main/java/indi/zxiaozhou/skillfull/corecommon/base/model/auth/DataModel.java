// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 数据权限信息
 *
 * @author zxiaozhou
 * @date 2021-06-04 00:18
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
public class DataModel implements Serializable {
    private static final long serialVersionUID = -2447072397405849742L;

    @Schema(name = "userName", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataModel)) {
            return false;
        }
        DataModel agentInfo = (DataModel) o;
        return Objects.equals(getUserId(), agentInfo.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
