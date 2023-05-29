

package com.anyilanxin.anyicloud.messagerpc.rpc;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateCommonMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateEmailMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateResultModel;
import com.anyilanxin.anyicloud.messagerpc.model.TemplateSmsMsgModel;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息服务feign
 *
 * @author zxh
 * @date 2020-09-12 16:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.MESSAGE_SERVICE, path = ServiceConstant.MESSAGE_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface MessageRemoteRpcSendTemplate {

    /**
     * 发送通用模板消息
     */
    @PostMapping(value = "/send-template/common")
    Result<TemplateResultModel> sendTemplateCommon(@RequestBody @Valid TemplateCommonMsgModel model);


    /**
     * 发送短信模板消息
     */
    @PostMapping(value = "/send-template/sms")
    Result<TemplateResultModel> sendTemplateSms(@RequestBody @Valid TemplateSmsMsgModel model);


    /**
     * 发送邮件模板消息
     */
    @PostMapping(value = "/send-template/email")
    Result<TemplateResultModel> sendTemplateEmail(@RequestBody @Valid TemplateEmailMsgModel model);
}
