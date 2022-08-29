// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.constant.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 常量字典Model
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:56
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ConstantDictModel implements Serializable {
    private static final long serialVersionUID = 3970903709928893198L;

    @Schema(name = "type", title = "类型")
    private String type;

    @Schema(name = "typeDescribe", title = "类型描述")
    private String typeDescribe;

    @Schema(name = "typeName", title = "类型名称")
    private String typeName;

    @Schema(name = "extendInfo", title = "扩展信息,json object")
    private JSONObject extendInfo;
}
