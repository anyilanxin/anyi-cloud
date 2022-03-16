package indi.zxiaozhou.skillfull.gateway.modules.authcheck.model;

import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 权限检测结果
 *
 * @author zxiaozhou
 * @date 2021-05-06 14:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeCheckResult implements Serializable {

    private static final long serialVersionUID = 9066489545944505147L;

    /**
     * 检测结果
     */
    private boolean result;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误代码
     */
    private Status errorCode;

    /**
     * 检测url
     */
    private String uri;
}
