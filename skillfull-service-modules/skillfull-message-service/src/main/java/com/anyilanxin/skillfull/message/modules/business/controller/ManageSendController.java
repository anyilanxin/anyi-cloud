/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.message.modules.business.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.message.strategy.templatecommonmsg.TemplateCommonMsgContent;
import com.anyilanxin.skillfull.message.strategy.templateemailmsg.TemplateEmailMsgContent;
import com.anyilanxin.skillfull.message.strategy.templatesmsmsg.TemplateSmsMsgContent;
import com.anyilanxin.skillfull.messagerpc.model.TemplateCommonMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateEmailMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateResultModel;
import com.anyilanxin.skillfull.messagerpc.model.TemplateSmsMsgModel;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 消息发送(ManageSendRecord)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:40
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageSend", description = "消息发送相关")
@RequestMapping(value = "/send-template", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageSendController extends BaseController {
    private final TemplateCommonMsgContent commonMsgContent;
    private final TemplateSmsMsgContent smsMsgContent;
    private final TemplateEmailMsgContent emailMsgContent;


    @Operation(summary = "发送通用模板消息", tags = {"v1.0.0"}, description = "发送通用模板消息")
    @PostMapping(value = "/common")
    public Result<TemplateResultModel> sendTemplateCommon(@RequestBody @Valid TemplateCommonMsgModel model) {
        return ok(commonMsgContent.sendCommon(model));
    }


    @Operation(summary = "发送短信模板消息", tags = {"v1.0.0"}, description = "发送短信模板消息")
    @PostMapping(value = "/sms")
    public Result<TemplateResultModel> sendTemplateSms(@RequestBody @Valid TemplateSmsMsgModel model) {
        return ok(smsMsgContent.sendSms(model));
    }


    @Operation(summary = "发送邮件模板消息", tags = {"v1.0.0"}, description = "发送邮件模板消息")
    @PostMapping(value = "/email")
    public Result<TemplateResultModel> sendTemplateEmail(@RequestBody @Valid TemplateEmailMsgModel model) {
        return ok(emailMsgContent.sendEmail(model));
    }
}
