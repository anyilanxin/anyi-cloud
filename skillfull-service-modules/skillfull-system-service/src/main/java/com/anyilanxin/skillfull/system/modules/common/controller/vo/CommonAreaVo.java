// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 区域表添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:03
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonAreaVo implements Serializable {
    private static final long serialVersionUID = 255355653140939408L;

    @Schema(name = "areaId", title = "区域id,12位长度", required = true)
    @NotBlankOrNull(message = "区域id不能为空")
    @Length(min = 12, max = 12, message = "区域id必须12位,每3位代表一个级别")
    private String areaId;

    @Schema(name = "parentId", title = "上级区域id")
    private String parentId;

    @Schema(name = "prePinYin", title = "区域名称拼音的第一个字母", required = true)
    @NotBlankOrNull(message = "区域名称拼音的第一个字母不能为空")
    private String prePinYin;

    @Schema(name = "simplePy", title = "首字母简拼", required = true)
    @NotBlankOrNull(message = "首字母简拼不能为空")
    private String simplePy;

    @Schema(name = "pinYin", title = "区域名称拼音", required = true)
    @NotBlankOrNull(message = "区域名称拼音不能为空")
    private String pinYin;

    @Schema(name = "provinceId", title = "所属省级id")
    private String provinceId;

    @Schema(name = "simpleName", title = "中文简称", required = true)
    @NotBlankOrNull(message = "中文简称不能为空")
    private String simpleName;

    @Schema(name = "areaLevel", title = "级别：1为省级，2为市级，3为县级, 4为乡, 5为村,4为乡,5为村", required = true)
    @NotBlankOrNull(message = "级别：1为省级，2为市级，3为县级, 4为乡, 5为村不能为空")
    private Integer areaLevel;

    @Schema(name = "areaName", title = "区域名称", required = true)
    @NotBlankOrNull(message = "区域名称不能为空")
    private String areaName;

    @Schema(name = "areaCode", title = "区号", required = true)
    @NotBlankOrNull(message = "区号不能为空")
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

    @Schema(name = "remark", title = "备注")
    private String remark;
}