package com.anyilanxin.skillfull.auth.oauth2.validate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 验证结果
 *
 * @author zxiaozhou
 * @date 2020-06-30 16:35
 * @since JDK11
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
