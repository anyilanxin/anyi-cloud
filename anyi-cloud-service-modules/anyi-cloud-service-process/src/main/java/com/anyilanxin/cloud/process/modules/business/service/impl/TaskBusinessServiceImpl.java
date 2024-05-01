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

package com.anyilanxin.cloud.process.modules.business.service.impl;

import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.coreprocess.model.task.*;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskType;
import com.anyilanxin.cloud.process.modules.auxiliary.service.ISequenceFlowService;
import com.anyilanxin.cloud.process.modules.business.controller.vo.HistoryUserTaskDto;
import com.anyilanxin.cloud.process.modules.business.controller.vo.MultiInstanceHandleAddTaskVo;
import com.anyilanxin.cloud.process.modules.business.controller.vo.TaskHistoryPageVo;
import com.anyilanxin.cloud.process.modules.business.controller.vo.TaskWaitPageVo;
import com.anyilanxin.cloud.process.modules.business.service.IProcessBusinessCommonService;
import com.anyilanxin.cloud.process.modules.business.service.IProcessBusinessService;
import com.anyilanxin.cloud.process.modules.business.service.ITaskBusinessService;
import com.anyilanxin.cloud.process.modules.business.service.dto.HistoryTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.WaitTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.WaitUserTaskDto;
import com.anyilanxin.cloud.process.modules.business.service.mapstruct.*;
import com.anyilanxin.cloud.process.modules.manage.service.IProcessCategoryService;
import com.anyilanxin.cloud.process.modules.rbac.service.IProcessIdentityService;
import com.anyilanxin.cloud.processadapter.model.ProcessCallbackResultModel;
import com.anyilanxin.cloud.processadapter.model.WaitUserTaskDetailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import java.util.List;
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
public class TaskBusinessServiceImpl implements ITaskBusinessService {
    private final TaskService taskService;
    private final ISequenceFlowService sequenceFlowService;
    private final IdentityLinkCopyMap linkCopyMap;
    private final IProcessIdentityService processIdentityService;
    private final DataSourceTransactionManager dataSourceTransactionManager;
    private final TransactionDefinition transactionDefinition;
    private final IProcessBusinessService businessService;
    private final IProcessBusinessCommonService businessCommonService;
    private final FormService formService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;
    private final HistoricTaskUserTaskMap historicTaskUserTaskMap;
    private final IProcessCategoryService categoryService;
    private final MyWaitTaskPageMap waitTaskPageMap;
    private final CompleteTaskCopyMap completeTaskCopyMap;
    private final GeneralHistoryTaskPageMap historyTaskPageMap;
    private final TaskHistoryCommonPageCopyMap commonPageCopyMap;

    @Override
    public void delegateTask(TaskDelegateModel vo) throws RuntimeException {

    }

    @Override
    public void taskClaim(String taskId) throws RuntimeException {

    }

    @Override
    public void turnOverTask(TaskTurnOverModel vo) throws RuntimeException {

    }

    @Override
    public void taskUnClaim(String taskId) throws RuntimeException {

    }

    @Override
    public void cancelComplete(String taskId) throws RuntimeException {

    }

    @Override
    public ProcessCallbackResultModel completeTask(TaskAgreeModel vo) throws RuntimeException {
        return null;
    }

    @Override
    public ProcessCallbackResultModel completeAdvanceGetTaskInfo(CompleteAdvanceTaskModel vo) {
        return null;
    }

    @Override
    public void dismissTask(TaskDismissModel vo) {

    }

    @Override
    public Set<HistoryUserTaskDto> getHistoryTaskList(String processInstanceId) {
        return null;
    }

    @Override
    public Set<WaitUserTaskDto> getWaitTaskList(String processInstanceId) {
        return null;
    }

    @Override
    public WaitUserTaskDetailModel waitTaskDetail(String taskId) {
        return null;
    }

    @Override
    public AnYiPageResult<WaitTaskPageDto> selectTaskPage(TaskWaitPageVo vo, TaskType taskType) throws RuntimeException {
        return null;
    }

    @Override
    public AnYiPageResult<HistoryTaskPageDto> selectHistoryTaskPage(TaskHistoryPageVo vo, int type) {
        return null;
    }

    @Override
    public void multiInstanceSubtractTask(List<String> multiInstanceTaskIds) {

    }

    @Override
    public void multiInstanceAddTask(MultiInstanceHandleAddTaskVo vo) {

    }
}
