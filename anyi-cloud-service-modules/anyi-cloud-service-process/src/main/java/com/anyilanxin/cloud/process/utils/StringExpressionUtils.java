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

import com.anyilanxin.cloud.process.extend.constant.ProcessKeywordConstant;
import com.anyilanxin.cloud.process.extend.constant.impl.ModelElementType;
import com.anyilanxin.cloud.process.extend.constant.impl.VariableType;
import com.anyilanxin.cloud.process.modules.manage.service.dto.UserTaskNodeApprovePersonDto;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.impl.juel.ExpressionFactoryImpl;
import org.camunda.bpm.impl.juel.SimpleContext;
import org.camunda.bpm.impl.juel.jakarta.el.ExpressionFactory;
import org.camunda.bpm.impl.juel.jakarta.el.ValueExpression;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串表达式解析
 *
 * @author zxh
 * @date 2021-08-01 13:43
 * @since 1.0.0
 */
public class StringExpressionUtils {

    /**
     * 字符串变量替换
     *
     * @param variableMap ${@link Map<String, String>}
     * @param expression  ${@link String} 表达式
     * @return String ${@link String}
     * @author zxh
     * @date 2021-08-01 13:48
     */
    public static String getStringExpressionValue(Map<String, String> variableMap, String expression) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        if (CollUtil.isNotEmpty(variableMap)) {
            variableMap.forEach((k, v) -> context.setVariable(k, factory.createValueExpression(v, String.class)));
        }
        ValueExpression valueExpression = factory.createValueExpression(context, expression, String.class);
        return valueExpression.getValue(context).toString();
    }


    /**
     * 去掉变量中表达式符号
     *
     * @param variable ${@link String}
     * @return String ${@link String}
     * @author zxh
     * @date 2021-08-01 14:07
     */
    public static String getVariableNoSymbol(String variable) {
        if (StringUtils.isBlank(variable)) {
            return "";
        }
        return variable.replaceAll("\\$\\{", "").replace("}", "");
    }


    /**
     * 判断变量是表达式还是常量
     *
     * @param variable ${@link String}
     * @author zxh
     * @date 2021-08-01 19:11
     */
    public static boolean isExpressionVariable(String variable) {
        if (StringUtils.isBlank(variable)) {
            return false;
        }
        String reg = "^(\\$\\{)([0-9a-zA-Z_]+)(})$";
        return Pattern.matches(reg, variable);
    }


    /**
     * 获取所有userTask信息
     *
     * @return List<UserTaskNodeApprovePersonDto> ${@link List< UserTaskNodeApprovePersonDto >}
     * @author zxh
     * @date 2021-08-01 19:43
     */
    public static List<UserTaskNodeApprovePersonDto> getApprovePersonExtentVariable(Collection<UserTask> userTasks, Collection<StartEvent> modelElementsByType) {
        Set<UserTaskNodeApprovePersonDto> approvePersonDtos = new HashSet<>();
        if (CollUtil.isNotEmpty(userTasks)) {
            List<StartEvent> startCollect = modelElementsByType.stream().filter(v -> ModelElementType.START_EVENT.getTypeName().equals(v.getDomElement().getParentElement().getLocalName())).collect(Collectors.toList());
            String initiatorValue = "";
            if (CollUtil.isNotEmpty(startCollect)) {
                StartEvent startEvent = startCollect.get(0);
                String camundaInitiator = startEvent.getCamundaInitiator();
                if (StringExpressionUtils.isExpressionVariable(camundaInitiator)) {
                    VariableType variableType = VariableType.getVariableType(camundaInitiator);
                    if (variableType == VariableType.SYS_GLOBAL) {
                        initiatorValue = startEvent.getCamundaInitiator();
                    }
                }
            }
            for (UserTask userTask : userTasks) {
                String camundaAssignee = userTask.getCamundaAssignee();
                String camundaCandidateGroups = userTask.getCamundaCandidateGroups();
                String camundaCandidateUsers = userTask.getCamundaCandidateUsers();
                String name = userTask.getName();
                if (!name.startsWith(ProcessKeywordConstant.SUBMIT_APPLICATION_PREFIX)) {
                    if (StringUtils.isNotBlank(initiatorValue)) {
                        if (initiatorValue.equals(camundaAssignee) || initiatorValue.equals(camundaCandidateUsers) || initiatorValue.equals(camundaCandidateGroups)) {
                            continue;
                        }
                    }
                    UserTaskNodeApprovePersonDto dto = new UserTaskNodeApprovePersonDto();
                    dto.setAssignee(camundaAssignee);
                    dto.setName(name);
                    dto.setCandidateUsers(camundaCandidateUsers);
                    dto.setCandidateGroups(camundaCandidateGroups);
                    approvePersonDtos.add(dto);
                }
            }
        }
        if (CollUtil.isNotEmpty(approvePersonDtos)) {
            return new ArrayList<>(approvePersonDtos);
        }
        return null;
    }
}
