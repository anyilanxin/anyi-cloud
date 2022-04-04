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
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corewebflux.component.BindingComponent;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.LOGIN_ONLINE_PREFIX;


/**
 * core mvc servlet util
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:24
 * @since JDK11
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketUtils {
    private static SocketUtils utils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final BindingComponent bindingComponent;

    @PostConstruct
    private void init() {
        utils = this;
    }
    

    /**
     * 获取用户登录信息
     *
     * @param linkModel ${@link WebSocketLinkModel} web socket信息
     * @return LoginOnlineInfoModel ${@link LoginOnlineInfoModel} 用户登录在线信息
     * @author zxiaozhou
     * @date 2020-10-20 18:02
     */
    public static LoginOnlineInfoModel getOnline(WebSocketLinkModel linkModel) {
        Object data = utils.redisTemplate.opsForValue().get(LOGIN_ONLINE_PREFIX + linkModel.getUserId() + ":" + linkModel.getLoginCode());
        if (Objects.nonNull(data)) {
            return (LoginOnlineInfoModel) data;
        }
        throw new ResponseException(Status.TOKEN_EXPIRED);
    }


    /**
     * 获取用户登录token有效时间
     *
     * @param linkModel ${@link WebSocketLinkModel} web socket信息
     * @return Long ${@link Long} 有效时间
     * @author zxiaozhou
     * @date 2020-10-20 18:02
     */
    public static Long getExpire(WebSocketLinkModel linkModel) {
        return utils.redisTemplate.getExpire(LOGIN_ONLINE_PREFIX + linkModel.getUserId() + ":" + linkModel.getLoginCode());
    }


    /**
     * 发送消息并设置未消费过期时间
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @param ttl         ${@link Long} 过期时间,单位:s
     * @author zxiaozhou
     * @date 2021-07-29 17:52
     */
    public static <T> void out(String bindingName, T t, long ttl) {
        utils.bindingComponent.out(bindingName, t, ttl);
    }


    /**
     * 发送消息
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @author zxiaozhou
     * @date 2021-08-30 15:32
     */
    public static <T> void out(String bindingName, T t) {
        utils.bindingComponent.out(bindingName, t);
    }
}
