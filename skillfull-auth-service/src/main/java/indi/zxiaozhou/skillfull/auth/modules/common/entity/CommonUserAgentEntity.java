// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户-代理人表(CommonUserAgent)Entity
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:12:37
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_common_user_agent")
public class CommonUserAgentEntity extends BaseEntity {
    private static final long serialVersionUID = 433272613011477461L;

    @TableId
    private String agentId;

    @Schema(name = "userId", title = "用户名id")
    private String userId;

    @Schema(name = "agentUserId", title = "代理人用户id")
    private String agentUserId;

    @Schema(name = "agentStartTime", title = "代理开始时间")
    private LocalDateTime agentStartTime;

    @Schema(name = "agentEndTime", title = "代理结束时间")
    private LocalDateTime agentEndTime;

    @Schema(name = "isLimit", title = "是否限制时间:0-不限制,1-限制，默认0")
    private Integer isLimit;

    @Schema(name = "agentStatus", title = "状态：0-无效，1-有效,默认0")
    private Integer agentStatus;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;
}