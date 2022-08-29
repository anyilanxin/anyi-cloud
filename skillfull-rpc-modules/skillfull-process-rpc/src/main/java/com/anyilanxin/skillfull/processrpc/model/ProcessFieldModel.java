// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 流程任务信息
 *
 * @author zxiaozhou
 * @date 2021-08-01 14:51
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessFieldModel implements Serializable {
    private static final long serialVersionUID = 4141293311374431672L;

    @Schema(name = "businessKey", title = "是否业务id")
    private boolean businessKey;

    @Schema(name = "businessKey", title = "id")
    private String id;

    @Schema(name = "businessKey", title = "字段名称")
    private String label;

    @Schema(name = "businessKey", title = "表单类型")
    private JSONObject formType;

    @Schema(name = "businessKey", title = "默认值")
    private Object defaultValue;

    @Schema(name = "businessKey", title = "字段类型")
    private JSONObject TypedValue;

    @Schema(name = "validationConstraints", title = "验证常量信息")
    private List<FormFieldValidationConstraint> validationConstraints;

    @Schema(name = "properties", title = "其他配置信息")
    private Map<String, String> properties;


    /**
     * 验证信息
     *
     * @author zxiaozhou
     * @date 2021-12-07 10:32
     * @since JDK1.8
     */
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode

    @SuperBuilder(toBuilder = true)

    @NoArgsConstructor
    public static class FormFieldValidationConstraint implements Serializable {
        private static final long serialVersionUID = 4141293311374431672L;

        @Schema(name = "name", title = "常量名称")
        private String name;

        @Schema(name = "configuration", title = "常量配置信息")
        private String configuration;
    }
}
