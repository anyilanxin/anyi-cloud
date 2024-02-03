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

package com.anyilanxin.anyicloud.process.modules.business.service;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.coreprocess.model.task.*;
import com.anyilanxin.anyicloud.process.extend.constant.impl.TaskType;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.HistoryUserTaskDto;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.MultiInstanceHandleAddTaskVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.TaskHistoryPageVo;
import com.anyilanxin.anyicloud.process.modules.business.controller.vo.TaskWaitPageVo;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.HistoryTaskPageDto;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.WaitTaskPageDto;
import com.anyilanxin.anyicloud.process.modules.business.service.dto.WaitUserTaskDto;
import com.anyilanxin.anyicloud.processadapter.model.ProcessCallbackResultModel;
import com.anyilanxin.anyicloud.processadapter.model.WaitUserTaskDetailModel;

import java.util.List;
import java.util.Set;

/**
 * 流程逻辑处理
 *
 * @author zxh
 * @date 2020-10-15 19:46
 * @since 1.0.0
 */
public interface ITaskBusinessService {

    /**
     * 流程任务委托
     *
     * @param vo ${@link TaskDelegateModel} 委托信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-05-22 21:16
     */
    void delegateTask(TaskDelegateModel vo) throws RuntimeException;


    /**
     * 流程任务签收
     *
     * @param taskId 任务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-05-22 21:01
     */
    void taskClaim(String taskId) throws RuntimeException;


    /**
     * 流程任务转办
     *
     * @param vo ${@link String} 转办信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-08-02 00:01
     */
    void turnOverTask(TaskTurnOverModel vo) throws RuntimeException;


    /**
     * 流程任务归还
     *
     * @param taskId 任务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-05-22 21:01
     */
    void taskUnClaim(String taskId) throws RuntimeException;


    /**
     * 任务撤回
     *
     * @param taskId 任务id
     * @author zxh
     * @date 2021-12-05 17:04
     */
    void cancelComplete(String taskId) throws RuntimeException;


    /**
     * 审批
     *
     * @param vo ${@link TaskAgreeModel} 任务处理表单信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-15 19:51
     */
    ProcessCallbackResultModel completeTask(TaskAgreeModel vo) throws RuntimeException;


    /**
     * 预提交获取下个用户任务节点信息
     *
     * @param vo
     * @return ProcessCallbackResultModel
     * @author zxh
     * @date 2022-08-08 12:12
     */
    ProcessCallbackResultModel completeAdvanceGetTaskInfo(CompleteAdvanceTaskModel vo);


    /**
     * 驳回
     *
     * @param vo ${@link TaskDismissModel} 驳回信息
     * @author zxh
     * @date 2021-12-05 17:08
     */
    void dismissTask(TaskDismissModel vo);


    /**
     * 获取历史审批用户任务
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return Set<HistoryTaskVo> ${@link Set < HistoryUserTaskDto >}
     * @author zxh
     * @date 2021-12-05 17:18
     */
    Set<HistoryUserTaskDto> getHistoryTaskList(String processInstanceId);


    /**
     * 获取未办用户任务
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return Set<WaitUserTaskVo> ${@link Set< WaitUserTaskDto >}
     * @author zxh
     * @date 2021-12-05 17:21
     */
    Set<WaitUserTaskDto> getWaitTaskList(String processInstanceId);


    /**
     * 查询我的待办任务详情
     *
     * @param taskId ${@link String} 任务id
     * @return WaitTaskDetailDto ${@link WaitUserTaskDetailModel}
     * @author zxh
     * @date 2021-12-05 17:24
     */
    WaitUserTaskDetailModel waitTaskDetail(String taskId);


    /**
     * 分页查询待处理任务
     *
     * @param vo       $
     * @param taskType $
     * @return {@link AnYiPageResult }<{@link WaitTaskPageDto }>
     * @throws RuntimeException
     * @author zxh
     * @date 2023-12-26 15:25:43
     */
    AnYiPageResult<WaitTaskPageDto> selectTaskPage(TaskWaitPageVo vo, TaskType taskType) throws RuntimeException;


    /**
     * 分页查询已处理任务
     *
     * @param vo   $
     * @param type $
     * @return {@link AnYiPageResult }<{@link HistoryTaskPageDto }>
     * @author zxh
     * @date 2023-12-26 15:25:41
     */
    AnYiPageResult<HistoryTaskPageDto> selectHistoryTaskPage(TaskHistoryPageVo vo, int type);


    /**
     * 会签减签
     *
     * @param multiInstanceTaskIds
     * @author zxh
     * @date 2023-12-26 15:25:38
     */
    void multiInstanceSubtractTask(List<String> multiInstanceTaskIds);


    /**
     * 会签加签
     *
     * @param vo
     * @author zxh
     * @date 2023-12-21 15:49:04
     */
    void multiInstanceAddTask(MultiInstanceHandleAddTaskVo vo);
}
