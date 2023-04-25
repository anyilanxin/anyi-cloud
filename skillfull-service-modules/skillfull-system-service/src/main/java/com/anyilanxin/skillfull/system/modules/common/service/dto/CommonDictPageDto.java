package com.anyilanxin.skillfull.system.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 数据字典表分页查询Response
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:18
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CommonDictPageDto implements Serializable {
    private static final long serialVersionUID = 656946092026147735L;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;

    @Schema(name = "dictName", title = "字典名称")
    private String dictName;

    @Schema(name = "dictCode", title = "字典编码")
    private String dictCode;

    @Schema(name = "dictType", title = "字典类型：0-字符串,1-数字,2-布尔。默认0")
    private Integer dictType;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "dictStatus", title = "字典状态：1启用，0禁用，默认0")
    private Integer dictStatus;
}
