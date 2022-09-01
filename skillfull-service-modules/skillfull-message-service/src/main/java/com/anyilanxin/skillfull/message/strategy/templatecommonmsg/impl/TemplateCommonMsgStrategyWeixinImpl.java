// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.templatecommonmsg.impl;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.message.strategy.templatecommonmsg.ITemplateCommonMsgStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.MsgTemplateCommonChannelConstant;
import com.anyilanxin.skillfull.messagerpc.model.TemplateCommonMsgModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 微信模板消息实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-30 09:29
 * @since JDK11
 */
@Component(value = MsgTemplateCommonChannelConstant.WX_TEMPLATE_MSG)
public class TemplateCommonMsgStrategyWeixinImpl implements ITemplateCommonMsgStrategy {
    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateCommonMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
