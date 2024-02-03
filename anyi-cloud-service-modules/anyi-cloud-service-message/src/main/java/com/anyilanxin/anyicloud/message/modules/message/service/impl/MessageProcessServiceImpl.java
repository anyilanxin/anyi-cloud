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
package com.anyilanxin.anyicloud.message.modules.message.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState;
import com.anyilanxin.anyicloud.coreprocess.emuns.UserTaskState;
import com.anyilanxin.anyicloud.message.modules.message.service.IMessageProcessService;
import com.anyilanxin.anyicloud.message.modules.message.service.ISocketNoticeBannerService;
import com.anyilanxin.anyicloud.message.modules.message.service.ISocketNoticeService;
import com.anyilanxin.anyicloud.messageadapter.constant.enums.NoticeType;
import com.anyilanxin.anyicloud.messageadapter.constant.enums.SocketDestinationPrefixesTypes;
import com.anyilanxin.anyicloud.messageadapter.model.bannernotice.SocketMsgBannerNoticeModel;
import com.anyilanxin.anyicloud.messageadapter.model.notice.SocketMsgNoticeModel;
import com.anyilanxin.anyicloud.messageadapter.model.process.ProcessActivityMsg;
import com.anyilanxin.anyicloud.messageadapter.model.process.ProcessInstanceMsg;
import com.anyilanxin.anyicloud.messageadapter.model.process.ProcessTaskMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxh
 * @date 2023-10-11 12:17
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageProcessServiceImpl implements IMessageProcessService {
    private final SimpMessagingTemplate template;
    private final ISocketNoticeBannerService bannerService;
    private final ISocketNoticeService noticeService;
    public static final String PREFIX = SocketDestinationPrefixesTypes.TOPIC_PROCESS.getType();


    @Override
    public void sendProcessInstanceBpmn(List<ProcessActivityMsg> msgs) {
        msgs.forEach(v -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("instanceId", v.getInstanceId());
            template.convertAndSend(PREFIX + "/instance-bpmn/" + v.getPlatform().getType() + "/" + v.getInstanceId(), jsonObject);
        });
    }


    @Override
    public void sendProcessInstance(List<ProcessInstanceMsg> msgs) {
        msgs.forEach(v -> {
            // 发送刷新数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("instanceId", v.getInstanceId());
            template.convertAndSend(PREFIX + "/process-instance-refresh/" + v.getPlatform().getType() + "/" + v.getInitiator(), jsonObject);
            // 发送通知
            SocketMsgBannerNoticeModel noticeModel = new SocketMsgBannerNoticeModel();
            StringBuilder sb = new StringBuilder("您发起的" + (StringUtils.isNotBlank(v.getBpmnProcessName()) ? v.getBpmnProcessName() : "未知申请"));
            SocketMsgNoticeModel model = new SocketMsgNoticeModel();
            ProcessInstanceState state = v.getState();
            NoticeType noticeType = NoticeType.INFO;
            switch (state) {
                case SUSPENDED -> {
                    sb.append("审批已经被挂起");
                    noticeType = NoticeType.WARNING;
                }
                case ACTIVE -> {
                    sb.append("审批已经提交成功");
                    noticeType = NoticeType.SUCCESS;
                }
                case COMPLETED -> {
                    sb.append("审批已经完成");
                    noticeType = NoticeType.SUCCESS;
                }
                case REFUSED -> {
                    sb.append("审批已经被拒绝");
                    noticeType = NoticeType.WARNING;
                }
                case INVALID -> {
                    sb.append("审批已经作废");
                    noticeType = NoticeType.WARNING;
                }
            }
            model.setContent(sb.toString());
            model.setPlatform(v.getPlatform());
            model.setNoticeType(noticeType);
            model.setTargetId(v.getInitiator());
            model.setTitle("流程申请提醒");
            noticeModel.setContent(sb.toString());
            noticeModel.setPlatform(v.getPlatform());
            noticeModel.setTargetId(v.getInitiator());
            bannerService.sendMsg(List.of(noticeModel));
            noticeService.sendMsg(List.of(model));
        });
    }


    @Override
    public void sendProcessTask(List<ProcessTaskMsg> msgs) {
        msgs.forEach(v -> {
            List<SocketMsgBannerNoticeModel> bannerNotices = new ArrayList<>(2);
            List<SocketMsgNoticeModel> notices = new ArrayList<>(2);
            // 发送刷新数据
            template.convertAndSend(PREFIX + "/process-task-refresh/" + v.getPlatform().getType() + "/" + v.getAssignee(), v);
            // 发送通知
            StringBuilder sb = new StringBuilder("您有" + (StringUtils.isNotBlank(v.getBpmnProcessName()) ? v.getBpmnProcessName() : "未知") + "审批任务待处理");
            SocketMsgBannerNoticeModel noticeModel = new SocketMsgBannerNoticeModel();
            noticeModel.setContent(sb.toString());
            noticeModel.setPlatform(v.getPlatform());
            noticeModel.setTargetId(v.getAssignee());
            bannerNotices.add(noticeModel);
            SocketMsgNoticeModel model = new SocketMsgNoticeModel();
            model.setContent(sb.toString());
            model.setPlatform(v.getPlatform());
            model.setNoticeType(NoticeType.INFO);
            model.setTargetId(v.getAssignee());
            model.setTitle("审批任务提醒");
            notices.add(model);
            UserTaskState approveState = v.getApproveState();
            // 通知申请人
            if (approveState != UserTaskState.WAIT_AUDIT && approveState != UserTaskState.AUDIT) {
                sb = new StringBuilder("您发起的" + (StringUtils.isNotBlank(v.getBpmnProcessName()) ? v.getBpmnProcessName() : "未知") + "申请" + (StringUtils.isNotBlank(v.getBpmnElementName()) ? v.getBpmnElementName() + "审批完成" : ""));
                SocketMsgBannerNoticeModel noticeModelApprol = new SocketMsgBannerNoticeModel();
                noticeModelApprol.setContent(sb.toString());
                noticeModelApprol.setPlatform(v.getPlatform());
                noticeModelApprol.setTargetId(v.getInitiator());
                bannerNotices.add(noticeModelApprol);
                SocketMsgNoticeModel modelApprol = new SocketMsgNoticeModel();
                modelApprol.setContent(sb.toString());
                modelApprol.setPlatform(v.getPlatform());
                modelApprol.setNoticeType(NoticeType.INFO);
                modelApprol.setTargetId(v.getInitiator());
                modelApprol.setTitle("流程申请提醒");
                notices.add(modelApprol);
            }
            bannerService.sendMsg(bannerNotices);
            noticeService.sendMsg(notices);
        });
    }

}
