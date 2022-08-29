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

import java.io.Serializable;

/**
 * 表单信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 11:31
 * @since JDK1.8
 */
public class ProcessFormAndDataModel extends ProcessFormModel implements Serializable {
    private static final long serialVersionUID = -8035187351349997365L;

    @Schema(name = "formData", title = "表单数据")
    private JSONObject formData;
}
