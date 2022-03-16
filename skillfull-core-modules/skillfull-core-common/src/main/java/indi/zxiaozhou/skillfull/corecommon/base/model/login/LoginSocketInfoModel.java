// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 用户socket链接信息
 *
 * @author zxiaozhou
 * @date 2021-05-29 23:44
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginSocketInfoModel implements Serializable {
    private static final long serialVersionUID = -3529509184883905229L;

    @Schema(name = "loginCode", title = "登录编号")
    private String loginCode;

    @Schema(name = "tenantId", title = "当前租户id")
    private String tenantId;

    @Schema(name = "loginEndpoint(与LoginEndpointType一致)", title = "登录端点")
    private String loginEndpoint;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "socketSessionId", title = "当前链接socket的session")
    private String socketSessionId;

    @Schema(name = "connectionEquipment", title = "链接设备")
    private String connectionEquipment;

    @Schema(name = "connectionTime", title = "链接时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime connectionTime;
}
