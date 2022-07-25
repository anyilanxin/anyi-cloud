// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 待审批用户任务信息
 *
 * @author zxiaozhou
 * @date 2020-10-19 19:40
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class WaitUserTaskDetailModel implements Serializable {
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "eventName", title = "事件名称")
    private String eventName;

    @Schema(name = "mustClaim", title = "是否需要签收")
    private boolean mustClaim;

    @Schema(name = "formKey", title = "表单key")
    private String formKey;

    @Schema(name = "formJsonInfo", title = "表单信息")
    private String formJsonInfo;

    @Schema(name = "dueTime", title = "跟进日期", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueTime;

    @Schema(name = "followUpTime", title = "到期日期", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date followUpTime;

    @Schema(name = "taskId", title = "任务id")
    private String taskId;

    @Schema(name = "name", title = "任务名称")
    private String name;

    @Schema(name = "taskTitle", title = "任务标题")
    private String taskTitle;

    @Schema(name = "description", title = "任务描述")
    private String description;

    @Schema(name = "createTime", title = "任务创建时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name = "propertyInfo", title = "任务扩展信息")
    private UserTaskPropertyInfoModel propertyInfo;

    @Schema(name = "instanceInfoModel", title = "流程实例信息")
    private ActInstanceInfoModel instanceInfoModel;
}
