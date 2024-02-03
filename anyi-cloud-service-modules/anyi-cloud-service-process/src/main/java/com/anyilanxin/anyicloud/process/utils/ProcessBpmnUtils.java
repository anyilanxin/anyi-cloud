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

package com.anyilanxin.anyicloud.process.utils;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.process.extend.model.AnYiUserTaskPropertyModel;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessTaskInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Documentation;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.dromara.hutool.core.collection.CollUtil;

import java.io.InputStream;
import java.util.*;

/**
 * 流程公共工具类
 *
 * @author zxh
 * @date 2021-12-06 18:10
 * @since 1.0.0
 */
@Slf4j
public class ProcessBpmnUtils {

    public static List<ProcessTaskInfoDto> getBpmnUserTaskToList(InputStream processModel) {
        Map<String, ProcessTaskInfoDto> bpmnUserTaskToMap = getBpmnUserTaskToMap(processModel);
        List<ProcessTaskInfoDto> taskInfoList = new ArrayList<>();
        if (CollUtil.isNotEmpty(bpmnUserTaskToMap)) {
            bpmnUserTaskToMap.forEach((k, v) -> {
                taskInfoList.add(v);
            });
        }
        Collections.sort(taskInfoList);
        return taskInfoList;
    }


    /**
     * 获取流程用户任务
     *
     * @param processModel ${@link InputStream}
     * @return List<ProcessTaskInfoDto> ${@link List<ProcessTaskInfoDto>}
     * @author zxh
     * @date 2022-01-03 21:31
     */
    public static Map<String, ProcessTaskInfoDto> getBpmnUserTaskToMap(InputStream processModel) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(processModel);
        Collection<UserTask> modelElementsByType = bpmnModelInstance.getModelElementsByType(UserTask.class);
        Map<String, ProcessTaskInfoDto> taskNodeList = new HashMap<>(64);
        if (CollUtil.isNotEmpty(modelElementsByType)) {
            modelElementsByType.forEach(v -> {
                if (StringUtils.isNotBlank(v.getId())) {
                    ProcessTaskInfoDto taskNodeDto = ProcessTaskInfoDto.builder().taskDefinitionKey(v.getId()).name(v.getName()).formKey(v.getCamundaFormKey()).formRef(v.getCamundaFormRef()).dueDateStr(v.getCamundaDueDate()).followUpDateStr(v.getCamundaFollowUpDate()).assignee(v.getCamundaAssignee()).build();
                    String camundaPriority = v.getCamundaPriority();
                    if (StringUtils.isNumeric(camundaPriority)) {
                        taskNodeDto.setPriority(Integer.parseInt(camundaPriority));
                    }
                    // 自定义用户任务扩展信息,json object
                    ExtensionElements extensionElements = v.getExtensionElements();
                    if (Objects.nonNull(extensionElements)) {
                        String userTaskProperty = ProcessBpmnUtils.getProcessProperty(extensionElements, AnYiUserTaskPropertyModel.USER_TASK);
                        if (StringUtils.isNotBlank(userTaskProperty)) {
                            AnYiUserTaskPropertyModel propertyModel = JSONObject.parseObject(userTaskProperty, AnYiUserTaskPropertyModel.class);
                            taskNodeDto.setPropertyModel(propertyModel);
                            taskNodeDto.setFollowUpFormatDate(propertyModel.getFollowUpDate().getValue().getValue());
                            taskNodeDto.setDueFormatDate(propertyModel.getDueDate().getValue().getValue());
                            // taskNodeDto.setTaskSort(propertyModel.getTaskSort());
                            // taskNodeDto.setFormModel(propertyModel.getFormModel());
                        }
                    }
                    // 任务描述信息
                    String documentation = ProcessBpmnUtils.getDocumentation(v.getChildElementsByType(Documentation.class));
                    taskNodeDto.setDescription(documentation);
                    // 判断是否多实例
                    Collection<MultiInstanceLoopCharacteristics> modelElementInstances = v.getChildElementsByType(MultiInstanceLoopCharacteristics.class);
                    if (CollUtil.isNotEmpty(modelElementInstances)) {
                        for (MultiInstanceLoopCharacteristics loopCharacteristics : modelElementInstances) {
                            taskNodeDto.setMultiInstance(true);
                            taskNodeDto.setSequential(loopCharacteristics.isSequential());
                            break;
                        }
                    }
                    // 处理任务排序(如果taskSort没有定义则使用任务id)
                    if (StringUtils.isNotBlank(taskNodeDto.getTaskSort())) {
                        taskNodeDto.setTaskSort(StringUtils.isNotBlank(taskNodeDto.getTaskDefinitionKey()) ? taskNodeDto.getTaskDefinitionKey() : "");
                    }
                    taskNodeList.put(v.getId(), taskNodeDto);
                }
            });
        }
        return taskNodeList;
    }


    /**
     * 获取扩展信息,json object
     *
     * @param extensionElements ${@link ExtensionElements}
     * @param camundaName       ${@link String} 扩展信息,json object字段名
     * @return String ${@link String}
     * @author zxh
     * @date 2022-01-10 20:52
     */
    public static String getProcessProperty(ExtensionElements extensionElements, String camundaName) {
        if (Objects.nonNull(extensionElements) && StringUtils.isNotBlank(camundaName)) {
            List<CamundaProperties> camundaPropertiesList = extensionElements.getElementsQuery().filterByType(CamundaProperties.class).list();
            if (CollUtil.isNotEmpty(camundaPropertiesList)) {
                for (CamundaProperties camundaProperty : camundaPropertiesList) {
                    Collection<CamundaProperty> camundaProperties = camundaProperty.getCamundaProperties();
                    for (CamundaProperty property : camundaProperties) {
                        if (StringUtils.isNotBlank(property.getCamundaName()) && StringUtils.isNotBlank(property.getCamundaValue()) && camundaName.equals(property.getCamundaName())) {
                            return property.getCamundaValue();
                        }
                    }
                }
            }
        }
        return "";
    }


    /**
     * 获取元素文档信息
     *
     * @param documentations ${@link Collection<Documentation>}
     * @return String ${@link String}
     * @author zxh
     * @date 2022-04-01 16:52
     */
    public static String getDocumentation(Collection<Documentation> documentations) {
        if (CollUtil.isNotEmpty(documentations)) {
            for (Documentation documentation : documentations) {
                String rawTextContent = documentation.getRawTextContent();
                if (StringUtils.isNotBlank(rawTextContent)) {
                    return rawTextContent;
                }
            }
        }
        return "";
    }


    /**
     * 获取元素某个属性
     *
     * @param processModel  模型
     * @param attributeName 属性名称
     * @param bpmnElementId 元素id
     * @return String
     * @author zxh
     * @date 2022-10-19 12:27
     */
    public static String getElementAttribute(InputStream processModel, String attributeName, String bpmnElementId) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(processModel);
        ModelElementInstance modelElementById = bpmnModelInstance.getModelElementById(bpmnElementId);
        if (Objects.isNull(modelElementById)) {
            throw new AnYiResponseException(AnYiResultStatus.ERROR, "当前节点模型未查询到:" + bpmnElementId);
        }
        String attributeValue = modelElementById.getAttributeValue(attributeName);
        if (StringUtils.isBlank(attributeValue)) {
            throw new AnYiResponseException(AnYiResultStatus.ERROR, "未查询到当前属性:" + attributeName);
        }
        return attributeValue;
    }


    /**
     * 获取元素某个属性
     *
     * @param processModel  模型
     * @param bpmnElementId 元素id
     * @return String
     * @author zxh
     * @date 2022-10-19 12:27
     */
    public static String getMutiElementVariable(InputStream processModel, String bpmnElementId) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(processModel);
        ModelElementInstance modelElementById = bpmnModelInstance.getModelElementById(bpmnElementId);
        MultiInstanceLoopCharacteristics uniqueChildElementByType = (MultiInstanceLoopCharacteristics) modelElementById.getUniqueChildElementByType(MultiInstanceLoopCharacteristics.class);
        String camundaElementVariable = uniqueChildElementByType.getCamundaElementVariable();
        if (StringUtils.isBlank(camundaElementVariable)) {
            throw new AnYiResponseException(AnYiResultStatus.ERROR, "未查询到变量设置值:elementVariable");
        }
        return camundaElementVariable;
    }
}
