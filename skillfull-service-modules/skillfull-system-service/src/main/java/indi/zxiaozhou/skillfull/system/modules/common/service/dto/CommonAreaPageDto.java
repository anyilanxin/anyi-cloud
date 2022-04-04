// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.model.BaseTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 区域表分页查询Response
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:04
 * @since JDK11
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class CommonAreaPageDto extends BaseTree<CommonAreaPageDto> implements Serializable {
    private static final long serialVersionUID = 416249807570362423L;

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

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;

    @Schema(name = "countyId", title = "区县id")
    private String countyId;

    @Schema(name = "remark", title = "备注/说明")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}