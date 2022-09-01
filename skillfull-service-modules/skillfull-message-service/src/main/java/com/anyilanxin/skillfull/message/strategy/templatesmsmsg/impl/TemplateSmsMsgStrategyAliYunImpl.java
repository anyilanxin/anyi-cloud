// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.templatesmsmsg.impl;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.message.strategy.templatesmsmsg.ITemplateSmsMsgStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.MsgTemplateSmsChannelConstant;
import com.anyilanxin.skillfull.messagerpc.model.TemplateSmsMsgModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 阿里云短信实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-30 09:32
 * @since JDK11
 */
@Component(value = MsgTemplateSmsChannelConstant.ALIYUN_SMS)
public class TemplateSmsMsgStrategyAliYunImpl implements ITemplateSmsMsgStrategy {

    @Override
    public List<ManageSendRecordEntity> sendMsg(TemplateSmsMsgModel model, ManageTemplateSendInfoDto sendInfo) {
        return null;
    }
}
