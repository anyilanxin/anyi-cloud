

package com.anyilanxin.anyicloud.corecommon.constant.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 常量字典Model
 *
 * @author zxh
 * @date 2020-09-12 16:56
 * @since 1.0.0
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
