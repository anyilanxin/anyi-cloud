

package com.anyilanxin.anyicloud.message.strategy.templatecommonmsg;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateCommonMsgModel;

import java.util.List;

/**
 * 消息没了没有子类的消息处理
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
public interface ITemplateCommonMsgStrategy {
    /**
     * 发送其他消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageTemplateEntity>
     * @author zxh
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateCommonMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
