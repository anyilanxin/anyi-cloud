

package com.anyilanxin.anyicloud.message.strategy.templatesmsmsg;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateSmsMsgModel;

import java.util.List;

/**
 * 短信消息处理
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
public interface ITemplateSmsMsgStrategy {
    /**
     * 发送短信消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageSendRecordEntity>
     * @author zxh
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateSmsMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
