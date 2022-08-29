// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区域表条件查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:04
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class CommonAreaQueryVo implements Serializable {
    private static final long serialVersionUID = 924492404884446389L;

    @Schema(name = "areaId", title = "区域id")
    private String areaId;

    @Schema(name = "parentId", title = "上级区域id")
    private String parentId;

    @Schema(name = "prePinYin", title = "区域名称拼音的第一个字母")
    private String prePinYin;

    @Schema(name = "simplePy", title = "首字母简拼")
    private String simplePy;

    @Schema(name = "pinYin", title = "区域名称拼音")
    private String pinYin;

    @Schema(name = "wholeName", title = "完整名称")
    private String wholeName;

    @Schema(name = "provinceId", title = "所属省级id")
    private String provinceId;

    @Schema(name = "simpleName", title = "中文简称")
    private String simpleName;

    @Schema(name = "areaLevel", title = "级别：1为省级，2为市级，3为县级, 4为乡, 5为村")
    private Integer areaLevel;

    @Schema(name = "areaName", title = "区域名称")
    private String areaName;

    @Schema(name = "areaCode", title = "区号")
    private String areaCode;

    @Schema(name = "cityId", title = "所属城市id")
    private String cityId;

    @Schema(name = "lon", title = "本区域经度")
    private String lon;

    @Schema(name = "lat", title = "本区域纬度")
    private String lat;

    @Schema(name = "zipCode", title = "邮编")
    private String zipCode;

    @Schema(name = "countyId", title = "区县id")
    private String countyId;

    @Schema(name = "remark", title = "备注/说明")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
