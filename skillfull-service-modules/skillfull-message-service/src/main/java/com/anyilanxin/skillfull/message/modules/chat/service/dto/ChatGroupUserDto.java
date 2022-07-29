// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 群成员信息查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:26
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ChatGroupUserDto implements Serializable {
    private static final long serialVersionUID = 545713391012221438L;

    @Schema(name = "groupUserId", title = "群成员id")
    private String groupUserId;

    @Schema(name = "chatGroupId", title = "群id")
    private String chatGroupId;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "administrator", title = "是否管理员:0-不是，1-是。默认0")
    private Integer administrator;

    @Schema(name = "manager", title = "是否群主：0-不是，1-是，默认0")
    private Integer manager;

    @Schema(name = "joinTime", title = "加入时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime joinTime;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

    @Schema(name = "joinSuccessTime", title = "加入成功", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime joinSuccessTime;

}
