

package com.anyilanxin.anyicloud.message.strategy.templateemailmsg.impl;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.message.strategy.templateemailmsg.ITemplateEmailMsgStrategy;
import com.anyilanxin.anyicloud.messagerpc.constant.MsgTemplateEmailChannelConstant;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateEmailMsgModel;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 邮件qq邮箱实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-30 09:32
 * @since 1.0.0
 */
@Component(value = MsgTemplateEmailChannelConstant.MAIL_QQ)
public class TemplateEmailMsgStrategyQQEmailImpl implements ITemplateEmailMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateEmailMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
