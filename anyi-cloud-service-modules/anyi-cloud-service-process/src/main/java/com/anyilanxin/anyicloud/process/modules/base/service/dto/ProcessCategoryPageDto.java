

package com.anyilanxin.anyicloud.process.modules.base.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程类别分页查询Response
 *
 * @author zxh
 * @date 2021-11-19 10:47:01
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ProcessCategoryPageDto implements Serializable {
    private static final long serialVersionUID = 694284762200872007L;

    @Schema(name = "categoryId", title = "类别id")
    private String categoryId;

    @Schema(name = "categoryCode", title = "类别编码(唯一)")
    private String categoryCode;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "categoryState", title = "类别状态:0-禁用,1-启用。默认0")
    private Integer categoryState;

    @Schema(name = "pictures", title = "类别logo")
    private String pictures;

    @Schema(name = "categoryDescribe", title = "类别描述")
    private String categoryDescribe;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
