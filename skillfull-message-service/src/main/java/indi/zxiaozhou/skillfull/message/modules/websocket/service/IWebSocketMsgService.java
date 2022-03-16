// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.websocket.service;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.SocketMsgModel;
import reactor.core.publisher.Mono;

/**
 * socket消息处理接口
 *
 * @author zxiaozhou
 * @date 2021-05-11 19:58
 * @since JDK1.8
 */
public interface IWebSocketMsgService {
    /**
     * web socket链接处理
     *
     * @param msgModel ${@link SocketMsgModel}
     * @return Mono<Void>  ${@link Mono<Void> }
     * @author zxiaozhou
     * @date 2021-05-11 19:59
     */
    void onMessage(SocketMsgModel msgModel);


    /**
     * 接收stream处理消息
     *
     * @param model ${@link SocketResult}
     * @author zxiaozhou
     * @date 2021-05-29 12:18
     */
    void socketProcess(SocketResult model);
}
