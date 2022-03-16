// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.websocket.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.BusinessType;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.SocketMsgType;
import indi.zxiaozhou.skillfull.corecommon.utils.BeanValidatorUtils;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.SocketMsgModel;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import indi.zxiaozhou.skillfull.message.modules.websocket.process.MessageContent;
import indi.zxiaozhou.skillfull.message.modules.websocket.service.IWebSocketMsgService;
import indi.zxiaozhou.skillfull.message.utils.SocketUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * socket消息处理接口实现
 *
 * @author zxiaozhou
 * @date 2021-05-11 19:59
 * @since JDK1.8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketMsgServiceImpl implements IWebSocketMsgService {
    private final MessageContent messageContent;


    @Override
    public void onMessage(SocketMsgModel msgModel) {
        WebSocketLinkModel webSocketLinkModel = msgModel.getWebSocketLinkModel();
        String msg = msgModel.getMsg();
        if (StringUtils.isNotBlank(msg)) {
            SocketResult model = JSONObject.parseObject(msg, SocketResult.class);
            // 检测消息合法性
            if (checkMsg(model, webSocketLinkModel)) {
                // 如果是业务数据类型消息立即处理，否则发送mq广播后再处理
                if (model.getMsgType() == SocketMsgType.BUSINESS_DATA.getType()) {
                    log.debug("------------WebSocketMsgServiceImpl------收到处理业务数据消息------>onMessage:{}", model);
                    messageContent.processMessage(model, webSocketLinkModel);
                } else {
                    SocketUtils.out(BindingStreamConstant.SOCKET_PROCESS, model);
                }
            }

        }
    }


    @Override
    public void socketProcess(SocketResult model) {
        log.debug("------------WebSocketMsgServiceImpl------收到mq待处理消息------>socketProcess:{}", model);
        messageContent.processMessage(model, null);
    }

    
    /**
     * 基本消息合法性校验
     *
     * @param model              ${@link SocketResult}
     * @param webSocketLinkModel ${@link WebSocketLinkModel}
     * @author zxiaozhou
     * @date 2021-06-06 15:59
     */
    private boolean checkMsg(SocketResult model, WebSocketLinkModel webSocketLinkModel) {
        BeanValidatorUtils.ValidateResult validate = BeanValidatorUtils.validate(model);
        if (!validate.isSuccess()) {
            SocketResult result = new SocketResult(BusinessType.EXCEPTION_MSG)
                    .setMessage(validate.getErrorMsg())
                    .setRequestMarker(model.getRequestMarker());
            webSocketLinkModel.sendData(result.toString());
            return false;
        }
        if (model.getMsgType() == SocketMsgType.DESIGNATED_PERSONNEL.getType() && CollectionUtil.isEmpty(model.getUserIds())) {
            SocketResult result = new SocketResult(BusinessType.EXCEPTION_MSG)
                    .setMessage("当指定接收人时用户id不能为空")
                    .setRequestMarker(model.getRequestMarker());
            webSocketLinkModel.sendData(result.toString());
            return false;
        }
        if (model.getMsgType() == SocketMsgType.DESIGNATED_SYSTEM_PERSONNEL.getType() && CollectionUtil.isEmpty(model.getLoginCodes())) {
            SocketResult result = new SocketResult(BusinessType.EXCEPTION_MSG)
                    .setMessage("当指定接收登录编码时登录编码不能为空")
                    .setRequestMarker(model.getRequestMarker());
            webSocketLinkModel.sendData(result.toString());
        }
        return true;
    }
}
