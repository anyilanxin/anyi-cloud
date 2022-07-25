// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 表单信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 11:31
 * @since JDK1.8
 */
public class ProcessFormModel implements Serializable {
    private static final long serialVersionUID = -8035187351349997365L;

    @Schema(name = "type", title = "表单类型:1-设计器表单，2-camunda表单")
    private Integer type;

    @Schema(name = "formProp", title = "设计器表单表单属性")
    private FormPropDesignModel designFormProp;

    @Schema(name = "camundaFormProp", title = "camunda表单属性")
    private FormPropCamundaModel camundaFormProp;
}
