/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.message.strategy.templateemailmsg;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageSendRecordService;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageTemplateService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.messagerpc.model.TemplateEmailMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateResultModel;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* 邮件消息处理
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-08-27 11:58
* @since JDK11
*/
@Component
public class TemplateEmailMsgContent {
    private final IManageTemplateService templateService;
    private final IManageSendRecordService recordService;
    private static final Map<String, ITemplateEmailMsgStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public TemplateEmailMsgContent(
            final Map<String, ITemplateEmailMsgStrategy> strategyMap,
            final IManageTemplateService templateService,
            final IManageSendRecordService recordService) {
        STRATEGY.putAll(strategyMap);
        this.templateService = templateService;
        this.recordService = recordService;
    }

    /**
    * 发送邮件
    *
    * @param model
    * @author zxiaozhou
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
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, "未找到当前模板的配置信息:" + model.getTemplateCode());
        }
        List<ManageSendRecordEntity> manageSendRecordEntities =
                emailMsgStrategy.sendMsg(model, sendInfo);
        return recordService.saveBatchRecord(manageSendRecordEntities);
    }
}
