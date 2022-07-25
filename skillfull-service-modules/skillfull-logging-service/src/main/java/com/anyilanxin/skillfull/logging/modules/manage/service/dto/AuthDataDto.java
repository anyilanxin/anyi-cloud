// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 登录日志查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 21:53:02
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class AuthDataDto implements Serializable {
    private static final long serialVersionUID = 861527679376323127L;

    @Schema(name = "authLogId", title = "授权日志id")
    private String authLogId;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "authType", title = "授权类型，具体参考授权服务中AuthType常量字典")
    private String authType;

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

    @Schema(name = "logData", title = "日志内容")
    private String logData;

    @Schema(name = "logOtherData", title = "日志其余内容")
    private String logOtherData;

    @Schema(name = "exceptionMessage", title = "异常消息")
    private String exceptionMessage;

    @Schema(name = "costTime", title = "耗时")
    private Long costTime;

    @Schema(name = "requestStartTime", title = "请求开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "requestEndTime", title = "请求结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
