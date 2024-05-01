/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.message.modules.message.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.message.modules.message.service.IMessageProcessService;
import com.anyilanxin.cloud.messageadapter.constant.SocketDestinationPrefixes;
import com.anyilanxin.cloud.messageadapter.model.process.ProcessActivityMsg;
import com.anyilanxin.cloud.messageadapter.model.process.ProcessInstanceMsg;
import com.anyilanxin.cloud.messageadapter.model.process.ProcessTaskMsg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程消息相关
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:43
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "MessageProcess", description = "流程消息相关")
@RequestMapping(value = "/message/process", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageProcessController extends AnYiBaseController {
    private final IMessageProcessService service;


    @Operation(summary = "发送流程实例bpmn状态更新消息", tags = {"v1.0.0"}, description = "发送流程实例状态更新消息：<br/>" +
            "1.订阅通道：/" + SocketDestinationPrefixes.TOPIC_PROCESS + "/instance-bpmn/{platform}/{instanceId}，platform:平台编码，目前有cloudzeebe,cloud;instanceId流程实例id<br/>" +
            "2.返回数据结构,{instanceId:流程实例id}"
    )
    @PostMapping("/send/instance-bpmn")
    public AnYiResult<String> sendProcessInstanceBpmn(@RequestBody List<ProcessActivityMsg> msgs) {
        service.sendProcessInstanceBpmn(msgs);
        return ok("发送成功");
    }


    @Operation(summary = "发送流程实例状态更新消息", tags = {"v1.0.0"}, description = "发送流程实例状态更新消息：<br/>" +
            "1.订阅通道：/" + SocketDestinationPrefixes.TOPIC_PROCESS + "/process-instance-refresh/{platform}/{initiator}，platform:平台编码，目前有cloudzeebe,cloud;initiator流程发起用户id<br/>" +
            "2.notice、banner通知详见对应接口<br/>" +
            "3.返回数据结构,{instanceId:流程实例id}"
    )
    @PostMapping(value = "/send/process-instance")
    public AnYiResult<String> sendProcessInstance(@RequestBody @Valid List<ProcessInstanceMsg> msgs) {
        service.sendProcessInstance(msgs);
        return ok("发送成功");
    }


    @Operation(summary = "发送流程任务状态更新", tags = {"v1.0.0"}, description = "发送流程任务状态更新：<br/>" +
            "1.订阅通道：/" + SocketDestinationPrefixes.TOPIC_PROCESS + "/process-task-refresh/{platform}/{assignee}，platform:平台编码，目前有cloudzeebe,cloud;assignee流程审批用户id<br/>" +
            "2.notice、banner通知详见对应接口<br/>" +
            "3.返回数据结构,{instanceId:流程实例id}"
    )
    @PostMapping(value = "/send/process-task")
    public AnYiResult<String> sendProcessTask(@RequestBody @Valid List<ProcessTaskMsg> msgs) {
        service.sendProcessTask(msgs);
        return ok("发送成功");
    }

}
