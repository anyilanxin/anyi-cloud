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

import com.anyilanxin.cloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.cloud.process.extend.constant.impl.ProcessPermissionVariableType;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskPermissionVariableType;
import com.anyilanxin.cloud.process.extend.model.AnYiProcessPropertyModel;
import com.anyilanxin.cloud.process.extend.model.AnYiUserTaskPropertyModel;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 流程公共工具类
 *
 * @author zxh
 * @date 2021-12-06 18:10
 * @since 1.0.0
 */
@Component
public class ProcessPermissionUtils {
    @Value("${camunda.bpm.open-org-area-data-permission:false}")
    private boolean openOrgAreaDataPermission;
    private static ProcessPermissionUtils utils;

    @PostConstruct
    void init() {
        utils = this;
    }


    /**
     * 创建权限查询条件
     */
    public static void createPermission(HistoricProcessInstanceQuery instanceQuery) {
        if (utils.openOrgAreaDataPermission) {
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentOrgId())) {
                instanceQuery.variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), "");
            } else {
                instanceQuery.or().variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), AnYiUserContextUtils.getCurrentOrgId()).variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), "").endOr();
            }
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentAreaCode())) {
                instanceQuery.variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), "");
            } else {
                instanceQuery.or().variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), AnYiUserContextUtils.getCurrentAreaCode()).variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), "").endOr();
            }
        }
    }


    /**
     * 创建权限查询条件
     */
    public static void createPermission(ProcessInstanceQuery instanceQuery) {
        if (utils.openOrgAreaDataPermission) {
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentAreaCode())) {
                instanceQuery.variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), "");
            } else {
                instanceQuery.or().variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), AnYiUserContextUtils.getCurrentAreaCode()).variableValueEquals(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(), "").endOr();
            }
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentOrgId())) {
                instanceQuery.variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), "");
            } else {
                instanceQuery.or().variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), AnYiUserContextUtils.getCurrentOrgId()).variableValueEquals(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(), "").endOr();
            }
        }
    }


    /**
     * 创建权限查询条件
     */
    public static void createPermission(TaskQuery taskQuery) {
        if (utils.openOrgAreaDataPermission) {
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentOrgId())) {
                taskQuery.taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), "");
            } else {
                taskQuery.or().taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), AnYiUserContextUtils.getCurrentOrgId()).taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), "").endOr();
            }
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentAreaCode())) {
                taskQuery.taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), "");
            } else {
                taskQuery.or().taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), AnYiUserContextUtils.getCurrentAreaCode()).taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), "").endOr();
            }

        }
    }


    /**
     * 创建权限查询条件
     */
    public static void createPermission(HistoricTaskInstanceQuery taskInstanceQuery) {
        if (utils.openOrgAreaDataPermission) {
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentAreaCode())) {
                taskInstanceQuery.taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), "");
            } else {
                taskInstanceQuery.or().taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), AnYiUserContextUtils.getCurrentAreaCode()).taskVariableValueEquals(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(), "").endOr();
            }
            if (StringUtils.isBlank(AnYiUserContextUtils.getCurrentOrgId())) {
                taskInstanceQuery.taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), "");
            } else {
                taskInstanceQuery.or().taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), AnYiUserContextUtils.getCurrentOrgId()).taskVariableValueEquals(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(), "").endOr();
            }
        }
    }


    /**
     * 写入权限信息
     */
    public static void setPermission(Map<String, Object> processVariables, AnYiProcessPropertyModel processPropertyModel, DelegateExecution execution) {
        // 1). 机构权限
        // String orgId = "";
        // if
        // (StringUtils.isNotBlank(processPropertyModel.getAnYiCandidateStarterOrg())) {
        // // 处理系统类型
        // if (processPropertyModel.getAnYiCandidateStarterOrgType() == 1) {
        // orgId = processPropertyModel.getAnYiCandidateStarterOrg();
        // }
        // // 处理表达式类型
        // else {
        // ExpressionManager expressionManager = Context
        // .getProcessEngineConfiguration()
        // .getExpressionManager();
        // Expression expression =
        // expressionManager.createExpression(processPropertyModel.getAnYiCandidateStarterOrg());
        // Object value = expression.getValue(execution);
        // if (Objects.nonNull(value)) {
        // orgId = value.toString();
        // }
        // }
        // }
        // processVariables.put(ProcessPermissionVariableType.LIMIT_ORG.getVariableKey(),
        // orgId);
        // // 2). 区域权限
        // String areaCode = "";
        // if
        // (StringUtils.isNotBlank(processPropertyModel.getAnYiCandidateStarterArea()))
        // {
        // // 处理系统类型
        // if (Objects.nonNull(processPropertyModel.getAnYiCandidateStarterAreaType())
        // && processPropertyModel.getAnYiCandidateStarterAreaType() == 1) {
        // areaCode = processPropertyModel.getAnYiCandidateStarterArea();
        // }
        // // 处理表达式类型
        // else {
        // ExpressionManager expressionManager = Context
        // .getProcessEngineConfiguration()
        // .getExpressionManager();
        // Expression expression =
        // expressionManager.createExpression(processPropertyModel.getAnYiCandidateStarterArea());
        // Object value = expression.getValue(execution);
        // if (Objects.nonNull(value)) {
        // areaCode = value.toString();
        // }
        // }
        // }
        // processVariables.put(ProcessPermissionVariableType.LIMIT_AREA.getVariableKey(),
        // areaCode);
    }


    /**
     * 写入权限信息
     */
    public static void setPermission(DelegateTask delegateTask, AnYiUserTaskPropertyModel propertyModel) {
        // 1). 机构权限
        // String orgId = "";
        // if (StringUtils.isNotBlank(propertyModel.getAnYiCandidateOrg())) {
        // // 处理系统类型
        // if (propertyModel.getAnYiCandidateOrgType() == 1) {
        // orgId = propertyModel.getAnYiCandidateOrg();
        // }
        // // 处理表达式类型
        // else {
        // ExpressionManager expressionManager = Context
        // .getProcessEngineConfiguration()
        // .getExpressionManager();
        // Expression expression =
        // expressionManager.createExpression(propertyModel.getAnYiCandidateOrg());
        // Object value = expression.getValue(delegateTask.getExecution());
        // if (Objects.nonNull(value)) {
        // orgId = value.toString();
        // }
        // }
        // }
        // delegateTask.setVariableLocal(TaskPermissionVariableType.LIMIT_ORG.getVariableKey(),
        // orgId);
        // // 2). 区域权限
        // String areaCode = "";
        // if (StringUtils.isNotBlank(propertyModel.getAnYiCandidateArea())) {
        // // 处理系统类型
        // if (Objects.nonNull(propertyModel.getAnYiCandidateAreaType()) &&
        // propertyModel.getAnYiCandidateAreaType() == 1) {
        // areaCode = propertyModel.getAnYiCandidateArea();
        // }
        // // 处理表达式类型
        // else {
        // ExpressionManager expressionManager = Context
        // .getProcessEngineConfiguration()
        // .getExpressionManager();
        // Expression expression =
        // expressionManager.createExpression(propertyModel.getAnYiCandidateArea());
        // Object value = expression.getValue(delegateTask.getExecution());
        // if (Objects.nonNull(value)) {
        // areaCode = value.toString();
        // }
        // }
        // }
        // delegateTask.setVariableLocal(TaskPermissionVariableType.LIMIT_AREA.getVariableKey(),
        // areaCode);
    }
}
