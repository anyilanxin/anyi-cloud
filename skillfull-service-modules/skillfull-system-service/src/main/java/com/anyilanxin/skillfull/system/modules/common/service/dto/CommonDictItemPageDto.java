// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 数据字典配置项表分页查询Response
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:25
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonDictItemPageDto implements Serializable {
    private static final long serialVersionUID = -82769428547369081L;

    @Schema(name = "itemId", title = "字典项id")
    private String itemId;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;

    @Schema(name = "itemText", title = "字典项名称")
    private String itemText;

    @Schema(name = "itemValue", title = "字典项值")
    private String itemValue;

    @Schema(name = "dictName", title = "字典名称")
    private String dictName;

    @Schema(name = "dictCode", title = "字典编码")
    private String dictCode;

    @Schema(name = "sortOrder", title = "排序,越小越靠前,默认0")
    private Integer sortOrder;

    @Schema(name = "itemStatus", title = "字典项状态：1启用，0不启用，默认0")
    private Integer itemStatus;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}