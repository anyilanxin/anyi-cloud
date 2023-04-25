package com.anyilanxin.skillfull.message.strategy.templateemailmsg.impl;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.message.strategy.templateemailmsg.ITemplateEmailMsgStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.MsgTemplateEmailChannelConstant;
import com.anyilanxin.skillfull.messagerpc.model.TemplateEmailMsgModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 邮件qq邮箱实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-30 09:32
 * @since JDK11
 */
@Component(value = MsgTemplateEmailChannelConstant.MAIL_QQ)
public class TemplateEmailMsgStrategyQQEmailImpl implements ITemplateEmailMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateEmailMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
