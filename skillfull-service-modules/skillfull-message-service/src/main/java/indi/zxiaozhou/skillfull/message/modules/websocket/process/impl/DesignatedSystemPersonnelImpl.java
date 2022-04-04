// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.websocket.process.impl;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import indi.zxiaozhou.skillfull.message.modules.websocket.process.IMessageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理指定系统接收人
 *
 * @author zxiaozhou
 * @date 2021-04-02 09:59
 * @since JDK1.8
 */
@Component(SysBaseConstant.SOCKET_MSG_TYPE_PREFIX + 1)
@Slf4j
public class DesignatedSystemPersonnelImpl implements IMessageStrategy {
    @Override
    public void processMessage(ConcurrentHashMap<String, WebSocketLinkModel> socketMap, SocketResult model, WebSocketLinkModel webSocketLinkModel) {
        log.debug("------------DesignatedSystemPersonnelImpl-----处理指定系统接收人消息------->processMessage:{}", model);
        List<String> loginCodes = model.getLoginCodes();
        List<WebSocketLinkModel> linkModels = new ArrayList<>();
        model.setSystemIds(null);
        if (CollectionUtil.isNotEmpty(socketMap)) {
            socketMap.forEach((k, v) -> {
                for (String loginCode : loginCodes) {
                    if (v.getLoginCode().equals(loginCode)) {
                        linkModels.add(v);
                        break;
                    }
                }
            });
            if (CollectionUtil.isNotEmpty(linkModels)) {
                linkModels.forEach(v -> v.sendData(model.toString()));
            }
        }
    }
}
