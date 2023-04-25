package com.anyilanxin.skillfull.message.strategy.templateemailmsg;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.messagerpc.model.TemplateEmailMsgModel;

import java.util.List;

/**
 * 邮件消息处理
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-27 11:58
 * @since JDK11
 */
public interface ITemplateEmailMsgStrategy {
    /**
     * 发送邮件消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageSendRecordEntity>
     * @author zxiaozhou
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateEmailMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
