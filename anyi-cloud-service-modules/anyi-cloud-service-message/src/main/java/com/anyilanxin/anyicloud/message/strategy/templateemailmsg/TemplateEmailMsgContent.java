

package com.anyilanxin.anyicloud.message.strategy.templateemailmsg;

import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.IManageSendRecordService;
import com.anyilanxin.anyicloud.message.modules.manage.service.IManageTemplateService;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateEmailMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateResultModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 邮件消息处理
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
@Component
public class TemplateEmailMsgContent {
    private final IManageTemplateService templateService;
    private final IManageSendRecordService recordService;
    private static final Map<String, ITemplateEmailMsgStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public TemplateEmailMsgContent(final Map<String, ITemplateEmailMsgStrategy> strategyMap, final IManageTemplateService templateService, final IManageSendRecordService recordService) {
        STRATEGY.putAll(strategyMap);
        this.templateService = templateService;
        this.recordService = recordService;
    }


    /**
     * 发送邮件
     *
     * @param model
     * @author zxh
     * @date 2022-08-30 09:47
     */
    public TemplateResultModel sendEmail(TemplateEmailMsgModel model) {
        String type = model.getChannel().getType();
        ITemplateEmailMsgStrategy emailMsgStrategy = STRATEGY.get(type);
        if (Objects.isNull(emailMsgStrategy)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到当前渠道的实现:" + type);
        }
        ManageTemplateSendInfoDto sendInfo = templateService.getSendInfo(model.getTemplateCode());
        if (Objects.isNull(sendInfo)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到当前模板的配置信息:" + model.getTemplateCode());
        }
        List<ManageSendRecordEntity> manageSendRecordEntities = emailMsgStrategy.sendMsg(model, sendInfo);
        return recordService.saveBatchRecord(manageSendRecordEntities);
    }
}
