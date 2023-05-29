

package com.anyilanxin.anyicloud.system.modules.manage.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 自定义过滤器查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:14
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageCustomFilterSimpleDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "filterName", title = "过滤器名称")
    private String filterName;

    @Schema(name = "filterTypeName", title = "过滤器类型名称")
    private String filterTypeName;

    @Schema(name = "filterType", title = "过滤器类型")
    private String filterType;

    @Schema(name = "filterStatus", title = "过滤器状态:0-禁用,1-启用，默认0")
    private Integer filterStatus;

    @Schema(name = "haveSpecial", title = "是否有特殊url:0-没有,1-有。默认0")
    private Integer haveSpecial;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
