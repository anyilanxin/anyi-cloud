

package com.anyilanxin.anyicloud.coremvc.aspect.model;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 操作日志添加或修改Request
 *
 * @author zxh
 * @date 2020-09-14 23:39:55
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class OperateModel implements Serializable {
    private static final long serialVersionUID = -72227624406240356L;

    @Schema(name = "logNote", title = "日志注解")
    private String logNote;

    @Schema(name = "operateType", title = " 操作类型（1查询，2添加，3修改，4删除，5其他）", required = true)
    @NotBlankOrNull(message = " 操作类型不能为空")
    private Integer operateType;

    @Schema(name = "fileName", title = "调用文件名")
    private String fileName;

    @Schema(name = "methodName", title = "调用方法名")
    private String methodName;

    @Schema(name = "methodParams", title = "调用方法参数")
    private String methodParams;

    @Schema(name = "exceptionName", title = "异常名")
    private String exceptionName;

    @Schema(name = "stackTrace", title = "堆栈信息")
    private String stackTrace;

    @Schema(name = "exceptionMessage", title = "异常消息")
    private String exceptionMessage;

    @Schema(name = "requestResult", title = "请求结果")
    private String requestResult;

    @Schema(name = "requestParam", title = "请求参数")
    private String requestParam;

    @Schema(name = "lineNumber", title = "代码行数")
    private Integer lineNumber;

    @Schema(name = "requestStartTime", title = "请求开始时间(创建实例时自动处理)", hidden = true, type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
