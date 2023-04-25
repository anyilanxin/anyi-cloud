package com.anyilanxin.skillfull.messagerpc.rpc;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.messagerpc.model.TemplateCommonMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateEmailMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateResultModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateSmsMsgModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 消息服务feign
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:54
 * @since JDK11
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
