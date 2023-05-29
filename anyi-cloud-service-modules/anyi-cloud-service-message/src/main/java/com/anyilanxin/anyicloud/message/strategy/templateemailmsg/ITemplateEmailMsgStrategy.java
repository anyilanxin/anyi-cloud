

package com.anyilanxin.anyicloud.message.strategy.templateemailmsg;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateEmailMsgModel;

import java.util.List;

/**
 * 邮件消息处理
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
public interface ITemplateEmailMsgStrategy {
    /**
     * 发送邮件消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageSendRecordEntity>
     * @author zxh
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateEmailMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
