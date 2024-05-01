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

package com.anyilanxin.cloud.process.utils;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskVariableType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程临时变量处理工具类
 *
 * @author zxh
 * @date 2021-12-06 18:10
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessTemporaryUtils {
    public final static String HAS_RUN_KEY = "HAS_RUN";

    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param type              ${@link Integer} 获取方式：0-指定序号，1-指定ActivityId
     * @param num               ${@link Integer} type为1时传递数据,最小值为1，即最近一个节点,当前节点前节点序号，比如上一个用户任务节点填写1
     * @param activityId        ${@link String} type为2时传递数据,节点id
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static void getUserTaskTemporaryMapAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, int type, Integer num, String activityId) {
        VariableMap temporaryMap = getUserTaskTemporaryMap(historyService, processInstanceId, type, num, activityId);
        runtimeService.setVariablesLocal(executionId, temporaryMap);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param activityId        ${@link String} type为2时传递数据,节点id
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static void getUserTaskTemporaryMapNodeActivityIdAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, String activityId) {
        getUserTaskTemporaryMapAndSave(runtimeService, historyService, executionId, processInstanceId, 1, null, activityId);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param num               ${@link Integer} type为1时传递数据,最小值为1，即最近一个节点,当前节点前节点序号，比如上一个用户任务节点填写1
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static void getUserTaskTemporaryMapNodeNumAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, Integer num) {
        getUserTaskTemporaryMapAndSave(runtimeService, historyService, executionId, processInstanceId, 0, num, null);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储,存储前检查是否执行过(执行过就不再调用)
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param type              ${@link Integer} 获取方式：0-指定序号，1-指定ActivityId
     * @param num               ${@link Integer} type为1时传递数据,最小值为1，即最近一个节点,当前节点前节点序号，比如上一个用户任务节点填写1
     * @param activityId        ${@link String} type为2时传递数据,节点id
     * @return 结果： true-本次保存成功,false-本地保存失败(即已经调用过存储)
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static boolean getUserTaskTemporaryMapCheckAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, int type, Integer num, String activityId) {
        Object hasRun = runtimeService.getVariableLocal(executionId, HAS_RUN_KEY);
        if (Objects.nonNull(hasRun) && (Boolean) hasRun) {
            return false;
        }
        VariableMap temporaryMap = getUserTaskTemporaryMap(historyService, processInstanceId, type, num, activityId);
        runtimeService.setVariablesLocal(executionId, temporaryMap);
        return true;
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储,存储前检查是否执行过(执行过就不再调用)
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param activityId        ${@link String} 节点id
     * @return 结果： true-本次保存成功,false-本地保存失败(即已经调用过存储)
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static boolean getUserTaskTemporaryMapNodeActivityIdCheckAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, String activityId) {
        return getUserTaskTemporaryMapCheckAndSave(runtimeService, historyService, executionId, processInstanceId, 1, null, activityId);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量同时存储,存储前检查是否执行过(执行过就不再调用)
     *
     * @param runtimeService    ${@link RuntimeService}
     * @param historyService    ${@link HistoryService}
     * @param executionId       ${@link String} 执行id
     * @param processInstanceId ${@link String} 流程实例id
     * @param num               ${@link Integer} 最小值为1，即最近一个节点
     * @return 结果： true-本次保存成功,false-本地保存失败(即已经调用过存储)
     * @author zxh
     * @date 2022-03-11 09:28
     */
    public static boolean getUserTaskTemporaryMapNodeNumCheckAndSave(RuntimeService runtimeService, HistoryService historyService, String executionId, String processInstanceId, Integer num) {
        return getUserTaskTemporaryMapCheckAndSave(runtimeService, historyService, executionId, processInstanceId, 0, num, null);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量
     *
     * @param historyService    ${@link HistoryService}
     * @param processInstanceId ${@link String} 流程实例id
     * @param type              ${@link Integer} 获取方式：0-指定序号，1-指定ActivityId
     * @param num               ${@link Integer} type为1时传递数据,最小值为1，即最近一个节点,当前节点前节点序号，比如上一个用户任务节点填写1
     * @param activityId        ${@link String} type为2时传递数据,节点id
     * @return VariableMap ${@link VariableMap}
     * @author zxh
     * @date 2022-03-11 07:38
     */
    public static VariableMap getUserTaskTemporaryMap(HistoryService historyService, String processInstanceId, int type, Integer num, String activityId) {
        VariableMap temporaryMap = Variables.createVariables();
        // 标记是否读取过
        temporaryMap.put(HAS_RUN_KEY, Variables.booleanValue(true, true));
        // 获取所有用户任务信息
        List<HistoricActivityInstance> effectiveTaskActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType(ActivityTypes.TASK_USER_TASK).orderByHistoricActivityInstanceStartTime().desc().list();
        if (CollUtil.isNotEmpty(effectiveTaskActivityInstanceList)) {
            // 获取所有多实例信息
            List<HistoricActivityInstance> multiInstanceActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType(ActivityTypes.MULTI_INSTANCE_BODY).orderByHistoricActivityInstanceStartTime().desc().list();
            // 获取非多实例用户任务的任务id,以及任务多实例的id
            HistoricActivityInstanceEntity selectActivityInstance = null;
            if (CollUtil.isNotEmpty(multiInstanceActivityInstanceList)) {
                List<HistoricActivityInstance> effectiveMultiInstanceActivityInstanceList = new ArrayList<>(multiInstanceActivityInstanceList.size());
                effectiveTaskActivityInstanceList.removeIf(v -> {
                    String parentActivityInstanceId = v.getParentActivityInstanceId();
                    String nowActivityId = v.getActivityId();
                    return multiInstanceActivityInstanceList.removeIf(sv -> {
                        if (sv.getId().equals(parentActivityInstanceId) && (nowActivityId + "#" + ActivityTypes.MULTI_INSTANCE_BODY).equals(sv.getActivityId())) {
                            effectiveMultiInstanceActivityInstanceList.add(sv);
                            return true;
                        }
                        return false;
                    });
                });
                if (CollUtil.isNotEmpty(effectiveMultiInstanceActivityInstanceList)) {
                    effectiveTaskActivityInstanceList.addAll(effectiveMultiInstanceActivityInstanceList);
                }
                List<HistoricActivityInstanceEntity> finallyInstanceActivityInstanceList = effectiveTaskActivityInstanceList.stream().map(v -> (HistoricActivityInstanceEntity) v).sorted((o1, o2) -> Long.compare(o2.getSequenceCounter(), o1.getSequenceCounter())).collect(Collectors.toList());
                if (type == 1) {
                    List<HistoricActivityInstanceEntity> selectActivityInstanceList = finallyInstanceActivityInstanceList.stream().filter(v -> (activityId + "#" + ActivityTypes.MULTI_INSTANCE_BODY).equals(v.getActivityId()) || activityId.equals(v.getActivityId())).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(selectActivityInstanceList)) {
                        selectActivityInstance = selectActivityInstanceList.get(0);
                    }
                } else {
                    if (Objects.nonNull(num) && num > 0 && num <= finallyInstanceActivityInstanceList.size()) {
                        selectActivityInstance = finallyInstanceActivityInstanceList.get(num - 1);
                    }
                }
            } else {
                List<HistoricActivityInstanceEntity> finallyInstanceActivityInstanceList = effectiveTaskActivityInstanceList.stream().map(v -> (HistoricActivityInstanceEntity) v).sorted((o1, o2) -> Long.compare(o2.getSequenceCounter(), o1.getSequenceCounter())).collect(Collectors.toList());
                selectActivityInstance = finallyInstanceActivityInstanceList.get(0);
            }
            if (Objects.nonNull(selectActivityInstance)) {
                List<HistoricVariableInstance> variableInstances = getVariableInstances(selectActivityInstance, historyService);
                if (CollUtil.isNotEmpty(variableInstances)) {
                    temporaryMap = variableInstancesTemporaryMap(variableInstances);
                }
                // 追加当前节点定义id,同时标记是否读取过
                String selectedActivityId = selectActivityInstance.getActivityId();
                if (ActivityTypes.MULTI_INSTANCE_BODY.equals(selectActivityInstance.getActivityType())) {
                    selectedActivityId = StringUtils.removeEnd(selectedActivityId, "#" + ActivityTypes.MULTI_INSTANCE_BODY);
                }
                temporaryMap.put(TaskVariableType.LAST_TASK_DEFINITION_KEY.getVariableKey(), Variables.stringValue(selectedActivityId, true));
            }
        }
        return temporaryMap;
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量
     *
     * @param historyService    ${@link HistoryService}
     * @param processInstanceId ${@link String} 流程实例id
     * @param activityId        ${@link String} type为2时传递数据,节点id
     * @return VariableMap ${@link VariableMap}
     * @author zxh
     * @date 2022-03-11 07:38
     */
    public static VariableMap getUserTaskTemporaryMapNodeActivityId(HistoryService historyService, String processInstanceId, String activityId) {
        return getUserTaskTemporaryMap(historyService, processInstanceId, 1, null, activityId);
    }


    /**
     * 获取某个节点的局部变量并转换为临时变量
     *
     * @param historyService    ${@link HistoryService}
     * @param processInstanceId ${@link String} 流程实例id
     * @param num               ${@link Integer} type为1时传递数据,最小值为1，即最近一个节点,当前节点前节点序号，比如上一个用户任务节点填写1
     * @return VariableMap ${@link VariableMap}
     * @author zxh
     * @date 2022-03-11 07:38
     */
    public static VariableMap getUserTaskTemporaryMapNodeNum(HistoryService historyService, String processInstanceId, Integer num) {
        return getUserTaskTemporaryMap(historyService, processInstanceId, 0, num, null);
    }


    /**
     * 获取变量
     *
     * @param selectActivityInstance ${@link HistoricActivityInstanceEntity}
     * @return Map<String, Object> ${@link Map<String, Object>}
     * @author zxh
     * @date 2022-03-11 07:48
     */
    private static List<HistoricVariableInstance> getVariableInstances(HistoricActivityInstanceEntity selectActivityInstance, HistoryService historyService) {
        String activityType = selectActivityInstance.getActivityType();
        List<HistoricVariableInstance> variableInstances = new ArrayList<>();
        List<HistoricVariableInstance> list;
        if (ActivityTypes.MULTI_INSTANCE_BODY.equals(activityType)) {
            list = historyService.createHistoricVariableInstanceQuery().activityInstanceIdIn(selectActivityInstance.getId()).list();
        } else {
            list = historyService.createHistoricVariableInstanceQuery().taskIdIn(selectActivityInstance.getTaskId()).list();
        }
        if (CollUtil.isNotEmpty(list)) {
            // 过滤掉全局变量

            variableInstances.addAll(list);
        }
        return variableInstances;
    }


    /**
     * 变量转换为临时变量
     *
     * @param variableInstances ${@link List<HistoricVariableInstance>}
     * @return VariableMap ${@link VariableMap}
     * @author zxh
     * @date 2022-03-11 08:03
     */
    private static VariableMap variableInstancesTemporaryMap(List<HistoricVariableInstance> variableInstances) {
        VariableMap temporaryMap = Variables.createVariables();
        if (CollUtil.isNotEmpty(variableInstances)) {
            variableInstances.forEach(v -> {
                TypedValue typedValue = v.getTypedValue();
                String type = typedValue.getType().getName();
                if (StringUtils.isNotBlank(type) && !v.getProcessInstanceId().equals(v.getActivityInstanceId())) {
                    switch (type) {
                        case "string":
                            String stringValue = (String) v.getValue();
                            temporaryMap.put(v.getName(), Variables.stringValue(StringUtils.isBlank(stringValue) ? "" : stringValue, true));
                            break;
                        case "integer":
                            Integer integerValue = (Integer) v.getValue();
                            temporaryMap.put(v.getName(), Variables.integerValue(Objects.isNull(integerValue) ? 0 : integerValue, true));
                            break;
                        case "short":
                            Short shortValue = (Short) v.getValue();
                            temporaryMap.put(v.getName(), Variables.shortValue(Objects.isNull(shortValue) ? 0 : shortValue, true));
                            break;
                        case "boolean":
                            Boolean booleanValue = (Boolean) v.getValue();
                            temporaryMap.put(v.getName(), Variables.booleanValue(!Objects.isNull(booleanValue) && booleanValue, true));
                            break;
                        case "long":
                            Long longValue = (Long) v.getValue();
                            temporaryMap.put(v.getName(), Variables.longValue(Objects.isNull(longValue) ? 0L : longValue, true));
                            break;
                        case "double":
                            Double doubleValue = (Double) v.getValue();
                            temporaryMap.put(v.getName(), Variables.doubleValue(Objects.isNull(doubleValue) ? 0.0 : doubleValue, true));
                            break;
                        case "date":
                            Date dateValue = (Date) v.getValue();
                            temporaryMap.put(v.getName(), Variables.dateValue(dateValue, true));
                            break;
                        default:
                            // 其他类型全部当字符串处理
                            Object objectValue = v.getValue();
                            temporaryMap.put(v.getName(), Variables.stringValue(Objects.isNull(objectValue) ? new JSONObject().toJSONString() : JSONObject.toJSONString(objectValue), true));
                    }
                }
            });
        }
        return temporaryMap;
    }
}
