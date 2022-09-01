// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.messagerpc.model;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 发送消息
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-07 15:19
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AuthMsgModel implements Serializable {
    private static final long serialVersionUID = -3931714024892969626L;

    /**
     * 类型：1-登录过期，2-被强制下线，3-已在其他设备登录下线(开启单设备登录时有效)
     */
    private Status type;

    /**
     * 消息
     */
    private String message;

    /**
     * 鉴权信息针对token
     */
    private String token;
}
