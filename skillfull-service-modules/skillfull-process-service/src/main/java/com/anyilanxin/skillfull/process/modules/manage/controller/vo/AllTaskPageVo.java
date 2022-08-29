// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 获取我的代办
 *
 * @author zxiaozhou
 * @date 2020-10-19 19:37
 * @since JDK11
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class AllTaskPageVo extends BasePageVo implements Serializable {
    private static final long serialVersionUID = 2741579373373551412L;

    @Schema(name = "taskName", title = "任务名称")
    private String taskName;

    @Schema(name = "assignee", title = "处理人")
    private String assignee;

    @Schema(name = "roleCode", title = "处理角色")
    private String roleCode;

    @Schema(name = "candidateUser", title = "处理候选人")
    private String candidateUser;

    @Schema(name = "type", title = "任务类型:")
    private Integer type;
}
