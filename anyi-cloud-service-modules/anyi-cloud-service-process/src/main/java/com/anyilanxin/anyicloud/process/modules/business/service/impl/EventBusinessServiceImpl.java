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

package com.anyilanxin.anyicloud.process.modules.business.service.impl;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.process.extend.constant.impl.ProcessEventType;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.EventPageVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.MessageEventVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.SignalEventVo;
import com.anyilanxin.anyicloud.process.modules.business.service.IEventBusinessService;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.EventPageDto;
import com.anyilanxin.anyicloud.process.modules.business.service.mapstruct.EventPageCopyMap;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.EventSubscriptionQuery;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 路程逻辑处理实现
 *
 * @author zxh
 * @date 2020-10-15 19:53
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EventBusinessServiceImpl implements IEventBusinessService {
    private final RuntimeService runtimeService;
    private final EventPageCopyMap pageCopyMap;

    @Override
    public void signalEvent(SignalEventVo vo) throws RuntimeException {
        SignalEventReceivedBuilder signalEvent = runtimeService.createSignalEvent(vo.getEventName());
        if (!vo.isRadio()) {
            signalEvent.executionId(vo.getExecutionId());
        }
        if (CollUtil.isNotEmpty(vo.getVariables())) {
            signalEvent.setVariables(vo.getVariables());
        }
        signalEvent.send();
    }


    @Override
    public void messageEvent(MessageEventVo vo) throws RuntimeException {
        if (vo.getType() == 0) {
            if (CollUtil.isNotEmpty(vo.getVariables())) {
                runtimeService.messageEventReceived(vo.getEventName(), vo.getExecutionId(), vo.getVariables());
            } else {
                runtimeService.messageEventReceived(vo.getEventName(), vo.getExecutionId());
            }
        }
    }


    @Override
    public AnYiPageResult<EventPageDto> selectEventPage(@Valid EventPageVo vo) throws RuntimeException {
        EventSubscriptionQuery eventSubscriptionQuery = runtimeService.createEventSubscriptionQuery();
        if (StringUtils.isNotBlank(vo.getEventName())) {
            eventSubscriptionQuery.eventName(vo.getEventName());
        }
        if (StringUtils.isNotBlank(vo.getEventType())) {
            eventSubscriptionQuery.eventType(vo.getEventType());
        }
        if (StringUtils.isNotBlank(vo.getProcessInstanceId())) {
            eventSubscriptionQuery.processInstanceId(vo.getProcessInstanceId());
        }
        // 处理排序
        Set<String> ascSet = vo.getAscs();
        Set<String> descSet = vo.getDescs();
        if (CollUtil.isNotEmpty(ascSet)) {
            if (ascSet.contains("createTime")) {
                eventSubscriptionQuery.orderByCreated().asc();
            }
        } else if (CollUtil.isNotEmpty(descSet)) {
            if (descSet.contains("createTime")) {
                eventSubscriptionQuery.orderByCreated().desc();
            }
        } else {
            eventSubscriptionQuery.orderByCreated().desc();
        }
        long count = eventSubscriptionQuery.count();
        if (count <= 0) {
            return PageUtils.toPageData();
        }
        List<EventSubscription> eventSubscriptions = eventSubscriptionQuery.listPage(vo.getCurrent(), vo.getSize());
        List<EventPageDto> pageDtos = new ArrayList<>(eventSubscriptions.size());
        eventSubscriptions.forEach(v -> {
            EventPageDto eventPageDto = pageCopyMap.aToB((EventSubscriptionEntity) v);
            eventPageDto.setCreateTime(AnYiDateUtils.toLocalDateTime(v.getCreated()));
            ProcessEventType eventType = ProcessEventType.getByType(v.getEventType());
            if (Objects.nonNull(eventType)) {
                eventPageDto.setEventTypeName(eventType.getTypeName());
            }
            pageDtos.add(eventPageDto);
        });
        return PageUtils.toPageData(count, pageDtos);
    }
}
