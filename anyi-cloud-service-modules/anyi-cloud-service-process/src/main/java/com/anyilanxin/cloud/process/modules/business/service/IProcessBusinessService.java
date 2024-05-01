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

package com.anyilanxin.cloud.process.modules.business.service;

import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.coreprocess.model.AnYiBpmnProcessInstanceModel;
import com.anyilanxin.cloud.process.modules.business.controller.vo.*;
import com.anyilanxin.cloud.process.modules.business.service.dto.EnableDismissDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.InstanceApplyInfoDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.InstanceUserTaskDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.ProcessPageDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.ProcessTaskInfoDto;
import com.anyilanxin.cloud.processadapter.model.FormReSubmitProcessModel;
import com.anyilanxin.cloud.processadapter.model.FormSubmitProcessModel;
import com.anyilanxin.cloud.processadapter.model.MsgSubmitProcessModel;
import com.anyilanxin.cloud.processadapter.model.ProcessCallbackResultModel;

import java.util.List;
import java.util.Set;

/**
 * 流程逻辑处理
 *
 * @author zxh
 * @date 2020-10-15 19:46
 * @since 1.0.0
 */
public interface IProcessBusinessService {

    /**
     * 发起流程
     *
     * @param vo ProcessDto{@link FormSubmitProcessModel} 发起流程参数
     * @return String ${@link ProcessCallbackResultModel} 流程信息
     * @author zxh
     * @date 2021-12-05 17:49
     */
    ProcessCallbackResultModel submitProcess(FormSubmitProcessModel vo);


    /**
     * 发起流程(基于消息事件)
     *
     * @param model ProcessDto{@link MsgSubmitProcessModel} 发起流程参数
     * @return String ${@link ProcessCallbackResultModel} 流程信息
     * @author zxh
     * @date 2021-12-05 17:49
     */
    ProcessCallbackResultModel submitProcess(MsgSubmitProcessModel model);


    /**
     * 重新提交申请
     *
     * @param vo
     * @return ProcessCallbackResultModel
     * @author zxh
     * @date 2022-06-19 18:21
     */
    ProcessCallbackResultModel reSubmit(FormReSubmitProcessModel vo);


    /**
     * 取消流程
     *
     * @param vo ${@link CancelProcessVo} 取消流程信息
     * @author zxh
     * @date 2021-12-05 17:51
     */
    void cancelProcess(CancelProcessVo vo);


    /**
     * 催办
     *
     * @param vo ${@link ProcessUrgentVo} 催办信息
     * @author zxh
     * @date 2021-12-05 17:42
     */
    void urgentTask(ProcessUrgentVo vo);


    /**
     * 查询我的申请
     *
     * @param vo ${@link ProcessQueryPageVo} 查询条件
     * @return AnYiPageResult<ProcessPageDto> ${@link AnYiPlusPageResult < ProcessPageDto >}
     * @author zxh
     * @date 2021-12-05 17:51
     */
    AnYiPageResult<ProcessPageDto> selectProcessPage(ProcessQueryPageVo vo);


    /**
     * 获取申请基本信息(包含申请表单)
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return InstanceApplyInfoDto ${@link InstanceApplyInfoDto}
     * @author zxh
     * @date 2021-12-29 12:15
     */
    InstanceApplyInfoDto selectInstanceApplyInfo(String processInstanceId);


    /**
     * 获取用户任务
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return List<InstanceOptionDto> ${@link List< InstanceUserTaskDto >}
     * @author zxh
     * @date 2021-12-29 12:16
     */
    List<InstanceUserTaskDto> selectUserTaskInstance(String processInstanceId, boolean checkInstance);


    /**
     * 通过流程定义key或者流程定义id获取用户任务信息
     *
     * @param processKeyOrId ${@link String} 流动定义key或流程定义id
     * @return List<ProcessTaskInfoDto> ${@link List<ProcessTaskInfoDto>}
     * @author zxh
     * @date 2022-04-01 17:14
     */
    List<ProcessTaskInfoDto> selectUserTaskByProcessKeyOrId(String processKeyOrId);


    /**
     * 获取所有流程实例信息
     *
     * @param processInstanceId
     * @return ActInstanceInfoDto
     * @author zxh
     * @date 2022-06-13 23:13
     */
    AnYiBpmnProcessInstanceModel queryBpmnProcessInstance(String processInstanceId);


    /**
     * 查询可打回的节点或连线
     *
     * @param vo 查询参数
     * @return List<EnableDismissDto>
     * @author zxh
     * @date 2022-06-21 18:55
     */
    List<EnableDismissDto> selectEnableDismiss(EnableDismissVo vo);


    /**
     * 删除历史流程
     *
     * @param processInstanceId 流程实例id
     * @author zxh
     * @date 2022-10-07 21:10:31
     */
    void deleteHistoryProcess(String processInstanceId);


    /**
     * 批量删除历史流程
     *
     * @param processInstanceIds 流程实例id
     * @author zxh
     * @date 2022-10-07 21:10:39
     */
    void deleteHistoryProcessBatch(Set<String> processInstanceIds);


    /**
     * 添加多实例任务
     *
     * @param vo 数据信息
     * @author zxh
     * @date 2022-10-19 11:54:34
     */
    void addMultiInstanceTask(MultiInstanceTaskVo vo);
}
