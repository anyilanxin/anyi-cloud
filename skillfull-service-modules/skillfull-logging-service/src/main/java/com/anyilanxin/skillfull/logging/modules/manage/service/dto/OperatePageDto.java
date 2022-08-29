// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
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
 * 操作日志分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-13 10:24:41
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class OperatePageDto implements Serializable {
    private static final long serialVersionUID = -79023533300323244L;

    @Schema(name = "operateId", title = "操作日志id")
    private String operateId;

    @Schema(name = "operateType", title = " 操作类型（1查询，2添加，3修改，4删除，5其他）具体与常量字典OperateType一致")
    private Integer operateType;

    @Schema(name = "userId", title = "操作人用户id")
    private String userId;

    @Schema(name = "userName", title = "操作人用户名称")
    private String userName;

    @Schema(name = "requestClientCode", title = "请求客户端编号")
    private String requestClientCode;

    @Schema(name = "requestClientName", title = "请求客户端名称")
    private String requestClientName;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "targetServiceCode", title = "目标服务")
    private String targetServiceCode;

    @Schema(name = "targetUrl", title = "目标地址")
    private String targetUrl;

    @Schema(name = "requestUrl", title = "请求路径")
    private String requestUrl;

    @Schema(name = "requestMethod", title = "请求方法")
    private String requestMethod;

    @Schema(name = "requestParam", title = "请求参数")
    private String requestParam;

    @Schema(name = "requestResult", title = "请求结果")
    private String requestResult;

    @Schema(name = "logOtherData", title = "日志其余内容")
    private String logOtherData;

    @Schema(name = "exceptionMessage", title = "异常消息")
    private String exceptionMessage;

    @Schema(name = "operateStatus", title = "操作状态：0-失败,1-成功")
    private Integer operateStatus;

    @Schema(name = "dataSources", title = "数据来源")
    private String dataSources;

    @Schema(name = "dataSourcesDescribe", title = "数据来源说明")
    private String dataSourcesDescribe;

    @Schema(name = "costTime", title = "耗时")
    private Long costTime;

    @Schema(name = "requestStartTime", title = "请求开始时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "requestEndTime", title = "请求结束时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
