// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.aspect.model;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 数据日志添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-09-14 23:39:46
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DataModel implements Serializable {
    private static final long serialVersionUID = 597266457862845864L;

    @Schema(name = "dataTable", title = "表名")
    private String dataTable;

    @Schema(name = "logNote", title = "日志注解")
    private String logNote;

    @Schema(name = "dataId", title = "数据id")
    private String dataId;

    @Schema(name = "dataContent", title = "数据内容", required = true)
    @NotBlankOrNull(message = "数据内容不能为空")
    private String dataContent;

    @Schema(name = "fileName", title = "调用文件名", required = true)
    @NotBlankOrNull(message = "调用文件名不能为空")
    private String fileName;

    @Schema(name = "methodName", title = "调用方法名", required = true)
    @NotBlankOrNull(message = "调用方法名不能为空")
    private String methodName;

    @Schema(name = "methodParams", title = "调用方法参数")
    private String methodParams;

    @Schema(name = "methodStatus", title = "调用方法状态:0-失败,1-成功,默认1")
    private Integer methodStatus;

    @Schema(name = "exceptionName", title = "异常名")
    private String exceptionName;

    @Schema(name = "stackTrace", title = "堆栈信息")
    private String stackTrace;

    @Schema(name = "exceptionMessage", title = "异常消息")
    private String exceptionMessage;

    @Schema(name = "lineNumber", title = "代码行数")
    private Integer lineNumber;

    @Schema(name = "requestStartTime", title = "请求开始时间(创建实例时自动处理)", hidden = true, type = "string", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}