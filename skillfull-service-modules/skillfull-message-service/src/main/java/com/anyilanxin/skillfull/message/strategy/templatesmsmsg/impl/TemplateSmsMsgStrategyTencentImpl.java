package com.anyilanxin.skillfull.message.strategy.templatesmsmsg.impl;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.message.strategy.templatesmsmsg.ITemplateSmsMsgStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.MsgTemplateSmsChannelConstant;
import com.anyilanxin.skillfull.messagerpc.model.TemplateSmsMsgModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 滕云短信实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-30 09:32
 * @since JDK11
 */
@Component(value = MsgTemplateSmsChannelConstant.TENCENT_SMS)
public class TemplateSmsMsgStrategyTencentImpl implements ITemplateSmsMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateSmsMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
