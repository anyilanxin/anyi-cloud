package com.anyilanxin.skillfull.logging.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权日志分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-13 10:24:40
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class AuthDataPageDto implements Serializable {
    private static final long serialVersionUID = 439082307107456507L;

    @Schema(name = "authLogId", title = "授权日志id")
    private String authLogId;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "ipAddress", title = "请求ip属地")
    private String ipAddress;

    @Schema(name = "authType", title = "授权类型，具体参考常量字典AuthorizedGrantTypes")
    private String authType;

    @Schema(name = "authTypeDescribe", title = "授权类型描述，具体参考常量字典AuthorizedGrantTypes")
    private String authTypeDescribe;

    @Schema(name = "authUserId", title = "授权用户id")
    private String authUserId;

    @Schema(name = "authUserName", title = "授权用户名称")
    private String authUserName;

    @Schema(name = "authClientCode", title = "授权客户端编号")
    private String authClientCode;

    @Schema(name = "authClientName", title = "授权客户端名称")
    private String authClientName;

    @Schema(name = "authStatus", title = "授权状态：0-失败,1-成功")
    private Integer authStatus;

    @Schema(name = "requestStartTime", title = "请求开始时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "costTime", title = "耗时")
    private Long costTime;

    @Schema(name = "requestEndTime", title = "请求结束时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
