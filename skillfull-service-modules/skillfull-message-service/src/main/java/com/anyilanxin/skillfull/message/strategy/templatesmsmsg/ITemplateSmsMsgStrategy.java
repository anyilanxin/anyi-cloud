package com.anyilanxin.skillfull.message.strategy.templatesmsmsg;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.messagerpc.model.TemplateSmsMsgModel;

import java.util.List;

/**
 * 短信消息处理
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-27 11:58
 * @since JDK11
 */
public interface ITemplateSmsMsgStrategy {
    /**
     * 发送短信消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageSendRecordEntity>
     * @author zxiaozhou
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateSmsMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
