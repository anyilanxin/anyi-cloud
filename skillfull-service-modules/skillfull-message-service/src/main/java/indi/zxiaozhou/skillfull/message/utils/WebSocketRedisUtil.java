// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.utils;

import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginSocketInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.mapstruct.OnlineSocketMap;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.LOGIN_SOCKET_PREFIX;

/**
 * websocket操作redis工具类
 *
 * @author zxiaozhou
 * @date 2021-01-14 12:13
 * @since JDK11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketRedisUtil {
    private static WebSocketRedisUtil util;
    private final RedisTemplate<String, Object> redisTemplate;
    private final OnlineSocketMap socketMap;

    /**
     * 添加链接信息到redis
     *
     * @param linkModel ${@link String} socket链接信息
     * @author zxiaozhou
     * @date 2021-01-25 16:17
     */
    public static void addMsgConnect(WebSocketLinkModel linkModel) {
        WebSocketSession session = linkModel.getSession();
        LoginOnlineInfoModel onlineInfoModel = SocketUtils.getOnline(linkModel);
        LoginSocketInfoModel loginSocketInfoModel = util.socketMap.bToA(onlineInfoModel);
        loginSocketInfoModel.setSocketSessionId(session.getId());
        loginSocketInfoModel.setConnectionTime(LocalDateTime.now());
        loginSocketInfoModel.setConnectionEquipment(onlineInfoModel.getLoginEquipment());
        Long expire = SocketUtils.getExpire(linkModel);
        if (Objects.isNull(expire)) {
            util.redisTemplate.opsForValue().set(LOGIN_SOCKET_PREFIX + linkModel.getUserId() + ":" + linkModel.getLoginCode(), loginSocketInfoModel);
        } else {
            util.redisTemplate.opsForValue().set(LOGIN_SOCKET_PREFIX + linkModel.getUserId() + ":" + linkModel.getLoginCode(), loginSocketInfoModel, expire, TimeUnit.SECONDS);
        }
    }


    /**
     * 删除用户链接信息
     *
     * @param linkModel ${@link WebSocketLinkModel} 链接信息
     * @author zxiaozhou
     * @date 2021-01-25 16:18
     */
    public static void deleteMsgUser(WebSocketLinkModel linkModel) {
        util.redisTemplate.delete(LOGIN_SOCKET_PREFIX + linkModel.getUserId() + ":" + linkModel.getLoginCode());
    }

    @PostConstruct
    private void init() {
        util = this;
    }
}
