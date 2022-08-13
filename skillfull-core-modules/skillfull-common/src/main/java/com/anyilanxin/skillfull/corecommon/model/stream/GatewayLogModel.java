// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 日志信息
 *
 * @author zxiaozhou
 * @date 2021-05-06 12:56
 * @since JDK1.8
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class GatewayLogModel implements Serializable {
    private static final long serialVersionUID = -5373929396939925329L;


    @Schema(name = "loginEndpoint(与LoginEndpointType一致)", title = "登录端点")
    private String loginEndpoint;

    @Schema(name = "loginEquipment", title = "登录设备")
    private String loginEquipment;

    @Schema(name = "loginIp", title = "登录ip")
    private String loginIp;

    @Schema(name = "loginCode", title = "登录编号")
    private String loginCode;

    @Schema(name = "targetServiceName", title = "目标服务名")
    private String targetServiceName;

    @Schema(name = "targetIp", title = "目标服务ip")
    private String targetIp;

    @Schema(name = "targetPort", title = "目标服务端口")
    private int targetPort;

    @Schema(name = "requestUrl", title = "目的地路径")
    private String targetRequestUrl;

    @Schema(name = "requestAllResult", title = "请求响应所有信息")
    private String requestAllResult;

    @Schema(name = "requestResultData", title = "请求响应数据")
    private String requestResultData;

    @Schema(name = "requestStatus", title = "请求响应状态")
    private Integer requestStatus;

    @Schema(name = "requestHttpStatus", title = "请求http状态")
    private Integer requestHttpStatus;

}
