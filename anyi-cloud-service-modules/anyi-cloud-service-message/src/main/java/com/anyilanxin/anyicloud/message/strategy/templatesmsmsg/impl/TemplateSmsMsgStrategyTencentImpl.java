

package com.anyilanxin.anyicloud.message.strategy.templatesmsmsg.impl;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.message.strategy.templatesmsmsg.ITemplateSmsMsgStrategy;
import com.anyilanxin.anyicloud.messagerpc.constant.MsgTemplateSmsChannelConstant;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateSmsMsgModel;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 滕云短信实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-30 09:32
 * @since 1.0.0
 */
@Component(value = MsgTemplateSmsChannelConstant.TENCENT_SMS)
public class TemplateSmsMsgStrategyTencentImpl implements ITemplateSmsMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateSmsMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
