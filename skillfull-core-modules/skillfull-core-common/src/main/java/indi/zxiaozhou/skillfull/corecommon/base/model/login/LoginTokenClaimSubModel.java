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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * token sub信息
 *
 * @author zxiaozhou
 * @date 2020-10-20 16:36
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class LoginTokenClaimSubModel implements Serializable {
    private static final long serialVersionUID = 6925442851885369086L;
    /**
     * 登录编号
     */
    private String loginCode;

    /**
     * 登录端(与LoginEndpointType一致)
     */
    private String loginEndpoint;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 租户id
     */
    private String tenantId;
}
