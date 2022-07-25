// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.announcement.controller.vo;

import com.anyilanxin.skillfull.database.datasource.base.controller.vo.BasePageVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 系统通知公告阅读记录分页查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 08:35:34
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@Schema
public class AnnouncementRecordPageVo extends BasePageVo {
    private static final long serialVersionUID = 716898468493570077L;

    @Schema(name = "anntReadId", title = "通知公告阅读记录id")
    private String anntReadId;

    @Schema(name = "anntId", title = "通知公告id")
    private String anntId;

    @Schema(name = "readStatus", title = "阅读状态：0-未读，1-已经。默认0")
    private Integer readStatus;

    @Schema(name = "readTime", title = "阅读时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime readTime;

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

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

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

}
