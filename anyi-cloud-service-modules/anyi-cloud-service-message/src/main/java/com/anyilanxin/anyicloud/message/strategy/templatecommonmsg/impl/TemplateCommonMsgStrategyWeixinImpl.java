

package com.anyilanxin.anyicloud.message.strategy.templatecommonmsg.impl;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.message.strategy.templatecommonmsg.ITemplateCommonMsgStrategy;
import com.anyilanxin.anyicloud.messagerpc.constant.MsgTemplateCommonChannelConstant;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateCommonMsgModel;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 微信模板消息实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-30 09:29
 * @since 1.0.0
 */
@Component(value = MsgTemplateCommonChannelConstant.WX_TEMPLATE_MSG)
public class TemplateCommonMsgStrategyWeixinImpl implements ITemplateCommonMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateCommonMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
