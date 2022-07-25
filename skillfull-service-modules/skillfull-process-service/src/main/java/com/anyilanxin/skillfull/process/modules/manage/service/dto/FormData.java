// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.form.FormField;

import java.io.Serializable;
import java.util.List;

/**
 * 表单信息
 *
 * @author zxiaozhou
 * @date 2021-08-01 04:19
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class FormData implements Serializable {
    private static final long serialVersionUID = 3259297787805731201L;

    @Schema(name = "formKey", title = "表单key")
    private String formKey;

    @Schema(name = "formFields", title = "表单字段信息")
    private List<FormField> formFields;
}
