// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.afterconnection;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.WebSocketSession;

/**
 * afterConnection处理
 *
 * @author zxiaozhou
 * @date 2022-08-27 11:32
 * @since JDK11
 */
public interface AfterConnectionStrategy {

    /**
     * 链接后处理
     *
     * @param session
     * @author zxiaozhou
     * @date 2022-08-28 21:10
     */
    @Async
    void handleAfterMsg(WebSocketSession session);
}
