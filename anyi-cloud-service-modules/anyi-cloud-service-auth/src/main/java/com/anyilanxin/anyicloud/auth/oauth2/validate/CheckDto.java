

package com.anyilanxin.anyicloud.auth.oauth2.validate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 验证结果
 *
 * @author zxh
 * @date 2020-06-30 16:35
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class CheckDto implements Serializable {
    private static final long serialVersionUID = -5032945213213317228L;
    /**
     * 验证结果
     */
    private boolean result;

    /**
     * 验证结果信息
     */
    private String msg;
}
